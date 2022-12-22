package com.agatarauzer.myBooks.googleBooksSearch;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.googleBooksSearch.dto.GoogleBookForUserDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleBooksSearchService {
	
	private final GoogleBooksClient googleBooksClient;
	
	public List<GoogleBookForUserDto> searchBookByPhrase(String phrase) {
		return googleBooksClient.getBooksFromSearch(phrase);	
	}
}
