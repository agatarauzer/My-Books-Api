package com.agatarauzer.myBooks.book;

import com.agatarauzer.myBooks.book.domain.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
	private Long id;
	private String title;
	private String authors;
	private String isbn;
	private String publisher;
	private String publishingDate;
	private String language;
	private Integer pages;
	private String description;
	private String imageLink;
	private Version version;
}