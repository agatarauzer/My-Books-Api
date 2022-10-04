package com.agatarauzer.myBooks.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

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
	
	@Test
	public void shouldMapBookDtoToBook() {
		BookDto bookDto = new BookDto(1L, "Java. Podstawy. Wydanie IX","Cay S. Horstmann,Gary Cornell", "9788324677610, 8324677615", "Helion", "2013-12-09", "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
				Version.PAPER, 1);
		
		Book book = bookMapper.mapToBook(bookDto);

		assertEquals(book.getTitle(), bookDto.getTitle());
		assertEquals(book.getAuthors(), bookDto.getAuthors());
		assertEquals(book.getISBN(), bookDto.getISBN());
		assertEquals(book.getPublisher(), bookDto.getPublisher());
		assertEquals(book.getPublishingDate(), bookDto.getPublishingDate());
		assertEquals(book.getLanguage(), bookDto.getLanguage());
		assertEquals(book.getPages(), bookDto.getPages());
		assertEquals(book.getDescription(), bookDto.getDescription());
		assertEquals(book.getVersion(), bookDto.getVersion());
		assertEquals(book.getCopies(), bookDto.getCopies());
	}
	
	@Test
	public void sholudMapBookDtoToBook() {
		Book book = new Book(1L, "Java. Podstawy. Wydanie IX","Cay S. Horstmann,Gary Cornell", "9788324677610, 8324677615", "Helion", "2013-12-09", "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
			  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api", Version.PAPER, 1);
		
		BookDto bookDto = bookMapper.mapToBookDto(book);
		
		assertEquals(bookDto.getTitle(), book.getTitle());
		assertEquals(bookDto.getAuthors(), book.getAuthors());
		assertEquals(bookDto.getISBN(), book.getISBN());
		assertEquals(bookDto.getPublisher(), book.getPublisher());
		assertEquals(bookDto.getPublishingDate(), book.getPublishingDate());
		assertEquals(bookDto.getLanguage(), book.getLanguage());
		assertEquals(bookDto.getPages(), book.getPages());
		assertEquals(bookDto.getDescription(), book.getDescription());
		assertEquals(bookDto.getVersion(), book.getVersion());
		assertEquals(bookDto.getCopies(), book.getCopies());
	}
	
	@Test
	public void sholudMapBookListToBookDtoList() {
		Book book = new Book(1L, "Java. Podstawy. Wydanie IX","Cay S. Horstmann,Gary Cornell", "9788324677610, 8324677615", "Helion", "2013-12-09", "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",Version.PAPER, 1);
		List<Book> books = List.of(book);
		
		List<BookDto> booksDto = bookMapper.mapToBookDtoList(books);
		
		assertEquals(booksDto.get(0).getTitle(), books.get(0).getTitle());
		assertEquals(booksDto.get(0).getAuthors(), books.get(0).getAuthors());
		assertEquals(booksDto.get(0).getISBN(), books.get(0).getISBN());
		assertEquals(booksDto.get(0).getPublisher(), books.get(0).getPublisher());
		assertEquals(booksDto.get(0).getPublishingDate(), books.get(0).getPublishingDate());
		assertEquals(booksDto.get(0).getLanguage(), books.get(0).getLanguage());
		assertEquals(booksDto.get(0).getPages(), books.get(0).getPages());
		assertEquals(booksDto.get(0).getDescription(), books.get(0).getDescription());
		assertEquals(booksDto.get(0).getVersion(), books.get(0).getVersion());
		assertEquals(booksDto.get(0).getCopies(), books.get(0).getCopies());
	}
}
