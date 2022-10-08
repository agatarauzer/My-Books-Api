package com.agatarauzer.myBooks.dto.GoogleBooks;

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
	
	@JsonProperty("subtitle")
	private String subtitle;
	
	@JsonProperty("authors")
	private List<String> authors;
	
	@JsonProperty("industryIdentifiers")
	private List<IsbnDto> isbn;
	
	@JsonProperty("publisher")
	private String publisher;
	
	@JsonProperty("publishedDate")
	private String publishingDate;
	
	@JsonProperty("language")
	private String language;
	
	@JsonProperty("pageCount")
	private int pages;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("imageLinks")
	private ImageLinkDto imageLink;
}
