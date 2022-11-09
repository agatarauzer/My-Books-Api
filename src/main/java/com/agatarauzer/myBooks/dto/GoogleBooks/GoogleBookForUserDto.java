package com.agatarauzer.myBooks.dto.GoogleBooks;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GoogleBookForUserDto {
	private String title;
	private String authors;
	private String isbn;
	private String publisher;
	private String publishingDate;
	private String language;
	private int pages;
	private String description;
	private String imageLink;
}
