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
	
	public List<Book> findBooksByUser(Long id) {
		return bookRepository.findBooksByUserId(id);
	}
	
	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}
	
	public Book updateBook(Long bookId, Book book) {
		Book bookUpdated = bookRepository.getReferenceById(bookId);
		bookUpdated.setTitle(book.getTitle());
		bookUpdated.setAuthors(book.getAuthors());
		bookUpdated.setISBN(book.getISBN());
		bookUpdated.setPublisher(book.getPublisher());
		bookUpdated.setPublishingDate(book.getPublishingDate());
		bookUpdated.setLanguage(book.getLanguage());
		bookUpdated.setPages(book.getPages());
		bookUpdated.setDescription(book.getDescription());
		bookUpdated.setImageLink(book.getImageLink());
		bookUpdated.setPrice(book.getPrice());
		bookUpdated.setPurchaseDate(book.getPurchaseDate());
		return saveBook(bookUpdated);
	}

	public void deleteBookById(Long id) {
		bookRepository.deleteById(id);
	}
}