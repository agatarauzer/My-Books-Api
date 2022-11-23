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
import com.agatarauzer.myBooks.domain.Purchase;
import com.agatarauzer.myBooks.domain.enums.Version;
import com.agatarauzer.myBooks.exception.BookNotFoundException;
import com.agatarauzer.myBooks.exception.PurchaseNotFoundException;
import com.agatarauzer.myBooks.repository.BookRepository;
import com.agatarauzer.myBooks.repository.PurchaseRepository;
import com.agatarauzer.myBooks.service.PurchaseService;

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
		purchaseId = 1L;
		purchase =  Purchase.builder()
				.id(purchaseId)
				.price(48.90)
				.purchaseDate(LocalDate.of(2022, 7, 12))
				.boughtFrom("empik.com")
				.build();
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
		Purchase purchaseUpdated =  Purchase.builder()
				.id(purchaseId)
				.price(51.99)
				.purchaseDate(LocalDate.of(2020, 8, 9))
				.boughtFrom("taniaksiazka.pl")
				.build();
		when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));
		when(purchaseRepository.save(any(Purchase.class))).thenReturn(purchaseUpdated);
		
		Purchase purchaseAfterUpdate = purchaseService.updatePurchase(purchaseId, purchaseUpdated);
		
		assertEquals(purchaseUpdated, purchaseAfterUpdate);
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
