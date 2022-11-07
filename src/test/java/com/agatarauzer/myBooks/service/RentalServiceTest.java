package com.agatarauzer.myBooks.service;

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
import com.agatarauzer.myBooks.domain.Rental;
import com.agatarauzer.myBooks.domain.enums.RentalStatus;
import com.agatarauzer.myBooks.domain.enums.Version;
import com.agatarauzer.myBooks.exception.BookNotFoundException;
import com.agatarauzer.myBooks.exception.RentalNotFoundException;
import com.agatarauzer.myBooks.repository.BookRepository;
import com.agatarauzer.myBooks.repository.RentalRepository;

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
		book = new Book(bookId, "Java. Podstawy. Wydanie IX", "Cay S. Horstmann,Gary Cornell", "8324677615, 9788324677610", "Helion", "2013-12-09", "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api", Version.PAPER, 1);
		
		rentalId = 1L;
		rental = new Rental(rentalId, RentalStatus.BORROWED, "from Kate", LocalDate.of(2022, 6, 21), LocalDate.of(2023, 1, 5), "Kate will need it in January!");
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
		Rental rentalUpdated = new Rental(rentalId, RentalStatus.LENDED, "to Kate", LocalDate.of(2020, 5, 9), LocalDate.of(2021, 8, 9), "Not needed now");
		when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));
		when(rentalRepository.save(any(Rental.class))).thenReturn(rentalUpdated);
		
		Rental rentalAfterUpdate = rentalService.updateRental(rentalId, rentalUpdated);
		
		assertEquals(rentalUpdated, rentalAfterUpdate);
	}
	
	@Test
	public void shouldUpdateOnlyGivenFields() {
		Rental rentalUpdated = new Rental(null, RentalStatus.LENDED, null, LocalDate.of(2020, 5, 9), null, null);
		Rental rentalExpected = new Rental(rentalId, RentalStatus.LENDED, "from Kate", LocalDate.of(2020, 5, 9), LocalDate.of(2023, 1, 5), "Kate will need it in January!");
		
		when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rental));
		when(rentalRepository.save(any(Rental.class))).thenReturn(rentalExpected);
		
		Rental rentalAfterUpdate = rentalService.updateRental(rentalId, rentalUpdated);
			
		assertEquals(rentalExpected.getId(), rentalAfterUpdate.getId());
		assertEquals(rentalExpected.getStatus(), rentalAfterUpdate.getStatus());
		assertEquals(rentalExpected.getName(), rentalAfterUpdate.getName());
		assertEquals(rentalExpected.getStartDate(), rentalAfterUpdate.getStartDate());
		assertEquals(rentalExpected.getEndDate(), rentalAfterUpdate.getEndDate());
		assertEquals(rentalExpected.getNotes(), rentalAfterUpdate.getNotes());
	}
	
	@Test
	public void shouldDeleteRental() {
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		
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
		doThrow(new RentalNotFoundException()).when(rentalRepository).deleteById(rentalId);
		
		assertThrows(RentalNotFoundException.class, ()-> rentalService.deleteRental(bookId, rentalId));
		verify(rentalRepository, times(1)).deleteById(rentalId);
	}
}
