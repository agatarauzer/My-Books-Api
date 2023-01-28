package com.agatarauzer.myBooks.rental;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.agatarauzer.myBooks.book.BookService;
import com.agatarauzer.myBooks.rental.domain.Rental;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RentalMapper {
	
	private final BookService bookService;
	
	public Rental mapToRental(RentalDto rentalDto) {
		return Rental.builder()
				.id(rentalDto.getId())
				.status(rentalDto.getStatus())
				.name(rentalDto.getName())
				.startDate(rentalDto.getStartDate())
				.endDate(rentalDto.getEndDate())
				.notes(rentalDto.getNotes())
				.book(bookService.findBookById(rentalDto.getBookId()))
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
				.bookId(rental.getBook().getId())
				.build();
	}
	
	public List<RentalDto> mapToRentalDtoList(List<Rental> rentals) {
		return rentals.stream()
				.map(r -> mapToRentalDto(r))
				.collect(Collectors.toList());
	}
}
