package com.agatarauzer.myBooks.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.exception.BookAlreadyExistsException;
import com.agatarauzer.myBooks.exception.notFound.BookNotFoundException;
import com.agatarauzer.myBooks.exception.notFound.UserNotFoundException;
import com.agatarauzer.myBooks.repository.BookRepository;
import com.agatarauzer.myBooks.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

	public List<Book> findBooksByUser(Long userId, int page, int size, String sortBy, String sortDir) {
		userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("User id not found: " + userId));
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(page, size, sort);
		
		return bookRepository.findByUserId(userId, pageable);		
	}
	
	public Book saveBookForUser(Long userId, Book book) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("User id not found: " + userId));
		
		Optional<Book> bookInDb = bookRepository.findByTitle(book.getTitle());
		if (bookInDb.isPresent()) {
			throw new BookAlreadyExistsException("Book title is already on your list");
		}
		book.setUser(user);
		book.setCreated(LocalDate.now());
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
		log.info("Book with id: " + bookId + " was updated");
		return bookRepository.save(bookUpdated);
	}

	public void deleteBook(Long bookId) {
		bookRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		bookRepository.deleteById(bookId);
		log.info("Book with id: " + bookId + " was deleted");
	}
}