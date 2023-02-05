package com.agatarauzer.myBooks.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.agatarauzer.myBooks.authentication.role.ERole;
import com.agatarauzer.myBooks.authentication.role.Role;
import com.agatarauzer.myBooks.book.BookRepository;
import com.agatarauzer.myBooks.book.BookService;
import com.agatarauzer.myBooks.book.domain.Book;
import com.agatarauzer.myBooks.book.domain.Version;
import com.agatarauzer.myBooks.exception.alreadyExists.BookAlreadyExistsException;
import com.agatarauzer.myBooks.exception.notFound.BookNotFoundException;
import com.agatarauzer.myBooks.user.User;
import com.agatarauzer.myBooks.user.UserService;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
	
	@InjectMocks
	private BookService bookService;
	
	@Mock
	private BookRepository bookRepository;
	
	@Mock
	private UserService userService;
	
	private Long bookId;
	private Long userId;
	private Book book;
	private Pageable pageable;
	
	@BeforeEach
	public void prepareTestData() {
		bookId = 1L;
		userId = 1L;
		book = Book.builder()
				.id(bookId)
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
		
		String sortDir = "asc";
		String sortBy = "id";
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();
		pageable = PageRequest.of(0, 5, sort);
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
		when(bookRepository.findByUserId(userId, pageable)).thenReturn(bookList);
		
		List<Book> books = bookService.findBooksByUser(userId, 0, 5, "id", "asc");
		
		assertEquals(1, books.size());
		assertEquals(bookList, books);
	}
	
	@Test
	public void shouldSaveBookForUser() {
		User user = User.builder()
				.id(1L)
				.firstName("Tomasz")
				.lastName("Malinowski")
				.email("tomasz.malinowski@gmail.com")
				.username("tommal")
				.password("tom_mal_password")
				.roles(Set.of(new Role(ERole.ROLE_USER_PAID)))
				.build();
		when(bookRepository.findByTitleAndUserId(book.getTitle(), userId)).thenReturn(Optional.ofNullable(null));
		when(userService.findUserById(userId)).thenReturn(user);
		when(bookRepository.save(book)).thenReturn(book);
		
		Book savedBook = bookService.saveBookForUser(userId, book);
		
		assertEquals(userId, savedBook.getUser().getId());
		assertEquals(book, savedBook);
	}

	@Test
	public void shouldUpdateBook() {
		Book bookUpdated = Book.builder()
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
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
		when(bookRepository.save(any(Book.class))).thenReturn(bookUpdated);
		
		Book bookAfterUpdate = bookService.updateBook(bookUpdated);
		
		assertEquals(bookUpdated, bookAfterUpdate);
	}
	
	@Test
	public void shouldDeleteBook() {
		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
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
	public void shouldThrowException_saveBookForUser() {
		doThrow(new BookAlreadyExistsException()).when(bookRepository).findByTitleAndUserId(book.getTitle(), userId);
		
		assertThrows(BookAlreadyExistsException.class, ()-> bookService.saveBookForUser(userId, book));
		verify(bookRepository, times(1)).findByTitleAndUserId(book.getTitle(), userId);
	}
	
	@Test
	public void shouldThrowException_updateBook() {
		doThrow(new BookNotFoundException()).when(bookRepository).findById(bookId);
		
		assertThrows(BookNotFoundException.class, ()-> bookService.updateBook(new Book()));
		verify(bookRepository, times(1)).findById(bookId);
	}
	
	@Test
	public void shouldThrowException_deleteBook() {
		doThrow(new BookNotFoundException()).when(bookRepository).findById(bookId);
		
		assertThrows(BookNotFoundException.class, ()-> bookService.deleteBook(bookId));
		verify(bookRepository, times(0)).deleteById(bookId);
	}
}
	
