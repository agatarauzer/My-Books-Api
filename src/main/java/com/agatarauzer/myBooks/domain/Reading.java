package com.agatarauzer.myBooks.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="reading_details")
public class Reading {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reading_id")
	private Long id;
	
	@Column(name="status")
	private ReadingStatus status;
	
	@Column(name="start_date")
	private LocalDate startDate;
	
	@Column(name="end_date")
	private LocalDate endDate;
	
	@Column(name="readed_pages")
	private int readedPages;
	
	@Column(name="rate")
	@Min(value=1)
	@Max(value=5)
	private int rate;
	
	@Column(name="notes")
	private String notes;
	
	@OneToOne
	@JoinColumn(name="reading")
	//@JsonIgnoreProperties("reading")
	private Book book;

	public Reading(Long id, ReadingStatus status, LocalDate startDate, LocalDate endDate, int readedPages,
			int rate, String notes) {
		this.id = id;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.readedPages = readedPages;
		this.rate = rate;
		this.notes = notes;
	}
}
