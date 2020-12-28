package com.roberto.hroauth.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.roberto.hroauth.entities.User;

@Component
//nome do micro serviço que vai se comunicar, especificar o caminho com path
@FeignClient(name="hr-user", path = "/users")
public interface UserFeignClient {
	
	@GetMapping(value = "/search")	
	ResponseEntity<User> findByEmail(@RequestParam String email);

}
