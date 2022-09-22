package com.agatarauzer.myBooks.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.domain.Reading;
import com.agatarauzer.myBooks.repository.BookRepository;
import com.agatarauzer.myBooks.repository.ReadingRepository;

@Service
public class ReadingService {
	
	@Autowired
	private ReadingRepository readingRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	
	public Reading getReadingForBook(Long bookId) {
		return readingRepository.findByBookId(bookId);
	}
	
	public Reading saveReading(Long bookId, Reading reading) {
		Book book = bookRepository.getReferenceById(bookId);
		reading.setBook(book);
		return readingRepository.save(reading);
	}
	
	public Reading updateReading(Long readingId, Reading reading) {
		Reading readingUpdated = readingRepository.getReferenceById(readingId);
		readingUpdated.setStatus(reading.getStatus());
		readingUpdated.setStartDate(reading.getStartDate());
		readingUpdated.setEndDate(reading.getEndDate());
		readingUpdated.setReadedPages(reading.getReadedPages());
		readingUpdated.setRate(reading.getRate());
		readingUpdated.setNotes(reading.getNotes());
		return readingRepository.save(readingUpdated);
	}
	
	public void deleteById(Long id) {
		readingRepository.deleteById(id);
	}
}
