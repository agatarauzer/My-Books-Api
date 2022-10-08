package com.agatarauzer.myBooks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.domain.Reading;
import com.agatarauzer.myBooks.domain.ReadingStatus;
import com.agatarauzer.myBooks.domain.Version;
import com.agatarauzer.myBooks.exception.BookNotFoundException;
import com.agatarauzer.myBooks.exception.ReadingNotFoundException;
import com.agatarauzer.myBooks.repository.BookRepository;
import com.agatarauzer.myBooks.repository.ReadingRepository;

@ExtendWith(MockitoExtension.class)
public class ReadingServiceTest {
	
	@InjectMocks
	private ReadingService readingService;
	
	@Mock
	private ReadingRepository readingRepository;
	
	@Mock
	private BookRepository bookRepository;
	
	private Long bookId;
	private Book book;
	private Long readingId;
	private Reading reading;
	
	@BeforeEach
	public void prepareTestData() {
		bookId = 1L;
		book = new Book(bookId, "Java. Podstawy. Wydanie IX", "Cay S. Horstmann,Gary Cornell", "8324677615, 9788324677610", "Helion", "2013-12-09", "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api", Version.PAPER, 1);
		
		readingId = 1L;
		reading = new Reading(readingId, ReadingStatus.READED, LocalDate.of(2020, 5, 9), LocalDate.of(2022, 1, 24), 820, 4, "Java basics...");
	}
	
	@Test
	public void shouldGetReadingForBook() {
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		when(readingRepository.findByBookId(bookId)).thenReturn(reading);
		
		Reading foundedReading = readingService.getReadingForBook(bookId);
		
		assertEquals(reading, foundedReading);
	}
	
	@Test
	public void shouldSaveReading() {
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		when(readingRepository.save(reading)).thenReturn(reading);
		
		Reading savedReading = readingService.saveReading(bookId, reading);
		
		assertEquals(reading, savedReading);
	}
	
	@Test
	public void shouldUpdateReading() {
		Reading readingUpdated = new Reading(readingId, ReadingStatus.LEFT, LocalDate.of(2020, 5, 9), null, 35, 2, "Boring!");
		when(readingRepository.findById(readingId)).thenReturn(Optional.of(reading));
		when(readingRepository.save(any(Reading.class))).thenReturn(readingUpdated);
		
		Reading readingAfterUpdate = readingService.updateReading(readingId, readingUpdated);
		
		assertEquals(readingUpdated, readingAfterUpdate);
	}
	
	@Test
	public void shouldDeleteReading() {
		doNothing().when(readingRepository).deleteById(readingId);
		
		readingService.deleteReading(readingId);
		
		verify(readingRepository, times(1)).deleteById(readingId);
	}
	
	@Test
	public void shouldThrowException_getReadingForBook() {
		doThrow(new BookNotFoundException()).when(bookRepository).findById(bookId);
		
		assertThrows(BookNotFoundException.class, ()-> readingService.getReadingForBook(bookId));
		verify(bookRepository, times(1)).findById(bookId);
	}
	
	@Test
	public void shouldThrowException_saveReading() {
		doThrow(new BookNotFoundException()).when(bookRepository).findById(bookId);
		
		assertThrows(BookNotFoundException.class, ()-> readingService.saveReading(bookId, reading));
		verify(bookRepository, times(1)).findById(bookId);
	}
	
	@Test
	public void shouldThrowException_updateReading() {
		doThrow(new ReadingNotFoundException()).when(readingRepository).findById(readingId);
		
		assertThrows(ReadingNotFoundException.class, ()-> readingService.updateReading(readingId, new Reading()));
		verify(readingRepository, times(1)).findById(readingId);
	}
	
	@Test
	public void shouldThrowException_deleteReading() {
		doThrow(new ReadingNotFoundException()).when(readingRepository).deleteById(readingId);
		
		assertThrows(ReadingNotFoundException.class, ()-> readingService.deleteReading(readingId));
		verify(readingRepository, times(1)).deleteById(readingId);
	}
}
