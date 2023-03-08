package com.agatarauzer.myBooks.reading;

import com.agatarauzer.myBooks.reading.domain.Reading;
import org.springframework.stereotype.Component;

@Component
public class ReadingMapper {
	
	public Reading mapToReading(ReadingDto readingDto) {
		return Reading.builder()
				.status(readingDto.getStatus())
				.progress(readingDto.getProgress())
				.rate(readingDto.getRate())
				.notes(readingDto.getNotes())
				.build();	
	}
	
	public ReadingDto mapToReadingDto(Reading reading) {
		return ReadingDto.builder()
				.status(reading.getStatus())
				.progress(reading.getProgress())
				.rate(reading.getRate())
				.notes(reading.getNotes())
				.build();		
	}
}
