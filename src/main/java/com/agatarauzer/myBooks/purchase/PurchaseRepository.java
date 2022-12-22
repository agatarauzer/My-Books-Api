package com.agatarauzer.myBooks.purchase;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
	public Optional<Purchase> findByBookId(Long bookId);
}
