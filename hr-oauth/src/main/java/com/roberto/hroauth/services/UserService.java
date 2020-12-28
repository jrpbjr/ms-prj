package com.roberto.hroauth.services;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.roberto.hroauth.entities.User;
import com.roberto.hroauth.feignclients.UserFeignClient;

public class UserService {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserFeignClient userFeignClient;
	
	public User findByEmail(String email) {
		User user = userFeignClient.findByEmail(email).getBody();
		if(user == null) {
			logger.error("Email not found" + email);
			throw new IllegalArgumentException("Email not fount");
		}
		logger.info("Email found" + email);
		return user;
	}

}
