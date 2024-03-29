package com.agatarauzer.myBooks.book;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.agatarauzer.myBooks.book.domain.Book;

@Component
public class BookMapper {
	
	public Book mapToBook(BookDto bookDto) {
		return Book.builder()
				.id(bookDto.getId())
				.title(bookDto.getTitle())
				.authors(bookDto.getAuthors())
				.isbn(bookDto.getIsbn())
				.publisher(bookDto.getPublisher())
				.publishingDate(bookDto.getPublishingDate())
				.language(bookDto.getLanguage())
				.pages(bookDto.getPages())
				.description(bookDto.getDescription())
				.imageLink(bookDto.getImageLink())
				.version(bookDto.getVersion())
				.build();			
	}
	
	public BookDto mapToBookDto(Book book) {
		return BookDto.builder()
				.id(book.getId())
				.title(book.getTitle())
				.authors(book.getAuthors())
				.isbn(book.getIsbn())
				.publisher(book.getPublisher())
				.publishingDate(book.getPublishingDate())
				.language(book.getLanguage())
				.pages(book.getPages())
				.description(book.getDescription())
				.imageLink(book.getImageLink())
				.version(book.getVersion())
				.build();
	}
	
	public List<BookDto> mapToBookDtoList(List<Book> books) {
		return books.stream()
				.map(c -> mapToBookDto(c))
				.collect(Collectors.toList());
	}
}
