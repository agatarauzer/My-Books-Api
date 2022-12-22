package com.agatarauzer.myBooks.security.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.security.ConfirmationToken;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {
	Optional<ConfirmationToken> findConfirmationTokenByConfirmationToken(String token);
}
