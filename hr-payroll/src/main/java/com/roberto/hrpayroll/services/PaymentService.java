package com.roberto.hrpayroll.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.roberto.hrpayroll.entities.Payment;

@Service
public class PaymentService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Payment getPayment(long workerId, int days) {
		return new Payment("Bob",200.0,days);
	}

}
