package com.agatarauzer.myBooks.reading;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;


@Builder
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
