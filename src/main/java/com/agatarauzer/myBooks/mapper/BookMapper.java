package com.agatarauzer.myBooks.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.dto.BookDto;

@Service
public class BookMapper {

	@Autowired
	private ReadingMapper readingMapper;
	
	@Autowired
	private RentalMapper rentalMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	
	
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
						//readingMapper.mapToReading(bookDto.getReading()),
						//rentalMapper.mapToRental(bookDto.getRental()),
						//userMapper.mapToUser(bookDto.getUser()));			
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
						//readingMapper.mapToReadingDto(book.getReading()),
						//rentalMapper.mapToRentalDto(book.getRental()),
						//userMapper.mapToUserDto(book.getUser()));
	}
	
	
	public List<BookDto> mapToListBookDto(List<Book> books) {
		return books.stream()
				.map(c -> mapToBookDto(c))
				.collect(Collectors.toList());
	}
}
