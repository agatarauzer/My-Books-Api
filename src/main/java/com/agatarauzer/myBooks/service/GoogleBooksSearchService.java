package com.agatarauzer.myBooks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.client.GoogleBooksClient;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookForUserDto;

@Service
public class GoogleBooksSearchService {
	
	@Autowired
	private GoogleBooksClient googleBooksClient;
	
	public List<GoogleBookForUserDto> searchBookByPhrase(String phrase) {
		return googleBooksClient.getBooksFromSearch(phrase);	
	}
}
