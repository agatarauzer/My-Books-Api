package com.agatarauzer.myBooks.rental;

import com.agatarauzer.myBooks.rental.domain.Rental;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalMapper {
	
	public Rental mapToRental(RentalDto rentalDto) {
		return Rental.builder()
				.status(rentalDto.getStatus())
				.name(rentalDto.getName())
				.predictedReturnDate(rentalDto.getPredictedReturnDate())
				.notes(rentalDto.getNotes())
				.build();
	}
	
	public RentalDto mapToRentalDto(Rental rental) {
		return RentalDto.builder()
				.status(rental.getStatus())
				.name(rental.getName())
				.predictedReturnDate(rental.getPredictedReturnDate())
				.notes(rental.getNotes())
				.build();
	}
	
	public List<RentalDto> mapToRentalDtoList(List<Rental> rentals) {
		return rentals.stream()
				.map(r -> mapToRentalDto(r))
				.collect(Collectors.toList());
	}
}
