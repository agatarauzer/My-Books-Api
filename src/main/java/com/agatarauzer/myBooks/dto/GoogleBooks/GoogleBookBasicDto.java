package com.agatarauzer.myBooks.dto.GoogleBooks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter 
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBookBasicDto {
	
	@JsonProperty("volumeInfo")
	private GoogleBookDto googleBookDto;
	
	@JsonProperty("searchInfo")
	private SearchInfo searchInfo;
}
