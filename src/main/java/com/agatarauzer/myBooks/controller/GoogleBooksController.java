package com.agatarauzer.myBooks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookForUserDto;
import com.agatarauzer.myBooks.service.GoogleBooksSearchService;


@RestController
@RequestMapping("/v1")
public class GoogleBooksController {
	
	@Autowired
	private GoogleBooksSearchService searchService;
	
	@GetMapping("/search")
	public List<GoogleBookForUserDto> getSearchResultByPhrase(@RequestParam String phrase) {
		
		return searchService.searchBookByPhrase(phrase);
	}
}
