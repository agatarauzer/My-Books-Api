package com.agatarauzer.myBooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}