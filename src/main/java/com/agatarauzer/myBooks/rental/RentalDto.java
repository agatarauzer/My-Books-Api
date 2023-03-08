package com.agatarauzer.myBooks.rental;

import com.agatarauzer.myBooks.rental.domain.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RentalDto {
	private RentalStatus status;
	private String name;
	private LocalDate predictedReturnDate;
	private String notes;
}
