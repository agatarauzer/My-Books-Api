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
import com.agatarauzer.myBooks.domain.Purchase;
import com.agatarauzer.myBooks.domain.enums.Version;
import com.agatarauzer.myBooks.exception.BookNotFoundException;
import com.agatarauzer.myBooks.exception.PurchaseNotFoundException;
import com.agatarauzer.myBooks.repository.BookRepository;
import com.agatarauzer.myBooks.repository.PurchaseRepository;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {
	
	@InjectMocks
	private PurchaseService purchaseService;
	
	@Mock
	private PurchaseRepository purchaseRepository;
	
	@Mock
	private BookRepository bookRepository;
	
	private Long bookId;
	private Book book;
	private Long purchaseId;
	private Purchase purchase;
	
	@BeforeEach
	public void prepareTestData() {
		bookId = 1L;
		book = new Book(1L, "Java. Podstawy. Wydanie IX", "Cay S. Horstmann,Gary Cornell", "8324677615, 9788324677610", "Helion", "2013-12-09", "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api", Version.PAPER, 1);
		purchaseId = 1L;
		purchase = new Purchase(purchaseId, 48.90, LocalDate.of(2022, 7, 12), "empik.com");
	}
	
	@Test
	public void shouldGetPurchaseForBook() {
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		when(purchaseRepository.findByBookId(bookId)).thenReturn(purchase);
		
		Purchase foundedPurchase = purchaseService.getPurchaseForBook(bookId);
		
		assertEquals(purchase, foundedPurchase);
	}
	
	@Test
	public void shouldSavePurchase() {
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		
		Purchase savedPurchase = purchaseService.savePurchase(bookId, purchase);
		
		assertEquals(purchase, savedPurchase);
	}
	
	@Test
	public void shouldUpdatePurchase() {
		Purchase purchaseUpdated = new Purchase(purchaseId, 51.99, LocalDate.of(2020, 8, 9), "taniaksiazka.pl");
		when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));
		when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchaseUpdated);
		
		Purchase purchaseAfterUpdate = purchaseService.updatePurchase(purchaseId, purchaseUpdated);
		
		assertEquals(purchaseUpdated, purchaseAfterUpdate);
	}
	
	@Test
	public void shouldUpdateOnlyGivenFields() {
		Purchase purchaseUpdated = new Purchase(null, 51.99, null, "taniaksiazka.pl");
		Purchase purchaseExpected = new Purchase(purchaseId, 51.99, LocalDate.of(2022, 7, 12), "taniaksiazka.pl");
		
		when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));
		when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchaseExpected);
		
		Purchase purchaseAfterUpdate = purchaseService.updatePurchase(purchaseId, purchaseUpdated);
		
		assertEquals(purchaseExpected.getId(), purchaseAfterUpdate.getId());
		assertEquals(purchaseExpected.getPrice(), purchaseAfterUpdate.getPrice());
		assertEquals(purchaseExpected.getPurchaseDate(), purchaseAfterUpdate.getPurchaseDate());
		assertEquals(purchaseExpected.getBoughtFrom(), purchaseAfterUpdate.getBoughtFrom());
	}
	
	@Test
	public void shouldDeletePurchase() {
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		
		purchaseService.deletePurchase(bookId, purchaseId);
		
		verify(purchaseRepository, times(1)).deleteById(purchaseId);
	}
	
	@Test
	public void shouldThrowException_getPurchaseForBook() {
		doThrow(new BookNotFoundException()).when(bookRepository).findById(bookId);
		
		assertThrows(BookNotFoundException.class, ()-> purchaseService.getPurchaseForBook(bookId));
		verify(bookRepository, times(1)).findById(bookId);
	}
	
	@Test
	public void shouldThrowException_savePurchase() {
		doThrow(new BookNotFoundException()).when(bookRepository).findById(bookId);
		
		assertThrows(BookNotFoundException.class, ()-> purchaseService.savePurchase(bookId, purchase));
		verify(bookRepository, times(1)).findById(bookId);
	}
	
	@Test
	public void shouldThrowException_updatePurchase() {
		doThrow(new PurchaseNotFoundException()).when(purchaseRepository).findById(purchaseId);
		
		assertThrows(PurchaseNotFoundException.class, ()-> purchaseService.updatePurchase(purchaseId, new Purchase()));
		verify(purchaseRepository, times(1)).findById(purchaseId);
	}
	
	@Test
	public void shouldThrowException_deletePurchase() {
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		doThrow(new PurchaseNotFoundException()).when(purchaseRepository).deleteById(purchaseId);
		
		assertThrows(PurchaseNotFoundException.class, ()-> purchaseService.deletePurchase(bookId, purchaseId));
		verify(purchaseRepository, times(1)).deleteById(purchaseId);
	}
}
