package com.agatarauzer.myBooks.unit.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.agatarauzer.myBooks.rental.RentalDto;
import com.agatarauzer.myBooks.rental.RentalMapper;
import com.agatarauzer.myBooks.rental.domain.Rental;
import com.agatarauzer.myBooks.rental.domain.RentalStatus;

@SpringBootTest
public class RentalMapperTest {
	
	@Autowired
	RentalMapper rentalMapper;
	
	private Rental rental;
	private RentalDto rentalDto;
	
	@BeforeEach
	public void prepareTestData() {
		rental = Rental.builder()
					.id(1L)
					.status(RentalStatus.BORROWED_FROM)
					.name("from Kate")
					.startDate(LocalDate.of(2022, 6, 21))
					.endDate(LocalDate.of(2023, 1, 5))
					.notes("Kate will need it in January!")
					.build();
		rentalDto = RentalDto.builder()
					.id(1L)
					.status(RentalStatus.BORROWED_FROM)
					.name("from Kate")
					.startDate(LocalDate.of(2022, 6, 21))
					.endDate(LocalDate.of(2023, 1, 5))
					.notes("Kate will need it in January!")
					.build();
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

