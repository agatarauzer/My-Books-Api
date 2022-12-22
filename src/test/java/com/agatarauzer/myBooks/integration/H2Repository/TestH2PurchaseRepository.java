package com.agatarauzer.myBooks.integration.H2Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.agatarauzer.myBooks.purchase.Purchase;

public interface TestH2PurchaseRepository extends CrudRepository<Purchase, Long>  {
	public Purchase findByBookId(Long bookId);
	public List<Purchase> findAll();
}
