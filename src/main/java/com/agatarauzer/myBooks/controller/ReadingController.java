package com.agatarauzer.myBooks.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/users/{userId}/books/{bookId}")
@PreAuthorize("hasRole('USER_PAID') or hasRole('ADMIN')")
@RequiredArgsConstructor
public class ReadingController {
	
	private final ReadingService readingService;
	private final ReadingMapper readingMapper;
	
	@GetMapping("/readings")
	public ResponseEntity<ReadingDto> getReadingForBook(@PathVariable Long bookId) {
		Reading reading = readingService.getReadingForBook(bookId);
		return ResponseEntity.ok(readingMapper.mapToReadingDto(reading));
	}
	
	@PostMapping("/readings")
	public ResponseEntity<ReadingDto> addReading(@PathVariable Long bookId, @RequestBody ReadingDto readingDto) {
		Reading reading = readingMapper.mapToReading(readingDto);
		readingService.saveReading(bookId, reading);
		return new ResponseEntity<ReadingDto>(readingDto, HttpStatus.CREATED);		
	}
	
	@PutMapping("/readings/{readingId}")
	public ResponseEntity<ReadingDto> updateReading(@PathVariable Long readingId, @RequestBody ReadingDto readingDto) {
		Reading reading = readingMapper.mapToReading(readingDto);
		readingService.updateReading(readingId, reading);
		return ResponseEntity.ok(readingDto);
	}
	
	@DeleteMapping("/readings/{readingId}")
	public ResponseEntity<String> deleteReading(@PathVariable Long bookId, @PathVariable Long readingId) {
		readingService.deleteReading(bookId, readingId);
		return ResponseEntity.ok().body("Deleted reading: " + readingId);
	}
}
