package com.agatarauzer.myBooks.integration.H2Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.agatarauzer.myBooks.book.domain.Book;

public interface TestH2BookRepository extends CrudRepository<Book, Long> {
	public List<Book> findByUserId(Long userId);

}
