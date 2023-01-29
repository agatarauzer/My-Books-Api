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

import com.agatarauzer.myBooks.book.BookService;
import com.agatarauzer.myBooks.book.domain.Book;
import com.agatarauzer.myBooks.book.domain.Version;
import com.agatarauzer.myBooks.exception.notFound.PurchaseNotFoundException;
import com.agatarauzer.myBooks.purchase.Purchase;
import com.agatarauzer.myBooks.purchase.PurchaseRepository;
import com.agatarauzer.myBooks.purchase.PurchaseService;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {
	
	@InjectMocks
	private PurchaseService purchaseService;
	
	@Mock
	private PurchaseRepository purchaseRepository;
	
	@Mock
	private BookService bookService;
	
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
				.build();
		purchaseId = 1L;
		purchase =  Purchase.builder()
				.id(purchaseId)
				.price(48.90)
				.purchaseDate(LocalDate.of(2022, 7, 12))
				.boughtFrom("empik.com")
				.book(book)
				.build();
	}
	
	@Test
	public void shouldGetPurchaseForBook() {
		when(purchaseRepository.findByBookId(bookId)).thenReturn(Optional.of(purchase));
		
		Purchase foundedPurchase = purchaseService.getPurchaseForBook(bookId);
		
		assertEquals(purchase, foundedPurchase);
	}
	
	@Test
	public void shouldSavePurchase() {
		when(bookService.findBookById(bookId)).thenReturn(book);
		when(purchaseRepository.save(purchase)).thenReturn(purchase);
		
		Purchase savedPurchase = purchaseService.savePurchaseForBook(purchase);
		
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
		
		Purchase purchaseAfterUpdate = purchaseService.updatePurchase(purchaseUpdated);
		
		assertEquals(purchaseUpdated, purchaseAfterUpdate);
	}
	
	@Test
	public void shouldDeletePurchase() {
		when(purchaseRepository.findById(purchaseId)).thenReturn(Optional.of(purchase));
		
		purchaseService.deletePurchase(purchaseId);
		
		verify(purchaseRepository, times(1)).deleteById(purchaseId);
	}
	
	@Test
	public void shouldThrowException_getPurchaseForBook() {
		doThrow(new PurchaseNotFoundException()).when(purchaseRepository).findByBookId(bookId);
		
		assertThrows(PurchaseNotFoundException.class, ()-> purchaseService.getPurchaseForBook(bookId));
		verify(purchaseRepository, times(1)).findByBookId(bookId);
	}
	
	@Test
	public void shouldThrowException_updatePurchase() {
		doThrow(new PurchaseNotFoundException()).when(purchaseRepository).findById(purchaseId);
		
		assertThrows(PurchaseNotFoundException.class, ()-> purchaseService.updatePurchase(purchase));
		verify(purchaseRepository, times(1)).findById(purchaseId);
	}
	
	@Test
	public void shouldThrowException_deletePurchase() {
		doThrow(new PurchaseNotFoundException()).when(purchaseRepository).findById(purchaseId);
		
		assertThrows(PurchaseNotFoundException.class, ()-> purchaseService.deletePurchase(purchaseId));
		verify(purchaseRepository, times(0)).deleteById(purchaseId);
	}
}
