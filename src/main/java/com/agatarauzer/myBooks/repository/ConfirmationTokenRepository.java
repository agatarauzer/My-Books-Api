package com.agatarauzer.myBooks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.domain.ConfirmationToken;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {

}