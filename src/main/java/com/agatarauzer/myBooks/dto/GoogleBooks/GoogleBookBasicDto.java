package com.agatarauzer.myBooks.dto.GoogleBooks;

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
	
	//@JsonProperty("id")
	//private String googleBookId;
	
	//@JsonProperty("selfLink")
	//private String link;
	
	@JsonProperty("volumeInfo")
	private GoogleBookDto googleBookDetails;
	
}
