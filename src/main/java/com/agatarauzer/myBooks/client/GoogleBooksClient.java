package com.agatarauzer.myBooks.client;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.agatarauzer.myBooks.config.GoogleBooksApiConfig;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookForUserDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBooksSearchResultDto;
import com.agatarauzer.myBooks.mapper.GoogleBookMapper;

@Component
public class GoogleBooksClient {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired 
	private GoogleBooksApiConfig googleBooksApiConfig;
	
	@Autowired
	private GoogleBookMapper googleBookMapper;
	
	public List<GoogleBookForUserDto> getBooksFromSearch(String phrase) {
		try {
			GoogleBooksSearchResultDto searchResult = restTemplate.getForObject(buildUrlToGetSearchResults(phrase), GoogleBooksSearchResultDto.class);
			return Optional.ofNullable(searchResult)
					.map(s -> googleBookMapper.mapToGoogleBookForUserDtoList(s))
					.orElseGet(Collections::emptyList);
		}
		catch (RestClientException exc) {
			LOGGER.error(exc.getMessage(), exc);
			return Collections.emptyList();
		}
	}
	
	private URI buildUrlToGetSearchResults(String phrase) { 
		return UriComponentsBuilder.fromHttpUrl(googleBooksApiConfig.getGoogleBooksApiEndpoint() + "?q=" + phrase)
				.queryParam("key", googleBooksApiConfig.getGoogleBooksAppKey())
				.build()
				.encode()
				.toUri();
	}
}
