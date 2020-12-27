package com.roberto.hruser.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roberto.hruser.entities.User;
import com.roberto.hruser.repositories.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
		
	@Autowired
	private UserRepository repository;
		
	
	
	@GetMapping(value = "/{id}")	
	
	public ResponseEntity<User> findById(@PathVariable Long id){
		
		/*
		try {
			Thread.sleep(3000L);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
		//logger.info("PORT = " + env.getProperty("local.server.port"));
		
		User obj = repository.findById(id).get();
		return ResponseEntity.ok(obj);
	}

}

