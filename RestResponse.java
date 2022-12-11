package com.example.synchrony.Response;

import com.example.synchrony.userEntity.User;

public class RestResponse {
	
	private int status;
	private String message;
	private User user;
	
	public RestResponse(int status, String message, User user) {
		super();
		this.status = status;
		this.message = message;
		this.user = user;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getuser() {
		return user;
	}

	public void setuser(User user) {
		this.user = user;
	}
	
	

}
