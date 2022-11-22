package com.agatarauzer.myBooks.integration;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.agatarauzer.myBooks.domain.User;

public interface TestH2Repository extends CrudRepository<User, Long> {
	@Override
	List<User> findAll();
}
