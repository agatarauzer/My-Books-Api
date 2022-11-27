package com.agatarauzer.myBooks.service;


import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.domain.Reading;
import com.agatarauzer.myBooks.exception.notFound.BookNotFoundException;
import com.agatarauzer.myBooks.exception.notFound.ReadingNotFoundException;
import com.agatarauzer.myBooks.repository.BookRepository;
import com.agatarauzer.myBooks.repository.ReadingRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadingService {
	
	private final ReadingRepository readingRepository;
	private final BookRepository bookRepository;
	
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
		log.info("Reading for book with id: " + bookId + " was saved in db");
		return reading;
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
		log.info("Reading with id: " + readingId + "was updated");
		return readingRepository.save(readingUpdated);
	}
	
	public void deleteReading(Long bookId, Long readingId) {
		Book book = bookRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		book.setReading(null);
		readingRepository.findById(readingId)
			.orElseThrow(() -> new ReadingNotFoundException("Reading id not found: " + readingId));
		readingRepository.deleteById(readingId);
		log.info("Reading with id: " + readingId  + " was deleted");
	}
}
