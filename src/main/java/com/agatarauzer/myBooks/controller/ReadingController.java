package com.agatarauzer.myBooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agatarauzer.myBooks.domain.Reading;
import com.agatarauzer.myBooks.dto.ReadingDto;
import com.agatarauzer.myBooks.mapper.ReadingMapper;
import com.agatarauzer.myBooks.service.ReadingService;

@RestController
@RequestMapping("/v1/users/{userId}/books/{bookId}")
@PreAuthorize("hasRole('USER_PAID') or hasRole('ADMIN')")
public class ReadingController {
	
	@Autowired
	private ReadingService readingService;
	
	@Autowired
	private ReadingMapper readingMapper;
	
	
	@GetMapping("/readings")
	public ReadingDto getReadingForBook(@PathVariable Long bookId) {
		Reading reading = readingService.getReadingForBook(bookId);
		return readingMapper.mapToReadingDto(reading);
	}
	
	@PostMapping("/readings")
	public ReadingDto addReading(@PathVariable Long bookId, @RequestBody ReadingDto readingDto) {
		Reading reading = readingMapper.mapToReading(readingDto);
		readingService.saveReading(bookId, reading);
		return readingDto;
	}
	
	@PutMapping("/readings/{readingId}")
	public ReadingDto updateReading(@PathVariable Long readingId, @RequestBody ReadingDto readingDto) {
		Reading reading = readingMapper.mapToReading(readingDto);
		readingService.updateReading(readingId, reading);
		return readingDto;
	}
	
	@DeleteMapping("/readings/{readingId}")
	public String deleteReading(@PathVariable Long readingId) {
		readingService.deleteReading(readingId);
		return "Deleted reading: " + readingId;
	}
}
