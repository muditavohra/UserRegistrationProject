package com.example.synchrony.Service;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.synchrony.Request.LoginRequest;
import com.example.synchrony.Request.SignUpRequest;
import com.example.synchrony.Response.RestResponse;
import com.example.synchrony.UserDao.UserDao;
import com.example.synchrony.UserDao.UserDaoImpl;
import com.example.synchrony.userEntity.User;

@Transactional
@Service
public class UserServiceImpl implements UserService, UserDetailsService{
	
	@Autowired
	public UserDao userDao;
	
	@Autowired
	public UserDaoImpl userDaoImpl;
	
	
	@Override
	public RestResponse signup(SignUpRequest signUpRequest) {
		//Signing up new user and saving data in db
		User user=new User(signUpRequest.getUsername(), signUpRequest.getEmail() );
		BeanUtils.copyProperties(signUpRequest, user);
		userDaoImpl.save(user);
        RestResponse response=new RestResponse(200, "success" , user);
        
		return response;
	}
	
	  @Override
	  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//Login the existing user and finding by username 
	    User user = userDao.findByUsername(username);
	    if (user == null) {
	      throw new UsernameNotFoundException("User not found");
	    }
	    return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), null);
	  }
}
