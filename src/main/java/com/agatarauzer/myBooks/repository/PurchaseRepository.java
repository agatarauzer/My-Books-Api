package com.agatarauzer.myBooks.repository;

import org.springframework.data.repository.CrudRepository;

import com.agatarauzer.myBooks.domain.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
	public Purchase findByBookId(Long bookId);
}
