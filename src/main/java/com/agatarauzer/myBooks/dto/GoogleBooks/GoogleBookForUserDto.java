package com.agatarauzer.myBooks.dto.GoogleBooks;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GoogleBookForUserDto {
	private String title;
	private String authors;
	private String publisher;
	private String publishingDate;
	private String description;
	private String ISBNs;
	private int pages;
	private String language;
	private String imageLink;
}