package com.agatarauzer.myBooks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.dto.BookDto;
import com.agatarauzer.myBooks.mapper.BookMapper;
import com.agatarauzer.myBooks.service.GoogleBooksSearchService;


@RestController
@RequestMapping("/v1/users/{userId}/books")
public class GoogleBooksController {
	
	@Autowired
	private GoogleBooksSearchService searchService;
	
	@Autowired
	private BookMapper bookMapper;
	
	@GetMapping("/search")
	public List<BookDto> getSearchResultByPhrase(@RequestParam String phrase) {
		List<Book> books = searchService.searchBookByPhrase(phrase);
		return bookMapper.mapToBookDtoList(books);
	}
}
