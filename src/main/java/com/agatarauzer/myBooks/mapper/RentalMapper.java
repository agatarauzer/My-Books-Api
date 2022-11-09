package com.agatarauzer.myBooks.mapper;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Rental;
import com.agatarauzer.myBooks.dto.RentalDto;

@Service
public class RentalMapper {
	
	public Rental mapToRental(RentalDto rentalDto) {
		return Rental.builder()
				.id(rentalDto.getId())
				.status(rentalDto.getStatus())
				.name(rentalDto.getName())
				.startDate(rentalDto.getStartDate())
				.endDate(rentalDto.getEndDate())
				.notes(rentalDto.getNotes())
				.build();
	}
	
	public RentalDto mapToRentalDto(Rental rental) {
		return RentalDto.builder()
				.id(rental.getId())
				.status(rental.getStatus())
				.name(rental.getName())
				.startDate(rental.getStartDate())
				.endDate(rental.getEndDate())
				.notes(rental.getNotes())
				.build();
	}
}
