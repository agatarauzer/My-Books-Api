package com.agatarauzer.myBooks.dto;

import com.agatarauzer.myBooks.domain.enums.Version;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BookDto {
	private Long id;
	private String title;
	private String authors;
	private String isbn;
	private String publisher;
	private String publishingDate;
	private String language;
	private int pages;
	private String description;
	private String imageLink;
	private Version version;
	private Integer copies;
}