package com.agatarauzer.myBooks.rental;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.rental.domain.Rental;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {
	public Rental findByBookId(Long bookId);
}
