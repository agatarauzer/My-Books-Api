package com.agatarauzer.myBooks.reading;

import org.springframework.stereotype.Component;

import com.agatarauzer.myBooks.book.BookService;
import com.agatarauzer.myBooks.reading.domain.Reading;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReadingMapper {
	
	private final BookService bookService;
	
	public Reading mapToReading(ReadingDto readingDto) {
		return Reading.builder()
				.id(readingDto.getId())
				.status(readingDto.getStatus())
				.startDate(readingDto.getStartDate())
				.endDate(readingDto.getEndDate())
				.readedPages(readingDto.getReadedPages())
				.rate(readingDto.getRate())
				.notes(readingDto.getNotes())
				.book(bookService.findBookById(readingDto.getBookId()))
				.build();	
	}
	
	public ReadingDto mapToReadingDto(Reading reading) {
		return ReadingDto.builder()
				.id(reading.getId())
				.status(reading.getStatus())
				.startDate(reading.getStartDate())
				.endDate(reading.getEndDate())
				.readedPages(reading.getReadedPages())
				.rate(reading.getRate())
				.notes(reading.getNotes())
				.bookId(reading.getBook().getId())
				.build();		
	}
}
