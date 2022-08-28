package com.agatarauzer.myBooks.mapper;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.dto.GoogleBookDto;


@Service
public class GoogleBookMapper {
	
	public Book mapToBook(GoogleBookDto googleBookDto) {
		return new Book(googleBookDto.getTitle(),
						googleBookDto.getAuthor(),
						googleBookDto.getISBN(),
						googleBookDto.getPublisher(),
						googleBookDto.getPublishingYear(),
						googleBookDto.getLanguage(),
						googleBookDto.getPages());
	}
	
}
