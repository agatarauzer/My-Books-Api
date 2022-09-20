package com.agatarauzer.myBooks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agatarauzer.myBooks.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	public List<Book> findByUserId(Long userId);
}
