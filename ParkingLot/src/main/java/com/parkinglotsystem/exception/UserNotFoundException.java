package com.parkinglotsystem.exception;

public class UserNotFoundException extends Exception 
{	
	private static final long serialVersionUID = 1L;
	private String statusCode;
	String message;
	
	public UserNotFoundException(String message) {
		super(message);
		this.message = message;
	}
	
	public UserNotFoundException(String message, String status) {
		super(message);
		this.message = message;
		this.statusCode = status;
	}
	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatus(String status) {
		this.statusCode = status;
	}

	public String getStatus() {
		return statusCode;
	}


}