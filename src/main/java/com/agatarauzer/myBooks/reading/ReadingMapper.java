package com.agatarauzer.myBooks.reading;

import org.springframework.stereotype.Component;

import com.agatarauzer.myBooks.reading.domain.Reading;

@Component
public class ReadingMapper {
	
	public Reading mapToReading(ReadingDto readingDto) {
		return Reading.builder()
				.id(readingDto.getId())
				.status(readingDto.getStatus())
				.startDate(readingDto.getStartDate())
				.endDate(readingDto.getEndDate())
				.readedPages(readingDto.getReadedPages())
				.rate(readingDto.getRate())
				.notes(readingDto.getNotes())
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
				.build();		
	}
}
