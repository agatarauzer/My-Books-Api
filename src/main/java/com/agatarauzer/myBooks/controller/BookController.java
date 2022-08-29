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
import com.agatarauzer.myBooks.dto.GoogleBookDto;
import com.agatarauzer.myBooks.exception.BookNotFoundException;
import com.agatarauzer.myBooks.mapper.BookMapper;
import com.agatarauzer.myBooks.mapper.GoogleBookMapper;
import com.agatarauzer.myBooks.service.BookService;

@RestController
@RequestMapping("/myBooks")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private GoogleBookMapper googleBookMapper;
	
	@Autowired
	private BookMapper bookMapper;
	
	
	@GetMapping("/books")
	public List<BookDto>GetBooks() {
		
		List<Book> books = bookService.findAll();
		return bookMapper.mapToListBookDto(books);
	}
	
	@GetMapping("/books/{bookId}")
	public BookDto getBook(@PathVariable Long id) {
		
		Book book = bookService.findBookById(id);
		
		if (book == null) {
			throw new BookNotFoundException("Book id not found - " + id);
		}
		
		return bookMapper.mapToBookDto(book);
	}
	
	
	@PostMapping("/gbooks/")
	public Book addGoogleBook (@RequestBody GoogleBookDto googleBookDto) {
		
		Book book = googleBookMapper.mapToBook(googleBookDto);
		book.setId((long)0);
		
		bookService.save(book);
		
		return book;
	}
	
	
	@PostMapping("/books")
	public Book addBook (@RequestBody BookDto bookDto) {
		
		Book book = bookMapper.mapToBook(bookDto);
		book.setId((long)0);
		
		bookService.save(book);
		
		return book;
	}
	
	
	@PutMapping("/books/{bookId}")
	public Book updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
		
		
		Book book = bookMapper.mapToBook(bookDto);
		
		if (book == null) {
			throw new BookNotFoundException("Book id not found - " + id);
		}
		
		bookService.save(book);
		
		return book;
	}
	
	@DeleteMapping("/books/{bookId}")
	public String deleteBook(@PathVariable Long id) {
		
		Book book = bookService.findBookById(id);
		
		if (book == null) {
			throw new BookNotFoundException("Book id not found - " + id);
		}
		
		bookService.deleteById(id);
		
		return "Deleted book: " + id;
	}
}
