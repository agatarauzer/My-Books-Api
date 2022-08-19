package com.agatarauzer.myBooks.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="reading")
public class Reading {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reading_id")
	private Long id;
	
	@Column(name="status")
	private ReadingStatus status;
	
	@Column(name="start_date")
	private LocalDate startReading;
	
	@Column(name="end_date")
	private LocalDate endReading;
	
	@Column(name="readed_pages")
	private int readedPages;
	
	@Column(name="rate")
	private Rate rate;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="id")
	private Rental rental;
	
	@Column(name="notes")
	private String notes;
	
}
