package com.agatarauzer.myBooks.integration.H2Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.agatarauzer.myBooks.user.User;

public interface TestH2UserRepository extends CrudRepository<User, Long> {
	@Override
	List<User> findAll();
}
