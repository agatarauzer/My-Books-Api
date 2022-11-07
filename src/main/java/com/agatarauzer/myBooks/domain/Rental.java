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

import com.agatarauzer.myBooks.domain.enums.RentalStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="rentals")
public class Rental {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="rental_id")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="rental_status")
	private RentalStatus status;
	
	@Column(name="name")
	private String name;
	
	@Column(name="start_date")
	private LocalDate startDate;
	
	@Column(name="end_date")
	private LocalDate endDate;
	
	@Column(name="notes")
	private String notes;
	
	@OneToOne(mappedBy="rental")
	private Book book;

	public Rental(Long id, RentalStatus status, String name, LocalDate startDate, LocalDate endDate, String notes) {
		this.id = id;
		this.status = status;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.notes = notes;
	}
}
