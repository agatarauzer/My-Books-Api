package com.agatarauzer.myBooks.dto;

import java.time.LocalDate;

import com.agatarauzer.myBooks.domain.enums.RentalStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class RentalDto {
	private Long id;
	private RentalStatus status;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private String notes;
}
