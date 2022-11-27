package com.agatarauzer.myBooks.integration;

import org.springframework.data.repository.CrudRepository;

import com.agatarauzer.myBooks.domain.ConfirmationToken;

public interface TestH2ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {

}
