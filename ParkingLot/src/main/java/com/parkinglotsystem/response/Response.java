package com.parkinglotsystem.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Response {
	private String status;
	private String message;
	private Object data;
	private String property;
	private String property2;
	private String string;
	private LocalDateTime localDateTime;
	private int i;
	private String string1;
	
	
	public Response(LocalDateTime localDateTime, int i, String string1) {
		
		this.localDateTime=localDateTime;
		this.i=i;
		this.string1=string1;
	}

	public Response(LocalDateTime dateTime ,String status , String message) {
		this.status = status;
		this.message = message;
	}
	public Response(Object data, String message) {
		this.message = message;
		this.data = data;
	}

	public Response(String message, String status) {
		this.message = message;
		this.status = status;
	}

	public Response(String status, String message, Object data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public Response(String property, String property2, String string) {
		this.property = property;
		this.property2 = property2;
		this.string = string;


	}

}
