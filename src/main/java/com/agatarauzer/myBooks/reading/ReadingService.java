package com.agatarauzer.myBooks.reading;


import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.book.BookService;
import com.agatarauzer.myBooks.book.domain.Book;
import com.agatarauzer.myBooks.exception.notFound.ReadingNotFoundException;
import com.agatarauzer.myBooks.reading.domain.Reading;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadingService {
	
	private final ReadingRepository readingRepository;
	private final BookService bookService;
	
	public Reading getReadingForBook(Long bookId) {
		return readingRepository.findByBookId(bookId).orElseThrow(() -> new ReadingNotFoundException("Reading for book: " + bookId + "not found"));
	}
	
	public Reading saveReadingForBook(Long bookId, Reading reading) {
		Book book = bookService.findBookById(bookId);
		reading.setBook(book);
		readingRepository.save(reading);
		log.info("Reading for book with id: " + reading.getBook().getId() + " was saved in db");
		return reading;
	}
	
	public Reading updateReading(Reading reading) {
		Reading readingUpdated = readingRepository.findById(reading.getId())
			.orElseThrow(() -> new ReadingNotFoundException("Reading id not found: " + reading.getId()));
		readingUpdated.setStatus(reading.getStatus());
		readingUpdated.setStartDate(reading.getStartDate());
		readingUpdated.setEndDate(reading.getEndDate());
		readingUpdated.setReadedPages(reading.getReadedPages());
		readingUpdated.setRate(reading.getRate());
		readingUpdated.setNotes(reading.getNotes());
		log.info("Reading with id: " + reading.getId() + "was updated");
		return readingRepository.save(readingUpdated);
	}
	
	public void deleteReading(Long readingId) {
		readingRepository.findById(readingId)
			.orElseThrow(() -> new ReadingNotFoundException("Reading id not found: " + readingId));
		readingRepository.deleteById(readingId);
		log.info("Reading with id: " + readingId  + " was deleted");
	}
}
