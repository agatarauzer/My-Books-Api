package com.agatarauzer.myBooks.rental;

import java.util.List;

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
	public ResponseEntity<List<RentalDto>> getRentalsForBook(@PathVariable final Long bookId) {
		List<Rental> rentals = rentalService.getRentalsForBook(bookId);
		return ResponseEntity.ok(rentalMapper.mapToRentalDtoList(rentals));
	}
	
	@PostMapping("/rentals")
	public ResponseEntity<RentalDto> addRental(@PathVariable final Long bookId, @RequestBody final RentalDto rentalDto) {
		Rental rental = rentalMapper.mapToRental(rentalDto);
		rentalService.saveRentalForBook(bookId, rental);
		return new ResponseEntity<RentalDto>(rentalDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/rentals/{rentalId}")
	public ResponseEntity<RentalDto> updateRental(@RequestBody final RentalDto rentalDto) {
		Rental rental = rentalMapper.mapToRental(rentalDto);
		rentalService.updateRental(rental);
		return ResponseEntity.ok(rentalDto);
	}
	
	@DeleteMapping("/rentals/{rentalId}")
	public ResponseEntity<String> deleteRental(@PathVariable final Long rentalId) {
		rentalService.deleteRental(rentalId);
		return ResponseEntity.ok().body("Deleted rental: " + rentalId);
	}
}
