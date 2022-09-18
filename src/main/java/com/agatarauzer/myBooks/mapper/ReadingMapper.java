package com.agatarauzer.myBooks.mapper;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Reading;
import com.agatarauzer.myBooks.dto.ReadingDto;

@Service
public class ReadingMapper {
	
	public ReadingDto mapToReadingDto(Reading reading) {
		return new ReadingDto(reading.getId(),
				reading.getStatus(),
				reading.getStartDate(),
				reading.getEndDate(),
				reading.getReadedPages(),
				reading.getRate(),
				reading.getNotes());
	}
	
	public Reading mapToReading(ReadingDto readingDto) {
		return new Reading(readingDto.getId(),
				readingDto.getStatus(),
				readingDto.getStartDate(),
				readingDto.getEndDate(),
				readingDto.getReadedPages(),
				readingDto.getRate(),
				readingDto.getNotes());
	}

}
