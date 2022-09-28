package com.agatarauzer.myBooks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.domain.Reading;

@Repository
public interface ReadingRepository extends CrudRepository<Reading, Long> {
	public Reading findByBookId(Long book_id);
}
