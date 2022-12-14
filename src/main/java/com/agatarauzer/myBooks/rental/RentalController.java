package com.agatarauzer.myBooks.rental;

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

import com.agatarauzer.myBooks.rental.domain.Rental;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/users/{userId}/books/{bookId}")
@PreAuthorize("hasRole('USER_PAID') or hasRole('ADMIN')")
@RequiredArgsConstructor
public class RentalController {
	
	private final RentalService rentalService;
	private final RentalMapper rentalMapper;
	
	@GetMapping("/rentals")
	public ResponseEntity<RentalDto> getRentalForBook(@PathVariable final Long bookId) {
		Rental rental = rentalService.getRentalForBook(bookId);
		return ResponseEntity.ok(rentalMapper.mapToRentalDto(rental));
	}
	
	@PostMapping("/rentals")
	public ResponseEntity<RentalDto> addRental(@PathVariable final Long bookId, @RequestBody final RentalDto rentalDto) {
		Rental rental = rentalMapper.mapToRental(rentalDto);
		rentalService.saveRental(bookId, rental);
		return new ResponseEntity<RentalDto>(rentalDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/rentals/{rentalId}")
	public ResponseEntity<RentalDto> updateRental(@PathVariable final Long rentalId, @RequestBody final RentalDto rentalDto) {
		Rental rental = rentalMapper.mapToRental(rentalDto);
		rentalService.updateRental(rentalId, rental);
		return ResponseEntity.ok(rentalDto);
	}
	
	@DeleteMapping("/rentals/{rentalId}")
	public ResponseEntity<String> deleteRental(@PathVariable final Long bookId, @PathVariable final Long rentalId) {
		rentalService.deleteRental(bookId, rentalId);
		return ResponseEntity.ok().body("Deleted rental: " + rentalId);
	}
}
