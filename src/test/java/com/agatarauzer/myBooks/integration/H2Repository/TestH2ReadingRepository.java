package com.agatarauzer.myBooks.integration.H2Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.agatarauzer.myBooks.reading.domain.Reading;

public interface TestH2ReadingRepository extends CrudRepository<Reading, Long>  {
	public Reading findByBookId(Long bookId);
	public List<Reading> findAll();
}
