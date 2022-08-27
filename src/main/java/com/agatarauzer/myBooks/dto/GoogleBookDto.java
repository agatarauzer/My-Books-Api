package com.agatarauzer.myBooks.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBookDto {
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("authors")
	private List<String> author;
	
	@JsonProperty("publishedDate")
	private String publishingYear;
	
	@JsonProperty("ISBN_13")
	private String ISBN;
	
	@JsonProperty("pageCount")
	private int pages;
	
	@JsonProperty("language")
	private String language;
	
	@JsonProperty("publisher")
	private String publisher;
	
}
