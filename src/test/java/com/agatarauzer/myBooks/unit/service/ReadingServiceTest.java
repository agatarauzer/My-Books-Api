package com.agatarauzer.myBooks.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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
import com.agatarauzer.myBooks.domain.enums.ReadingStatus;
import com.agatarauzer.myBooks.domain.enums.Version;
import com.agatarauzer.myBooks.exception.BookNotFoundException;
import com.agatarauzer.myBooks.exception.ReadingNotFoundException;
import com.agatarauzer.myBooks.repository.BookRepository;
import com.agatarauzer.myBooks.repository.ReadingRepository;
import com.agatarauzer.myBooks.service.ReadingService;

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
		book = Book.builder()
				.id(bookId)
				.title("Java. Podstawy. Wydanie IX_changed")
				.authors("Cay S. Horstmann,Gary Cornell_changed")
				.isbn("8324677615, 0000000000000")
				.publisher("Helion_changed")
				.publishingDate("2022-12-22")
				.language("pl_changed")
				.pages(900)
				.description("Kolejne wydanie_changed")
				.imageLink("http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api_changed")
				.version(Version.E_BOOK)
				.copies(2)
				.build();
		readingId = 1L;
		reading = Reading.builder()
				.id(readingId)
				.status(ReadingStatus.READED)
				.startDate(LocalDate.of(2020, 5, 9))
				.endDate(LocalDate.of(2022, 1, 24))
				.readedPages(820)
				.rate(4)
				.notes("Java basics...")
				.build();
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
		
		Reading savedReading = readingService.saveReading(bookId, reading);
		
		assertEquals(reading, savedReading);
	}
	
	@Test
	public void shouldUpdateReading() {
		Reading readingUpdated = Reading.builder()
				.id(readingId)
				.status(ReadingStatus.LEFT)
				.startDate(LocalDate.of(2020, 5, 9))
				.endDate(null)
				.readedPages(35)
				.rate(2)
				.notes("Boring!")
				.build();	
		when(readingRepository.findById(readingId)).thenReturn(Optional.of(reading));
		when(readingRepository.save(any(Reading.class))).thenReturn(readingUpdated);
		
		Reading readingAfterUpdate = readingService.updateReading(readingId, readingUpdated);
		
		assertEquals(readingUpdated, readingAfterUpdate);
	}
	
	@Test
	public void shouldUpdateOnlyGivenFields() {
		Reading readingUpdated = Reading.builder()
				.id(null)
				.status(ReadingStatus.LEFT)
				.startDate(null)
				.endDate(null)
				.readedPages(null)
				.rate(2)
				.notes("Boring!")
				.build();
		Reading readingExpected = Reading.builder()
				.id(readingId)
				.status(ReadingStatus.LEFT)
				.startDate(LocalDate.of(2020, 5, 9))
				.endDate(LocalDate.of(2022, 1, 24))
				.readedPages(840)
				.rate(2)
				.notes("Boring!")
				.build();
		when(readingRepository.findById(readingId)).thenReturn(Optional.of(reading));
		when(readingRepository.save(any(Reading.class))).thenReturn(readingExpected);
		
		Reading readingAfterUpdate = readingService.updateReading(readingId, readingUpdated);
		
		assertEquals(readingExpected.getId(), readingAfterUpdate.getId());
		assertEquals(readingExpected.getStatus(), readingAfterUpdate.getStatus());
		assertEquals(readingExpected.getStartDate(), readingAfterUpdate.getStartDate());
		assertEquals(readingExpected.getEndDate(), readingAfterUpdate.getEndDate());
		assertEquals(readingExpected.getReadedPages(), readingAfterUpdate.getReadedPages());
		assertEquals(readingExpected.getRate(), readingAfterUpdate.getRate());
		assertEquals(readingExpected.getNotes(), readingAfterUpdate.getNotes());
	}
	
	@Test
	public void shouldDeleteReading() {
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		
		readingService.deleteReading(bookId, readingId);
		
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
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		doThrow(new ReadingNotFoundException()).when(readingRepository).deleteById(readingId);
		
		assertThrows(ReadingNotFoundException.class, ()-> readingService.deleteReading(bookId, readingId));
		verify(readingRepository, times(1)).deleteById(readingId);
	}
}