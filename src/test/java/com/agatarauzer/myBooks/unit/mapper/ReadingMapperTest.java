package com.agatarauzer.myBooks.unit.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.agatarauzer.myBooks.domain.Reading;
import com.agatarauzer.myBooks.domain.enums.ReadingStatus;
import com.agatarauzer.myBooks.dto.ReadingDto;
import com.agatarauzer.myBooks.mapper.ReadingMapper;

@SpringBootTest
public class ReadingMapperTest {
	
	@Autowired
	ReadingMapper readingMapper;
	
	private Reading reading;
	private ReadingDto readingDto;
	
	@BeforeEach
	public void prepareTestData() {
		reading = Reading.builder()
				.id(1L)
				.status(ReadingStatus.READED)
				.startDate(LocalDate.of(2020, 5, 9))
				.endDate(LocalDate.of(2022, 1, 24))
				.readedPages(820)
				.rate(4)
				.notes("Java basics...")
				.build();	
		readingDto = ReadingDto.builder()
				.id(1L)
				.status(ReadingStatus.READED)
				.startDate(LocalDate.of(2020, 5, 9))
				.endDate(LocalDate.of(2022, 1, 24))
				.readedPages(820)
				.rate(4)
				.notes("Java basics...")
				.build();
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
