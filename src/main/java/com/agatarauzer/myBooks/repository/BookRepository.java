package com.agatarauzer.myBooks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	@Query(value = "SELECT * FROM book b WHERE b.user_id = :userId", 
			nativeQuery = true)
	public List<Book> findBooksByUserId(@Param("userId") Long userId);
}
