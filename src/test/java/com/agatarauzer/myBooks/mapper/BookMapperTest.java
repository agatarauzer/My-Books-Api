package com.agatarauzer.myBooks.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.domain.Version;
import com.agatarauzer.myBooks.dto.BookDto;

@SpringBootTest
public class BookMapperTest {
	
	@Autowired
	BookMapper bookMapper;
	
	private Book book;
	private BookDto bookDto;
	
	@BeforeEach
	public void prepareTestData() {
		book = new Book(1L, "Java. Podstawy. Wydanie IX","Cay S. Horstmann,Gary Cornell", "9788324677610, 8324677615", "Helion", "2013-12-09", "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api", Version.PAPER, 1);
		bookDto = new BookDto(1L, "Java. Podstawy. Wydanie IX","Cay S. Horstmann,Gary Cornell", "9788324677610, 8324677615", "Helion", "2013-12-09", "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
				Version.PAPER, 1);
	}
	
	@Test
	public void shouldMapBookDtoToBook() {
		Book bookMapped = bookMapper.mapToBook(bookDto);

		assertEquals(bookDto.getTitle(), bookMapped.getTitle());
		assertEquals(bookDto.getAuthors(), bookMapped.getAuthors());
		assertEquals(bookDto.getISBN(), bookMapped.getISBN());
		assertEquals(bookDto.getPublisher(), bookMapped.getPublisher());
		assertEquals(bookDto.getPublishingDate(), bookMapped.getPublishingDate());
		assertEquals(bookDto.getLanguage(), bookMapped.getLanguage());
		assertEquals(bookDto.getPages(), bookMapped.getPages());
		assertEquals(bookDto.getDescription(), bookMapped.getDescription());
		assertEquals(bookDto.getVersion(), bookMapped.getVersion());
		assertEquals(bookDto.getCopies(), bookMapped.getCopies());
	}
	
	@Test
	public void sholudMapBookDtoToBook() {
		BookDto bookDtoMapped = bookMapper.mapToBookDto(book);
		
		assertEquals(book.getTitle(), bookDtoMapped.getTitle());
		assertEquals(book.getAuthors(), bookDtoMapped.getAuthors());
		assertEquals(book.getISBN(), bookDtoMapped.getISBN());
		assertEquals(book.getPublisher(), bookDtoMapped.getPublisher());
		assertEquals(book.getPublishingDate(), bookDtoMapped.getPublishingDate());
		assertEquals(book.getLanguage(), bookDtoMapped.getLanguage());
		assertEquals(book.getPages(), bookDtoMapped.getPages());
		assertEquals(book.getDescription(), bookDtoMapped.getDescription());
		assertEquals(book.getVersion(), bookDtoMapped.getVersion());
		assertEquals(book.getCopies(), bookDtoMapped.getCopies());
	}
	
	@Test
	public void sholudMapBookListToBookDtoList() {
		List<Book> books = List.of(book);
		
		List<BookDto> booksDtoMapped = bookMapper.mapToBookDtoList(books);
		
		assertEquals(books.get(0).getTitle(), booksDtoMapped.get(0).getTitle());
		assertEquals(books.get(0).getAuthors(), booksDtoMapped.get(0).getAuthors());
		assertEquals(books.get(0).getISBN(), booksDtoMapped.get(0).getISBN());
		assertEquals(books.get(0).getPublisher(), booksDtoMapped.get(0).getPublisher());
		assertEquals(books.get(0).getPublishingDate(), booksDtoMapped.get(0).getPublishingDate());
		assertEquals(books.get(0).getLanguage(), booksDtoMapped.get(0).getLanguage());
		assertEquals(books.get(0).getPages(), booksDtoMapped.get(0).getPages());
		assertEquals(books.get(0).getDescription(), booksDtoMapped.get(0).getDescription());
		assertEquals(books.get(0).getVersion(), booksDtoMapped.get(0).getVersion());
		assertEquals(books.get(0).getCopies(), booksDtoMapped.get(0).getCopies());
	}
}
