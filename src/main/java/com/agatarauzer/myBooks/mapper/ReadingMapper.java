package com.agatarauzer.myBooks.mapper;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Reading;
import com.agatarauzer.myBooks.dto.ReadingDto;

@Service
public class ReadingMapper {
	
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
}
