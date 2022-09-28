package com.agatarauzer.myBooks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.domain.Rental;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {
	public Rental findByBookId(Long bookId);
}
