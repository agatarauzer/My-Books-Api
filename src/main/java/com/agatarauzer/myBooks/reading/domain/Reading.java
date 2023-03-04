package com.agatarauzer.myBooks.reading.domain;

import com.agatarauzer.myBooks.activity.ActivityOnBook;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="readings")
public class Reading extends ActivityOnBook {
	
	@Enumerated(EnumType.STRING)
	private ReadingStatus status;
	private Integer progress;
	private Integer rate;
	private String notes;
}
