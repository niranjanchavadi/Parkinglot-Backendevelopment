package com.parkinglotsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.parkinglotsystem.dto.LoginDto;
import com.parkinglotsystem.dto.RegisterDto;
import com.parkinglotsystem.exception.UserNotFoundException;
import com.parkinglotsystem.model.PasswordDto;
import com.parkinglotsystem.response.Response;
import com.parkinglotsystem.serviceimpl.AuthService;

@RestController
@RequestMapping("/parkinglot")
public class AuthController {

	@Autowired
	private AuthService parkingLotService;

	@PostMapping("/registration")
	public ResponseEntity<Response> register(@RequestBody RegisterDto registerDto) throws UserNotFoundException {
		return  ResponseEntity.ok(parkingLotService.registration(registerDto));
	}

	@PostMapping("/login")
	public ResponseEntity<Response> userLogin(@RequestBody LoginDto logindto) throws UserNotFoundException {
		return  ResponseEntity.ok(parkingLotService.login(logindto));
	}

	@GetMapping("/verify")
	public ResponseEntity<Response> userVerification(@RequestParam String token) {
		return  ResponseEntity.ok(parkingLotService.verify(token));
	}

	@PutMapping("/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam String email) throws UserNotFoundException {
		return  ResponseEntity.ok(parkingLotService.forgetPassword(email));
	}

	@PutMapping("/resetpassword")
	public ResponseEntity<Response> resetPassword(@RequestBody PasswordDto resetPassword,
			                                      @RequestParam String token) throws UserNotFoundException {
		return  ResponseEntity.ok(parkingLotService.resetPassword(resetPassword, token));
	}

}
