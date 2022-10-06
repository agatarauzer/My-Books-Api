package com.agatarauzer.myBooks.dto;

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
	private Version version;
	private Integer copies;
}