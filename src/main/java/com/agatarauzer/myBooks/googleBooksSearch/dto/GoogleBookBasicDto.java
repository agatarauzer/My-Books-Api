package com.agatarauzer.myBooks.googleBooksSearch.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter 
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBookBasicDto {
	
	@JsonProperty("volumeInfo")
	private GoogleBookDto googleBookDto;
	
	@JsonProperty("searchInfo")
	private SearchInfo searchInfo;
}
