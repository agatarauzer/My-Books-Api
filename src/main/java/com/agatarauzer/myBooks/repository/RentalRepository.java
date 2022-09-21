package com.agatarauzer.myBooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.domain.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
	
	public Rental findByBookId(Long bookId);
}
