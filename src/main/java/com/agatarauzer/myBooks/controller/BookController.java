package com.agatarauzer.myBooks.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.dto.BookDto;
import com.agatarauzer.myBooks.mapper.BookMapper;
import com.agatarauzer.myBooks.service.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/users/{userId}/books")
@RequiredArgsConstructor
public class BookController {
	
	private final BookService bookService;
	private final BookMapper bookMapper;
	
	@GetMapping
	public ResponseEntity<List<BookDto>> getUserBooks(@PathVariable final Long userId,
				@RequestParam(defaultValue = "0", required = false) int page,
				@RequestParam(defaultValue = "5", required = false) int size,
				@RequestParam(defaultValue = "id", required = false) String sortBy,
				@RequestParam(defaultValue = "desc", required = false) String sortDir) {
		List<Book> books = bookService.findBooksByUser(userId, page, size, sortBy, sortDir);
		return ResponseEntity.ok(bookMapper.mapToBookDtoList(books));
	}
	
	@GetMapping("/{bookId}")
	public ResponseEntity<BookDto> getBookById(@PathVariable final Long userId, @PathVariable final Long bookId) {
		Book book = bookService.findBookById(bookId);
		return ResponseEntity.ok(bookMapper.mapToBookDto(book));
	}
	
	@PostMapping
	public ResponseEntity<BookDto> addBook (@PathVariable final Long userId, @RequestBody final BookDto bookDto) {
		Book book = bookMapper.mapToBook(bookDto);
		bookService.saveBookForUser(userId, book);
		return new ResponseEntity<BookDto>(bookDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{bookId}")
	public ResponseEntity<BookDto> updateBook(@PathVariable final Long userId, @PathVariable final Long bookId, @RequestBody final BookDto bookDto) {
		Book book = bookMapper.mapToBook(bookDto);
		bookService.updateBook(bookId, book);
		return ResponseEntity.ok(bookDto);
	}
	
	@DeleteMapping("/{bookId}")
	public ResponseEntity<String> deleteBook(@PathVariable final Long userId, @PathVariable final Long bookId) {
		bookService.deleteBook(bookId);
		return ResponseEntity.ok().body("Deleted book: " + bookId);
	}
}



