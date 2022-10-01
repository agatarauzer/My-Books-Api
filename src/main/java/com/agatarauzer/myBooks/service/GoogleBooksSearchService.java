package com.agatarauzer.myBooks.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.client.GoogleBooksClient;
import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookForUserDto;
import com.agatarauzer.myBooks.mapper.GoogleBookMapper;

@Service
public class GoogleBooksSearchService {
	
	@Autowired
	private GoogleBooksClient googleBooksClient;
	
	@Autowired
	private GoogleBookMapper googleBookMapper;
	
	public List<Book> searchBookByPhrase(String phrase) {
		List<GoogleBookForUserDto> findedBooks = googleBooksClient.getBooksFromSearch(phrase);
		return findedBooks.stream()
				.map(b -> googleBookMapper.mapToBook(b))
				.collect(Collectors.toList());	
	}
}
