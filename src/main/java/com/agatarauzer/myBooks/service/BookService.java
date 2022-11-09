package com.agatarauzer.myBooks.service;


import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.exception.BookNotFoundException;
import com.agatarauzer.myBooks.exception.UserNotFoundException;
import com.agatarauzer.myBooks.repository.BookRepository;
import com.agatarauzer.myBooks.repository.UserRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class BookService {
	
	private final BookRepository bookRepository;
	private final UserRepository userRepository;
	
	public List<Book> findAll() {
		return IterableUtils.toList(bookRepository.findAll());		
	}
	
	public Book findBookById(Long bookId) {
		return bookRepository.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
	}
	
	public List<Book> findBooksByUser(Long userId) {
		userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User id not found: " + userId));
		return bookRepository.findByUserId(userId);		
	}
	
	public Book saveBookForUser(Long userId, Book book) {
		User user = userRepository.findById(userId)
					.orElseThrow(() -> new UserNotFoundException("User id not found: " + userId));
		
		//Optional<Book> bookInDb = bookRepository.findByTitle(book.getTitle());
		//if (bookInDb.isPresent()) {
			//return new MessageResponse("Title has been already added to your account.");
		//}
		book.setUser(user);
		return bookRepository.save(book);
	}

	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}
	
	public Book updateBook(Long bookId, Book book) {
		Book bookUpdated = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		bookUpdated.setTitle(book.getTitle());
		bookUpdated.setAuthors(book.getAuthors());
		bookUpdated.setIsbn(book.getIsbn());
		bookUpdated.setPublisher(book.getPublisher());
		bookUpdated.setPublishingDate(book.getPublishingDate());
		bookUpdated.setLanguage(book.getLanguage());
		bookUpdated.setPages(book.getPages());
		bookUpdated.setDescription(book.getDescription());
		bookUpdated.setImageLink(book.getImageLink());
		bookUpdated.setVersion(book.getVersion());
		bookUpdated.setCopies(book.getCopies());
		return saveBook(bookUpdated);
	}

	public void deleteBook(Long bookId) {
		try {
			bookRepository.deleteById(bookId);
		} catch (DataAccessException exc) {
			throw new BookNotFoundException("Book id not found: " + bookId);
		}
	}
}