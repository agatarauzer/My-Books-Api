package com.agatarauzer.myBooks.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.domain.Reading;
import com.agatarauzer.myBooks.exception.BookNotFoundException;
import com.agatarauzer.myBooks.exception.ReadingNotFoundException;
import com.agatarauzer.myBooks.repository.BookRepository;
import com.agatarauzer.myBooks.repository.ReadingRepository;

@Service
public class ReadingService {
	
	@Autowired
	private ReadingRepository readingRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	
	public Reading getReadingForBook(Long bookId) {
		bookRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		return readingRepository.findByBookId(bookId);
	}
	
	public Reading saveReading(Long bookId, Reading reading) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		reading.setBook(book);
		return readingRepository.save(reading);
	}
	
	public Reading updateReading(Long readingId, Reading reading) {
		Reading readingUpdated = readingRepository.findById(readingId)
				.orElseThrow(() -> new ReadingNotFoundException("Reading id not found: " + readingId));
		readingUpdated.setStatus(reading.getStatus());
		readingUpdated.setStartDate(reading.getStartDate());
		readingUpdated.setEndDate(reading.getEndDate());
		readingUpdated.setReadedPages(reading.getReadedPages());
		readingUpdated.setRate(reading.getRate());
		readingUpdated.setNotes(reading.getNotes());
		return readingRepository.save(readingUpdated);
	}
	
	public void deleteReading(Long readingId) {
		try {
			readingRepository.deleteById(readingId);
		} catch (DataAccessException exc) {
			throw new ReadingNotFoundException("Reading id not found: " + readingId);
		}
	}
}
