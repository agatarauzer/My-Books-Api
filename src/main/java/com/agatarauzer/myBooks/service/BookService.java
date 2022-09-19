package com.agatarauzer.myBooks.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	private BookRepository bookRepository;
	
	
	public List<Book> findAll() {
		return bookRepository.findAll();
	}
	
	public Book findBookById(Long id) {
		return bookRepository.getReferenceById(id);
		
	}
	
	public void save(Book book) {
		bookRepository.save(book);
	}

	public void deleteById(long id) {
		bookRepository.deleteById(id);
	}
	
	public List<Book> findBooksByUser(Long id) {
		return bookRepository.findBooksByUserId(id);
	}
}