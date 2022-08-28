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
						bookDto.getAuthor(),
						bookDto.getISBN(),
						bookDto.getPublisher(),
						bookDto.getPublishingYear(),
						bookDto.getDescription(),
						bookDto.getPages(),
						bookDto.getLanguage(),
						bookDto.getPrice(),
						bookDto.getPurchaseDate(),
						bookDto.getVersion(),
						bookDto.getReadingDetails());
	}
	
	public BookDto mapToBookDto(Book book) {
		
		return new BookDto(book.getId(),
				book.getTitle(),
				book.getAuthor(),
				book.getISBN(),
				book.getPublisher(),
				book.getPublishingYear(),
				book.getDescription(),
				book.getPages(),
				book.getLanguage(),
				book.getPrice(),
				book.getPurchaseDate(),
				book.getVersion(),
				book.getReadingDetails());
	}
	
	
	public List<BookDto> mapToListBookDto(List<Book> books) {
		
		return books.stream()
				.map(c -> new BookDto(c.getId(), c.getTitle(),
						c.getAuthor(),
						c.getISBN(),
						c.getPublisher(),
						c.getPublishingYear(),
						c.getDescription(),
						c.getPages(),
						c.getLanguage(),
						c.getPrice(),
						c.getPurchaseDate(),
						c.getVersion(),
						c.getReadingDetails()))
				.collect(Collectors.toList());
		
	}
}
