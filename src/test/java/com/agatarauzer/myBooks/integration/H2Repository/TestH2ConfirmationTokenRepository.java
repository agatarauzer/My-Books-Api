package com.agatarauzer.myBooks.integration.H2Repository;

import org.springframework.data.repository.CrudRepository;

import com.agatarauzer.myBooks.domain.ConfirmationToken;

public interface TestH2ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {

}
