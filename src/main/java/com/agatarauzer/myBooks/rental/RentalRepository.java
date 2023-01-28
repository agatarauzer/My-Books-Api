package com.agatarauzer.myBooks.rental;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.rental.domain.Rental;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {
	public Optional<List<Rental>> findByBookId(Long bookId);
}
