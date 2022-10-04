package com.agatarauzer.myBooks.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.domain.Version;
import com.agatarauzer.myBooks.exception.BookNotFoundException;
import com.agatarauzer.myBooks.exception.UserNotFoundException;
import com.agatarauzer.myBooks.repository.BookRepository;
import com.agatarauzer.myBooks.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
	
	@InjectMocks
	private BookService bookService;
	
	@Mock
	private BookRepository bookRepository;
	
	@Mock
	private UserRepository userRepository;
	
	private Long bookId;
	private Long userId;
	private Book book;
	
	@BeforeEach
	public void prepareTestData() {
		bookId = 1L;
		userId = 1L;
		book = new Book(1L, "Java. Podstawy. Wydanie IX", "Cay S. Horstmann,Gary Cornell", "9788324677610, 8324677615", "Helion", "2013-12-09", "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api", Version.PAPER, 1);
	}
	
	@Test
	public void shouldFindAllBooks() {
		List<Book> bookList = List.of(book);
		when(bookRepository.findAll()).thenReturn(bookList);
		
		List<Book> books = bookService.findAll();
		
		assertEquals(1, books.size());
		assertEquals(book, books.get(0));
	}
	
	@Test
	public void shouldFindBookById() {
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		
		Book foundedBook = bookService.findBookById(bookId);

		assertEquals(book, foundedBook);
	}
	
	@Test
	public void shouldFindBooksByUser() {
		List<Book> bookList = List.of(book);
		when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
		when(bookRepository.findByUserId(userId)).thenReturn(bookList);
		
		List<Book> books = bookService.findBooksByUser(userId);
		
		assertEquals(1, books.size());
		assertEquals(bookList, books);
	}
	
	@Test
	public void shouldSaveBookForUser() {
		User user = new User(userId, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password");
		when(userRepository.findById(userId)).thenReturn(Optional.of(user));
		when(bookRepository.save(book)).thenReturn(book);
		
		Book savedBook = bookService.saveBookForUser(userId, book);
		
		assertEquals(userId, savedBook.getUser().getId());
		assertEquals(book, savedBook);
	}
	
	@Test
	public void sholudSaveBook() {
		when(bookRepository.save(book)).thenReturn(book);
		
		Book savedBook = bookService.saveBook(book);
		
		assertEquals(book, savedBook);
	}
	
	@Test
	public void shouldUpdateBook() {
		Book bookUpdated = new Book(bookId, "Java. Podstawy. Wydanie IX_changed", "Cay S. Horstmann,Gary Cornell_changed", "9788324677789, 8324677455", "Helion_changed", "2022-12-22", "pl_changed", 900, "Kolejne wydanie_changed", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api_changed", Version.E_BOOK, 2);
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		when(bookRepository.save(any(Book.class))).thenReturn(bookUpdated);
		
		Book bookAfterUpdate = bookService.updateBook(bookId, bookUpdated);
		
		assertEquals(bookUpdated, bookAfterUpdate);
	}
	
	@Test
	public void shouldDeleteBook() {
		doNothing().when(bookRepository).deleteById(bookId);
		
		bookService.deleteBook(bookId);
		
		verify(bookRepository, times(1)).deleteById(bookId);
	}
	
	@Test
	public void shouldThrowException_findBookById() {
		doThrow(new BookNotFoundException()).when(bookRepository).findById(bookId);
		
		assertThrows(BookNotFoundException.class, ()-> bookService.findBookById(bookId));
		verify(bookRepository, times(1)).findById(bookId);
	}
	
	@Test
	public void shouldThrowException_findBooksByUser() {
		doThrow(new UserNotFoundException()).when(userRepository).findById(userId);
		
		assertThrows(UserNotFoundException.class, ()-> bookService.findBooksByUser(userId));
		verify(userRepository, times(1)).findById(userId);
	}
	
	@Test
	public void shouldThrowException_saveBookForUser() {
		doThrow(new UserNotFoundException()).when(userRepository).findById(userId);
		
		assertThrows(UserNotFoundException.class, ()-> bookService.saveBookForUser(userId, new Book()));
		verify(userRepository, times(1)).findById(userId);
	}
	
	@Test
	public void shouldThrowException_updateBook() {
		doThrow(new BookNotFoundException()).when(bookRepository).findById(bookId);
		
		assertThrows(BookNotFoundException.class, ()-> bookService.updateBook(bookId, new Book()));
		verify(bookRepository, times(1)).findById(bookId);
	}
	
	@Test
	public void shouldThrowException_deleteBook() {
		doThrow(new BookNotFoundException()).when(bookRepository).deleteById(bookId);
		
		assertThrows(BookNotFoundException.class, ()-> bookService.deleteBook(bookId));
		verify(bookRepository, times(1)).deleteById(bookId);
	}
}
	
