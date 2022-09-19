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
	
	@JsonProperty("publisher")
	private String publisher;
	
	@JsonProperty("publishedDate")
	private String publishingDate;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("industryIdentifiers")
	private List<IsbnDto> ISBNs;
	
	@JsonProperty("pageCount")
	private int pages;
	
	@JsonProperty("language")
	private String language;
	
	@JsonProperty("imageLinks")
	private ImageLinkDto imageLink;
}
