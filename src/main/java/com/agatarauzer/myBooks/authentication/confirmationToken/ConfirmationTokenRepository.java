package com.agatarauzer.myBooks.authentication.confirmationToken;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {
	Optional<ConfirmationToken> findConfirmationTokenByConfirmationToken(String token);
}
