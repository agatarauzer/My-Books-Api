package com.agatarauzer.myBooks.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.agatarauzer.myBooks.domain.Reading;
import com.agatarauzer.myBooks.domain.ReadingStatus;
import com.agatarauzer.myBooks.dto.ReadingDto;

@SpringBootTest
public class ReadingMapperTest {
	
	@Autowired
	ReadingMapper readingMapper;
	
	private Reading reading;
	private ReadingDto readingDto;
	
	@BeforeEach
	public void prepareTestData() {
		reading = new Reading(1L, ReadingStatus.READED, LocalDate.of(2020, 5, 9), LocalDate.of(2022, 1, 24), 820, 4, "Java basics");
		readingDto = new ReadingDto(1L, ReadingStatus.READED, LocalDate.of(2020, 5, 9), LocalDate.of(2022, 1, 24), 820, 4, "Java basics");
	}
	
	@Test
	public void shouldMapToReadingDto() {
		ReadingDto readingDtoMapped = readingMapper.mapToReadingDto(reading);
		
		assertEquals(reading.getId(), readingDtoMapped.getId());
		assertEquals(reading.getStartDate(), readingDtoMapped.getStartDate());
		assertEquals(reading.getEndDate(), readingDtoMapped.getEndDate());
		assertEquals(reading.getReadedPages(), readingDtoMapped.getReadedPages());
		assertEquals(reading.getRate(), readingDtoMapped.getRate());
		assertEquals(reading.getNotes(), readingDtoMapped.getNotes());
	}
	
	@Test
	public void shouldMapToReading() {
		Reading readingMapped = readingMapper.mapToReading(readingDto);
		
		assertEquals(readingDto.getId(), readingMapped.getId());
		assertEquals(readingDto.getStartDate(), readingMapped.getStartDate());
		assertEquals(readingDto.getEndDate(), readingMapped.getEndDate());
		assertEquals(readingDto.getReadedPages(), readingMapped.getReadedPages());
		assertEquals(readingDto.getRate(), readingMapped.getRate());
		assertEquals(readingDto.getNotes(), readingMapped.getNotes());
	}
}
