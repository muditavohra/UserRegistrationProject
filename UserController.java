package com.example.synchrony.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.synchrony.Request.SignUpRequest;
import com.example.synchrony.Response.RestResponse;
import com.example.synchrony.Service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
    private UserService userService;

	//To signup new user
    @RequestMapping(value="/signup" , method=RequestMethod.POST)
    public RestResponse signUp(@RequestBody SignUpRequest signUpRequest){
        return userService.signup(signUpRequest);
    }

    //To login existing user 
    @GetMapping("/login")
    public String login() {
      return "login";
    }

}
