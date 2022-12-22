package com.agatarauzer.myBooks.googleBooksSearch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class GoogleBooksApiConfig {
	
	@Value("${googleBooks.api.endpoint}")
	private String googleBooksApiEndpoint;
}
