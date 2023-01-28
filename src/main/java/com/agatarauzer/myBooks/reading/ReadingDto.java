package com.agatarauzer.myBooks.reading;

import java.time.LocalDate;

import com.agatarauzer.myBooks.reading.domain.ReadingStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReadingDto {
	private Long id;
	private ReadingStatus status;
	private LocalDate startDate;
	private LocalDate endDate;
	private Integer readedPages;
	private Integer rate;
	private String notes;
	private Long bookId;
}
