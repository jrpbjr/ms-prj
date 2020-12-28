package com.roberto.hroauth.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.roberto.hroauth.entities.User;
import com.roberto.hroauth.feignclients.UserFeignClient;

public class UserService {
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	public User findByEmail(String email) {
		User user = userFeignClient.findByEmail(email).getBody();
		if(user == null) {
			throw new IllegalArgumentException("Email not fount");
		}
		return user;
	}

}
