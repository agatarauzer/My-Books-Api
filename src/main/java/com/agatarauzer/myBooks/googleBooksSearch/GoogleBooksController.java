package com.agatarauzer.myBooks.googleBooksSearch;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agatarauzer.myBooks.googleBooksSearch.dto.GoogleBookForUserDto;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
public class GoogleBooksController {
	
	private final GoogleBooksSearchService searchService;
	
	@GetMapping("/outer-search")
	public ResponseEntity<List<GoogleBookForUserDto>> getSearchResultByPhrase(@RequestParam String phrase) {
		return ResponseEntity.ok(searchService.searchBookByPhrase(phrase));
	}
}
