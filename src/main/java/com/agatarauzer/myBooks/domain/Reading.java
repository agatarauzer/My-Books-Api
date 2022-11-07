package com.agatarauzer.myBooks.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.agatarauzer.myBooks.domain.enums.ReadingStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="readings")
public class Reading {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reading_id")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private ReadingStatus status;
	
	@Column(name="start_date")
	private LocalDate startDate;
	
	@Column(name="end_date")
	private LocalDate endDate;
	
	@Column(name="readed_pages")
	private Integer readedPages;
	
	@Column(name="rate")
	@Min(value=1)
	@Max(value=5)
	private Integer rate;
	
	@Column(name="notes")
	private String notes;
	
	@OneToOne(mappedBy="reading")
	private Book book;

	public Reading(Long id, ReadingStatus status, LocalDate startDate, LocalDate endDate, Integer readedPages,
			Integer rate, String notes) {
		this.id = id;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.readedPages = readedPages;
		this.rate = rate;
		this.notes = notes;
	}
}
