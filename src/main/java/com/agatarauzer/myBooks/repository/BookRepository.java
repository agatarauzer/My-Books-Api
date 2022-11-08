package com.agatarauzer.myBooks.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.domain.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
	public List<Book> findByUserId(Long userId);
	public Optional<Book> findByTitle(String title);
	
	@Query(value = "SELECT ROUND(COUNT(b.book_id) / COUNT(DISTINCT b.user_id), 2) as Average"
			+ "FROM books b ", nativeQuery = true)
	public Double findAvgBooksForUser();
}
