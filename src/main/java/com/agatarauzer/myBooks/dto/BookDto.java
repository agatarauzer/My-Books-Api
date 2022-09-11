package com.agatarauzer.myBooks.dto;

import java.time.LocalDate;
import java.util.List;

import com.agatarauzer.myBooks.domain.Reading;
import com.agatarauzer.myBooks.domain.Version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookDto {
	
	private Long id;
	
	private String title;
	
	private String authors;
	
	private String ISBN;
	
	private String publisher;
	
	private String publishingDate;
	
	private String language;
	
	private int pages;
	
	private String description;
	
	private String imageLink;
	
	private Double price;
	
	private LocalDate purchaseDate;
	
	private Version version;
	
	private Reading readingDetails;
}


