package com.parkinglotsystem.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Pattern;

import com.parkinglotsystem.enums.ActorType;

import lombok.Data;
@Data
public class RegisterDto {
	@Pattern(regexp = "^[A-Z][a-z\\s]{3,}", message = "Please Enter Valid FirstName atleast minmum three character")
	private String firstName;
	@Pattern(regexp = "^[A-Z][a-z\\s]{3,}", message = "Please Enter Valid LastName  atleast minmum three character ")
	private String lastName;

	private String email;
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "length should be 8 character")
	private String password;
	
	@Enumerated(value = EnumType.STRING)
	private ActorType actorType;
	
	@Pattern(regexp = "[7-9]{1}[0-9]{9}", message = "Please Enter Valid PhoneNumber")
	private String phoneNo;

}
