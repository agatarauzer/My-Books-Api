package com.agatarauzer.myBooks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.domain.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
