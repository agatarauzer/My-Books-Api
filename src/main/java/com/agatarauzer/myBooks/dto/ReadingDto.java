package com.agatarauzer.myBooks.dto;

import java.time.LocalDate;

import com.agatarauzer.myBooks.domain.enums.ReadingStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class ReadingDto {
	private Long id;
	private ReadingStatus status;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer readedPages;
	private Integer rate;
	private String notes;
}
