package com.agatarauzer.myBooks.unit.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.agatarauzer.myBooks.book.BookService;
import com.agatarauzer.myBooks.book.domain.Book;
import com.agatarauzer.myBooks.book.domain.Version;
import com.agatarauzer.myBooks.purchase.Purchase;
import com.agatarauzer.myBooks.purchase.PurchaseDto;
import com.agatarauzer.myBooks.purchase.PurchaseMapper;

@SpringBootTest
public class PurchaseMapperTest {
	
	@Autowired
	PurchaseMapper purchaseMapper;
	
	@Autowired
	private BookService bookService;
	
	private Purchase purchase;
	private PurchaseDto purchaseDto;
	private Book book;

	
	@BeforeEach
	public void prepareTestData() {
		book = Book.builder()
				.id(1L)
				.title("Java. Podstawy. Wydanie IX")
				.authors("Cay S. Horstmann,Gary Cornell")
				.isbn("8324677615, 9788324677610")
				.publisher("Helion")
				.publishingDate("2013-12-09")
				.language("pl")
				.pages(864)
				.description("Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...")
				.imageLink("http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api")
				.version(Version.BOOK)
				.build();
		purchase = Purchase.builder()
				.id(1L)
				.price(49.99)
				.purchaseDate(LocalDate.of(2022, 6, 21))
				.boughtFrom("taniaksiazka.pl")
				.build();
		purchaseDto = PurchaseDto.builder()
				.id(1L)
				.price(49.99)
				.purchaseDate(LocalDate.of(2022, 6, 21))
				.boughtFrom("taniaksiazka.pl")
				.build();
	}
	
	@Test
	public void shouldMapToPurchaseDto() {
		PurchaseDto purchaseDtoMapped = purchaseMapper.mapToPurchaseDto(purchase);
		
		assertEquals(purchase.getId(), purchaseDtoMapped.getId());
		assertEquals(purchase.getPrice(), purchaseDtoMapped.getPrice());
		assertEquals(purchase.getPurchaseDate(), purchaseDtoMapped.getPurchaseDate());
		assertEquals(purchase.getBoughtFrom(), purchaseDtoMapped.getBoughtFrom());
	}
	
	@Test
	public void shouldMapToPurchase() {
		when(bookService.findBookById(1L)).thenReturn(book);
		
		Purchase purchaseMapped = purchaseMapper.mapToPurchase(purchaseDto);
		
		assertEquals(purchaseDto.getId(), purchaseMapped.getId());
		assertEquals(purchaseDto.getPrice(), purchaseMapped.getPrice());
		assertEquals(purchaseDto.getPurchaseDate(), purchaseMapped.getPurchaseDate());
		assertEquals(purchaseDto.getBoughtFrom(), purchaseMapped.getBoughtFrom());
	}
}
