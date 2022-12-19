package com.agatarauzer.myBooks.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.domain.Purchase;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
	public Optional<Purchase> findByBookId(Long bookId);
}
