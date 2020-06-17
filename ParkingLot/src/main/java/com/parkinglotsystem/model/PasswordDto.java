package com.parkinglotsystem.model;


import javax.validation.constraints.Pattern;

import lombok.Data;
@Data
public class PasswordDto {
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "length should be 8 character")
	private String password;
	private String confirmpassword;


}