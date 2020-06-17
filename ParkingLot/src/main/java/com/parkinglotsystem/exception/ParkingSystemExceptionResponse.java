package com.parkinglotsystem.exception;

import com.parkinglotsystem.response.ParkingSystemResponse;

public class ParkingSystemExceptionResponse extends ParkingSystemResponse {

	private String description;

	public ParkingSystemExceptionResponse(String msg, String desciption) {
		super(msg);
		this.description = desciption;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
