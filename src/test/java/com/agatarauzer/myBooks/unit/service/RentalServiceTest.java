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

import com.agatarauzer.myBooks.book.Book;
import com.agatarauzer.myBooks.book.BookRepository;
import com.agatarauzer.myBooks.book.Version;
import com.agatarauzer.myBooks.exception.notFound.BookNotFoundException;
import com.agatarauzer.myBooks.exception.notFound.RentalNotFoundException;
import com.agatarauzer.myBooks.rental.Rental;
import com.agatarauzer.myBooks.rental.RentalRepository;
import com.agatarauzer.myBooks.rental.RentalService;
import com.agatarauzer.myBooks.rental.RentalStatus;

@ExtendWith(MockitoExtension.class)
public class RentalServiceTest {
	
	@InjectMocks
	private RentalService rentalService;
	
	@Mock
	private RentalRepository rentalRepository;
	
	@Mock
	private BookRepository bookRepository;
	
	private Long bookId;
	private Book book;
	private Long rentalId;
	private Rental rental;
	
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
		
		rentalId = 1L;
		rental = Rental.builder()
				.id(1L)
				.status(RentalStatus.BORROWED_FROM)
				.name("from Kate")
				.startDate(LocalDate.of(2022, 6, 21))
				.endDate(LocalDate.of(2023, 1, 5))
				.notes("Kate will need it in January!")
				.build();
	}
	
	@Test
	public void shouldGetRentalForBook() {
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		when(rentalRepository.findByBookId(bookId)).thenReturn(rental);
		
		Rental foundedRental = rentalService.getRentalForBook(bookId);
		
		assertEquals(rental, foundedRental);
	}
	
	@Test
	public void shouldSaveRental() {
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		
		Rental savedRental = rentalService.saveRental(bookId, rental);
		
		assertEquals(rental, savedRental);
	}
	
	@Test
	public void shouldUpdateRental() {
		Rental rentalUpdated = Rental.builder()
				.id(rentalId)
				.status(RentalStatus.LENDED_TO)
				.name("to Kate")
				.startDate(LocalDate.of(2020, 5, 9))
				.endDate(LocalDate.of(2021, 8, 9))
				.notes("Not needed now")
				.build();
		when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));
		when(rentalRepository.save(any(Rental.class))).thenReturn(rentalUpdated);
		
		Rental rentalAfterUpdate = rentalService.updateRental(rentalId, rentalUpdated);
		
		assertEquals(rentalUpdated, rentalAfterUpdate);
	}
	
	@Test
	public void shouldDeleteRental() {
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));
		
		rentalService.deleteRental(bookId, rentalId);
		
		verify(rentalRepository, times(1)).deleteById(rentalId);
	}
	
	@Test
	public void shouldThrowException_getRentalForBook() {
		doThrow(new BookNotFoundException()).when(bookRepository).findById(bookId);
		
		assertThrows(BookNotFoundException.class, ()-> rentalService.getRentalForBook(bookId));
		verify(bookRepository, times(1)).findById(bookId);
	}
	
	@Test
	public void shouldThrowException_saveRental() {
		doThrow(new BookNotFoundException()).when(bookRepository).findById(bookId);
		
		assertThrows(BookNotFoundException.class, ()-> rentalService.saveRental(bookId, rental));
		verify(bookRepository, times(1)).findById(bookId);
	}
	
	@Test
	public void shouldThrowException_updateRental() {
		doThrow(new RentalNotFoundException()).when(rentalRepository).findById(rentalId);
		
		assertThrows(RentalNotFoundException.class, ()-> rentalService.updateRental(rentalId, new Rental()));
		verify(rentalRepository, times(1)).findById(rentalId);
	}
	
	@Test
	public void shouldThrowException_deleteRental() {
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		doThrow(new RentalNotFoundException()).when(rentalRepository).findById(rentalId);
		
		assertThrows(RentalNotFoundException.class, ()-> rentalService.deleteRental(bookId, rentalId));
		verify(rentalRepository, times(0)).deleteById(rentalId);
	}
}
