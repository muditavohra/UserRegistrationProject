package com.example.synchrony.Service;

import com.example.synchrony.Request.SignUpRequest;
import com.example.synchrony.Response.RestResponse;

public interface UserService {
	
	public RestResponse signup(SignUpRequest signUpRequest);

}
