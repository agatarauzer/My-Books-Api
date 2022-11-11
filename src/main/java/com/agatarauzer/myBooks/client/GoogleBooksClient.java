package com.agatarauzer.myBooks.client;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.agatarauzer.myBooks.config.GoogleBooksApiConfig;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookForUserDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBooksSearchResultDto;
import com.agatarauzer.myBooks.mapper.GoogleBookMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class GoogleBooksClient {
	
	private final RestTemplate restTemplate;
	private final GoogleBooksApiConfig googleBooksApiConfig;
	private final GoogleBookMapper googleBookMapper;
	
	public List<GoogleBookForUserDto> getBooksFromSearch(final String phrase) {
		URI uri = UriComponentsBuilder.fromHttpUrl(googleBooksApiConfig.getGoogleBooksApiEndpoint() + "?q=" + phrase)
			.build()
			.encode()
			.toUri();
		 
		try {
			GoogleBooksSearchResultDto searchResult = restTemplate.getForObject(uri, GoogleBooksSearchResultDto.class);
			return Optional.ofNullable(searchResult)
					.map(s -> googleBookMapper.mapToGoogleBookForUserDtoList(s))
					.orElseGet(Collections::emptyList);
		} catch (RestClientException exc) {
			log.error(exc.getMessage(), exc);
			return Collections.emptyList();
		}
	}
}
