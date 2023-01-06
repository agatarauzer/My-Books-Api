package com.agatarauzer.myBooks.integration.H2Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.agatarauzer.myBooks.rental.domain.Rental;

public interface TestH2RentalRepository extends CrudRepository<Rental, Long>  {
	public Rental findByBookId(Long bookId);
	public List<Rental> findAll();
}
