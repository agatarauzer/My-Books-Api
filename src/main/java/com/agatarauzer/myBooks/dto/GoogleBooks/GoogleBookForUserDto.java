package com.agatarauzer.myBooks.dto.GoogleBooks;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GoogleBookForUserDto {
	private String title;
	private String authors;
	private String ISBN;
	private String publisher;
	private String publishingDate;
	private String language;
	private int pages;
	private String description;
	private String imageLink;
}
