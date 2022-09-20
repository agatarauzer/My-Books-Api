package com.agatarauzer.myBooks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.dto.BookDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookForUserDto;
import com.agatarauzer.myBooks.exception.BookNotFoundException;
import com.agatarauzer.myBooks.exception.UserNotFoundException;
import com.agatarauzer.myBooks.mapper.BookMapper;
import com.agatarauzer.myBooks.mapper.GoogleBookMapper;
import com.agatarauzer.myBooks.service.BookService;
import com.agatarauzer.myBooks.service.UserService;

@RestController
@RequestMapping("/v1/books")
public class BookController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookMapper bookMapper;
	
	@Autowired
	private GoogleBookMapper googleBookMapper;
	
	
	@GetMapping("/users/{userId}")
	public List<BookDto> getUserBooks(@PathVariable Long userId) {
		List<Book> books = bookService.findBooksByUser(userId);
		return bookMapper.mapToListBookDto(books);
	}
	
	@GetMapping("/{bookId}")
	public BookDto getBookById(@PathVariable Long id) {
		Book book = bookService.findBookById(id);
		if (book == null) {
			throw new BookNotFoundException("Book id not found - " + id);
		}
		return bookMapper.mapToBookDto(book);
	}
	
	@PostMapping("/search/{userId}")
	public BookDto addBookFromSearch(@PathVariable Long userId, @RequestBody GoogleBookForUserDto googleBook) {
		User user = userService.findUserById(userId);
		if (user == null) {
			throw new UserNotFoundException("User id not found: " + userId);
		}
		Book book = googleBookMapper.mapToBook(googleBook);
		book.setId((long)0);
		book.setUser(user);
		bookService.saveBook(book);
		return bookMapper.mapToBookDto(book);
	}
	
	@PostMapping("/{userId}")
	public BookDto addBook (@PathVariable Long userId, @RequestBody BookDto bookDto) {
		User user = userService.findUserById(userId);
		if (user == null) {
			throw new UserNotFoundException("User id not found: " + userId);
		}
		Book book = bookMapper.mapToBook(bookDto);
		book.setId((long)0);
		book.setUser(user);
		bookService.saveBook(book);
		return bookDto;
	}
	
	@PutMapping("/{bookId}")
	public BookDto updateBook(@PathVariable Long bookId, @RequestBody BookDto bookDto) {
		Book book = bookMapper.mapToBook(bookDto);
		if (book == null) {
			throw new BookNotFoundException("Book id not found - " + bookId);
		}
		bookService.updateBook(bookId, book);
		return bookDto;
	}
	
	@DeleteMapping("/{bookId}")
	public String deleteBook(@PathVariable Long bookId) {
		Book book = bookService.findBookById(bookId);
		if (book == null) {
			throw new BookNotFoundException("Book id not found - " + bookId);
		}
		bookService.deleteBookById(bookId);
		return "Deleted book: " + bookId;
	}
}