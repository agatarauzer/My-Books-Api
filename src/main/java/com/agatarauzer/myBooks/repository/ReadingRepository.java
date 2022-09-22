package com.agatarauzer.myBooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.domain.Reading;

@Repository
public interface ReadingRepository extends JpaRepository<Reading, Long> {
	
	public Reading findByBookId(Long book_id);
}
