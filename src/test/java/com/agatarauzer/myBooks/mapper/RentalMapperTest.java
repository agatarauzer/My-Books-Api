package com.agatarauzer.myBooks.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.agatarauzer.myBooks.domain.Rental;
import com.agatarauzer.myBooks.domain.RentalStatus;
import com.agatarauzer.myBooks.dto.RentalDto;

@SpringBootTest
public class RentalMapperTest {
	
	@Autowired
	RentalMapper rentalMapper;
	
	private Rental rental;
	private RentalDto rentalDto;
	
	@BeforeEach
	public void prepareTestData() {
		rental = new Rental(1L, RentalStatus.BORROWED, "from Kate", LocalDate.of(2022, 6, 21), LocalDate.of(2023, 1, 5), "Kate will need it in January!");
		rentalDto = new RentalDto(1L, RentalStatus.BORROWED, "from Kate", LocalDate.of(2022, 6, 21), LocalDate.of(2023, 1, 5), "Kate will need it in January!");
	}
	
	@Test
	public void shouldMapToRentalDto() {
		RentalDto rentalDtoMapped = rentalMapper.mapToRentalDto(rental);
		
		assertEquals(rental.getId(), rentalDtoMapped.getId());
		assertEquals(rental.getStatus(), rentalDtoMapped.getStatus());
		assertEquals(rental.getName(), rentalDtoMapped.getName());
		assertEquals(rental.getStartDate(), rentalDtoMapped.getStartDate());
		assertEquals(rental.getEndDate(), rentalDtoMapped.getEndDate());
		assertEquals(rental.getNotes(), rentalDtoMapped.getNotes());
	}
	
	@Test
	public void shouldMapToRental() {
		Rental rentalMapped = rentalMapper.mapToRental(rentalDto);
		
		assertEquals(rentalDto.getId(), rentalMapped.getId());
		assertEquals(rentalDto.getStatus(), rentalMapped.getStatus());
		assertEquals(rentalDto.getName(), rentalMapped.getName());
		assertEquals(rentalDto.getStartDate(), rentalMapped.getStartDate());
		assertEquals(rentalDto.getEndDate(), rentalMapped.getEndDate());
		assertEquals(rentalDto.getNotes(), rentalMapped.getNotes());
	}
}

