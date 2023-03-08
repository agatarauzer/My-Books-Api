package com.agatarauzer.myBooks.reading;

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
	private ReadingStatus status;
	private Integer progress;
	private Integer rate;
	private String notes;
}
