package com.agatarauzer.myBooks.book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
	public List<Book> findByUserId(Long userId, Pageable pageable);
	public Optional<Book> findByTitleAndUserId(String title, Long userId);
	public List<Book>findAllByUserIdIn(List<Long> ids);
	
	@Query(value = "SELECT ROUND(COUNT(b.book_id) / COUNT(DISTINCT b.user_id), 2) as Average"
			+ "FROM books b ", nativeQuery = true)
	public Double findAvgBooksForUser();
}
