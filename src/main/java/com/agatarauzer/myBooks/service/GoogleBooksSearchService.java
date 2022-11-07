package com.agatarauzer.myBooks.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.client.GoogleBooksClient;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookForUserDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleBooksSearchService {
	
	private final GoogleBooksClient googleBooksClient;
	
	public List<GoogleBookForUserDto> searchBookByPhrase(String phrase) {
		return googleBooksClient.getBooksFromSearch(phrase);	
	}
}
