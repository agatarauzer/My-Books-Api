package com.agatarauzer.myBooks.dto;

import java.time.LocalDate;

import com.agatarauzer.myBooks.domain.ReadingStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReadingDto {
	
	private Long id;
	
	private ReadingStatus status;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private int readedPages;
	
	private int rate;
	
	private String notes;
}
