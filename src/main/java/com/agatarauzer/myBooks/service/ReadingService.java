package com.agatarauzer.myBooks.service;


import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.domain.Reading;
import com.agatarauzer.myBooks.domain.ReadingStatus;
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
		book.setReading(reading);
		bookRepository.save(book);
		return reading;
	}
	
	public Reading updateReading(Long readingId, Reading reading) {
		Reading readingUpdated = readingRepository.findById(readingId)
				.orElseThrow(() -> new ReadingNotFoundException("Reading id not found: " + readingId));
		
		ReadingStatus statusUpdated = Optional.ofNullable(reading.getStatus()).orElse(readingUpdated.getStatus());
		LocalDate startDateUpdated = Optional.ofNullable(reading.getStartDate()).orElse(readingUpdated.getStartDate());
		LocalDate endDateUpdated = Optional.ofNullable(reading.getEndDate()).orElse(readingUpdated.getEndDate());
		int readedPagesUpdated = Optional.ofNullable(reading.getReadedPages()).orElse(readingUpdated.getReadedPages());
		int rateUpdated = Optional.ofNullable(reading.getRate()).orElse(readingUpdated.getRate());
		String notesUpdated = Optional.ofNullable(reading.getNotes()).orElse(readingUpdated.getNotes());
		
		readingUpdated.setStatus(statusUpdated);
		readingUpdated.setStartDate(startDateUpdated);
		readingUpdated.setEndDate(endDateUpdated);
		readingUpdated.setReadedPages(readedPagesUpdated);
		readingUpdated.setRate(rateUpdated);
		readingUpdated.setNotes(notesUpdated);
		
		return readingRepository.save(readingUpdated);
	}
	
	public void deleteReading(Long bookId, Long readingId) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		book.setReading(null);
		try {
			readingRepository.deleteById(readingId);
		} catch (DataAccessException exc) {
			throw new ReadingNotFoundException("Reading id not found: " + readingId);
		}
	}
}
