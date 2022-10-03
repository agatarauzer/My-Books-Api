package com.agatarauzer.myBooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agatarauzer.myBooks.domain.Rental;
import com.agatarauzer.myBooks.dto.RentalDto;
import com.agatarauzer.myBooks.mapper.RentalMapper;
import com.agatarauzer.myBooks.service.RentalService;

@RestController
@RequestMapping("/v1/users/{userId}/books/{bookId}")
public class RentalController {
	
	@Autowired
	private RentalService rentalService;
	
	@Autowired
	private RentalMapper rentalMapper;
	
	
	@GetMapping("/rentals")
	public RentalDto getRentalForBook(@PathVariable Long bookId) {
		Rental rental = rentalService.getRentalForBook(bookId);
		return rentalMapper.mapToRentalDto(rental);
	}
	
	@PostMapping("/rentals")
	public RentalDto addRental(@PathVariable Long bookId, @RequestBody RentalDto rentalDto) {
		Rental rental = rentalMapper.mapToRental(rentalDto);
		rentalService.saveRental(bookId, rental);
		return rentalDto;
	}
	
	@PutMapping("/rentals/{rentalId}")
	public RentalDto updateRental(@PathVariable Long rentalId, @RequestBody RentalDto rentalDto) {
		Rental rental = rentalMapper.mapToRental(rentalDto);
		rentalService.updateRental(rentalId, rental);
		return rentalDto;
	}
	
	@DeleteMapping("/rentals/{rentalId}")
	public String deleteRental(@PathVariable Long rentalId) {
		rentalService.deleteById(rentalId);
		return "Deleted reading: " + rentalId;
	}
}
