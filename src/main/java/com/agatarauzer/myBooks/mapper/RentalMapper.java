package com.agatarauzer.myBooks.mapper;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Rental;
import com.agatarauzer.myBooks.dto.RentalDto;

@Service
public class RentalMapper {
	
	public Rental mapToRental(RentalDto rentalDto) {
		return new Rental(rentalDto.getId(),
				rentalDto.getStatus(),
				rentalDto.getName(),
				rentalDto.getStartDate(),
				rentalDto.getEndDate(),
				rentalDto.getNotes());
	}
	
	public RentalDto mapToRentalDto(Rental rental) {
		return new RentalDto(rental.getId(),
				rental.getStatus(),
				rental.getName(),
				rental.getStartDate(),
				rental.getEndDate(),
				rental.getNotes());
	}
}
