package com.agatarauzer.myBooks.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.agatarauzer.myBooks.repository.UserRepository;

public class AdminStatisticsService {
	
	@Autowired
	private UserRepository userRepository;
	
	public Map<String, Long> getUsersRegisteredByMonth() {
		return userRepository.findByRegistrationMonth();
	}
	
	
	
	
	//ile uzytkowników opłaciło dostęp / ile nie wg miesięcy
	
	//ile książek dodaje średnio jeden użytkownik
	

	
	
	
	
	
	

}
