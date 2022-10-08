package com.agatarauzer.myBooks.dto.GoogleBooks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
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
