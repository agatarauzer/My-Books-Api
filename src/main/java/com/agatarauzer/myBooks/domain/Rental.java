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

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="rental_details")
public class Rental {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="rental_id")
	private Long id;
	
	@Column(name="rental_status")
	private RentalStatus status;
	
	@Column(name="name")
	private String name;
	
	@Column(name="start_date")
	private LocalDate start;
	
	@Column(name="end_date")
	private LocalDate end;
	
	@OneToOne
	@JoinColumn(name="rental")
	private Book book;

	public Rental(Long id, RentalStatus status, String name, LocalDate start, LocalDate end) {
		this.id = id;
		this.status = status;
		this.name = name;
		this.start = start;
		this.end = end;
	}
}
