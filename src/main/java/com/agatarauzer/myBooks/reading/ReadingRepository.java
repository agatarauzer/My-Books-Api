package com.agatarauzer.myBooks.reading;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingRepository extends CrudRepository<Reading, Long> {
	public Reading findByBookId(Long bookId);
}
