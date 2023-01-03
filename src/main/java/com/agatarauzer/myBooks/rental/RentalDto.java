package com.agatarauzer.myBooks.rental;

import java.time.LocalDate;

import com.agatarauzer.myBooks.rental.domain.RentalStatus;

import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class RentalDto {
	private Long id;
	private RentalStatus status;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private String notes;
}
