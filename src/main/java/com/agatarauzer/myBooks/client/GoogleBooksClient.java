package com.agatarauzer.myBooks.client;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.agatarauzer.myBooks.config.GoogleBooksApiConfig;
import com.agatarauzer.myBooks.dto.GoogleBookBasicDto;
import com.agatarauzer.myBooks.dto.GoogleBookDto;
import com.agatarauzer.myBooks.dto.GoogleBooksSearchResultDto;

@Component
public class GoogleBooksClient {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired 
	private GoogleBooksApiConfig googleBooksApiConfig;
	
	
	public List<GoogleBookBasicDto> getBooksFromSearch(String phrase) throws RestClientException {
		
		GoogleBooksSearchResultDto searchResult = restTemplate.getForObject(buildUrlToGetSearchResults(phrase), GoogleBooksSearchResultDto.class);

		if (searchResult.equals(null)) {
			throw new RestClientException("Phrase not found.");
		}
		
		return searchResult.getBooks();	
	}
	
	
	private URI buildUrlToGetSearchResults(String phrase) {
		
		String validPhrase = phrase.trim().replaceAll(" ", "+").toString();
		
		return UriComponentsBuilder.fromHttpUrl(googleBooksApiConfig.getGoogleBooksApiEndpoint() +
				"?q=" + validPhrase)
				.queryParam("key", googleBooksApiConfig.getGoogleBooksAppKey())
				.build().encode().toUri();
	}
}
