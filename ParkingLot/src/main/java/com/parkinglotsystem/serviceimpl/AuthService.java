package com.parkinglotsystem.serviceimpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.parkinglotsystem.dto.LoginDto;
import com.parkinglotsystem.dto.RegisterDto;
import com.parkinglotsystem.enums.ActorType;
import com.parkinglotsystem.exception.UserNotFoundException;
import com.parkinglotsystem.mailservice.JavaMailservices;
import com.parkinglotsystem.model.PasswordDto;
import com.parkinglotsystem.model.User;
import com.parkinglotsystem.repository.UserRepo;
import com.parkinglotsystem.response.Response;
import com.parkinglotsystem.util.JWTGenerator;
import com.parkinglotsystem.util.Util;

@Service
@PropertySource("classpath:status.properties")
@PropertySource("classpath:message.properties")
@PropertySource("classpath:exception.properties")
public class AuthService {

	@Autowired
	private BCryptPasswordEncoder PasswordEncoder;

	@Autowired
	private UserRepo Userrepository;

	@Autowired
	private JavaMailservices messageService;

	@Autowired
	private Environment env;



	public Response registration(RegisterDto registerDto) throws UserNotFoundException {
		try {
			User emailavailable = Userrepository.findEmail(registerDto.getEmail());
			if (emailavailable != null) {
				return new Response(env.getProperty("user.already.register.message"),
						env.getProperty("bad.request.response.code"));
			} else {
				User user = new User();
				BeanUtils.copyProperties(registerDto, user);
				user.setCreateDate(Util.To_Day_DATE);
				user.setVerified(false);
				user.setPassword(PasswordEncoder.encode(user.getPassword()));
				user.setModifiedDate(Util.To_Day_DATE);
				Userrepository.save(user);
				User sendMail = Userrepository.findEmail(registerDto.getEmail());
				String response = Util.VERIFY_ADDRESS + JWTGenerator.createJWT(sendMail.getId(), Util.REGISTER_EXP);
				messageService.send(sendMail.getEmail(), Util.VERIFICATION, response);
				return new Response(env.getProperty("registration.successfull.message"),
						env.getProperty("bad.gateway.response.code"));
			}
		} catch (BeansException e) {
			throw new UserNotFoundException(env.getProperty("User.not.found"),
					env.getProperty("bad.request.response.code"));
		}

	}

	public Response login(LoginDto logindto) throws UserNotFoundException {
		User available = Userrepository.findEmail(logindto.getEmail());
		Userrepository.modifiedDate(Util.To_Day_DATE, available.getId());
		if (PasswordEncoder.matches(logindto.getPassword(), available.getPassword())) {
			String token = JWTGenerator.createJWT(available.getId(), Util.LOGIN_EXP);
			available.setModifiedDate(Util.To_Day_DATE);
			available.setUserStatus(Boolean.TRUE);
			Userrepository.save(available);
			return new Response(env.getProperty("ok.response.code"),
					env.getProperty("login.successfull.message"), "Token:   " + token);
		}
		throw new UserNotFoundException(env.getProperty("login.failed.message"),
				env.getProperty("bad.request.response.code"));

	}

	public Response verify(String token) {
		long id = JWTGenerator.decodeJWT(token);
		User availableId = Userrepository.findById(id);
		if (availableId == null) {
			return new Response(env.getProperty("user.not.found.exception.message"),
					env.getProperty("not.found.response.code"));
		} else {
			if (!availableId.isVerified()) {
				availableId.setVerified(true);
				Userrepository.verify(availableId.getId());
				return new Response(env.getProperty("ok.response.code"),
						env.getProperty("user.verified.successfully.message"));
			}
			return new Response(env.getProperty("user.already.verified.message"),
					env.getProperty("not.found.response.code"));
		}
	}

	public ActorType getActorType(long id) {
		User user = Userrepository.findById(id);
		if (user != null) {
			System.out.println(" Inside AuthService::getActorType actorType::"+user.getActorType().name());
			return user.getActorType();
		} else {
			return null;
		}
	}
	
	
	
	public Response forgetPassword(String email) throws UserNotFoundException {

		User isIdAvailable = Userrepository.findEmail(email);
		if (isIdAvailable != null && isIdAvailable.isVerified()) {
			String response = Util.RESET_PASSWORD
					+ JWTGenerator.createJWT(isIdAvailable.getId(), Util.LOGIN_EXP);
			messageService.send(email, env.getProperty(" password.uptation.successfully.message"), response);

			return new Response(env.getProperty(" check.mail_message"), env.getProperty("created.response.code"));
		}
		return new Response(env.getProperty("user.not.found.exception.message"), env.getProperty("not.found.response.code"));
	}
	
	public Response resetPassword(PasswordDto resetpassword, String token) throws UserNotFoundException {
		if (resetpassword.getPassword().equals(resetpassword.getConfirmpassword())) {
			long id = JWTGenerator.decodeJWT(token);
			User user = Userrepository.findById(id);

			if (user != null) {
				user.setPassword(PasswordEncoder.encode((resetpassword.getPassword())));
				user.setModifiedDate(Util.To_Day_DATE);
				Userrepository.save(user);
				JWTGenerator.createJWT(user.getId(), Util.LOGIN_EXP);
				return new Response(env.getProperty(" password.uptation.successfully.message"),env.getProperty("created.response.code"));
			}
		}
		return new Response(env.getProperty("valid.input.message"), env.getProperty("user.authentication.exception.status"));
	}
}
