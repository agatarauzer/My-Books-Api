package com.agatarauzer.myBooks.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.dto.BookDto;

@Service
public class BookMapper {
	
	public Book mapToBook(BookDto bookDto) {
		return new Book(bookDto.getId(),
						bookDto.getTitle(),
						bookDto.getAuthors(),
						bookDto.getISBN(),
						bookDto.getPublisher(),
						bookDto.getPublishingDate(),
						bookDto.getLanguage(),
						bookDto.getPages(),
						bookDto.getDescription(),
						bookDto.getImageLink(),
						bookDto.getPrice(),
						bookDto.getPurchaseDate(),
						bookDto.getVersion());
	}
	
	public BookDto mapToBookDto(Book book) {
		return new BookDto(book.getId(),
						book.getTitle(),
						book.getAuthors(),
						book.getISBN(),
						book.getPublisher(),
						book.getPublishingDate(),
						book.getLanguage(),
						book.getPages(),
						book.getDescription(),
						book.getImageLink(),
						book.getPrice(),
						book.getPurchaseDate(),
						book.getVersion());
	}
	
	
	public List<BookDto> mapToBookDtoList(List<Book> books) {
		return books.stream()
				.map(c -> mapToBookDto(c))
				.collect(Collectors.toList());
	}
}
