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
import com.agatarauzer.myBooks.dto.BookDto;
import com.agatarauzer.myBooks.mapper.BookMapper;
import com.agatarauzer.myBooks.service.BookService;

@RestController
@RequestMapping("/v1/users/{userId}")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private BookMapper bookMapper;
	
	@GetMapping("/books")
	public List<BookDto> getUserBooks(@PathVariable Long userId) {
		List<Book> books = bookService.findBooksByUser(userId);
		return bookMapper.mapToBookDtoList(books);
	}
	
	@GetMapping("/books/{bookId}")
	public BookDto getBookById(@PathVariable Long userId, @PathVariable Long bookId) {
		Book book = bookService.findBookById(bookId);
		return bookMapper.mapToBookDto(book);
	}
	
	@PostMapping("/books")
	public BookDto addBook (@PathVariable Long userId, @RequestBody BookDto bookDto) {
		Book book = bookMapper.mapToBook(bookDto);
		bookService.saveBookForUser(userId, book);
		return bookDto;
	}
	
	@PutMapping("/books/{bookId}")
	public BookDto updateBook(@PathVariable Long userId, @PathVariable Long bookId, @RequestBody BookDto bookDto) {
		Book book = bookMapper.mapToBook(bookDto);
		bookService.updateBook(bookId, book);
		return bookDto;
	}
	
	@DeleteMapping("/books/{bookId}")
	public String deleteBook(@PathVariable Long userId, @PathVariable Long bookId) {
		bookService.deleteBook(bookId);
		return "Deleted book: " + bookId;
	}
}