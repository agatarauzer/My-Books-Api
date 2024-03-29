package com.agatarauzer.myBooks.reading;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agatarauzer.myBooks.reading.domain.Reading;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/users/{userId}/books/{bookId}")
@RequiredArgsConstructor
public class ReadingController {
	
	private final ReadingService readingService;
	private final ReadingMapper readingMapper;
	
	@GetMapping("/readings")
	public ResponseEntity<ReadingDto> getReadingForBook(@PathVariable final Long bookId) {
		Reading reading = readingService.getReadingForBook(bookId);
		return ResponseEntity.ok(readingMapper.mapToReadingDto(reading));
	}
	
	@PostMapping("/readings")
	public ResponseEntity<ReadingDto> addReading(@PathVariable final Long bookId, @RequestBody final ReadingDto readingDto) {
		Reading reading = readingMapper.mapToReading(readingDto);
		readingService.saveReadingForBook(bookId, reading);
		return new ResponseEntity<ReadingDto>(readingDto, HttpStatus.CREATED);		
	}
	
	@PutMapping("/readings/{readingId}")
	public ResponseEntity<ReadingDto> updateReading(@RequestBody final ReadingDto readingDto) {
		Reading reading = readingMapper.mapToReading(readingDto);
		readingService.updateReading(reading);
		return ResponseEntity.ok(readingDto);
	}
	
	@DeleteMapping("/readings/{readingId}")
	public ResponseEntity<String> deleteReading(@PathVariable final Long readingId) {
		readingService.deleteReading(readingId);
		return ResponseEntity.ok().body("Deleted reading: " + readingId);
	}
}
