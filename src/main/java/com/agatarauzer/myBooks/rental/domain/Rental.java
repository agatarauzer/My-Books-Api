package com.agatarauzer.myBooks.rental.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.agatarauzer.myBooks.book.domain.Book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="rentals")
public class Rental {
	@Id
	@Column(name="rental_id")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private RentalStatus status;
	
	private String name;
	
	@Column(name="start_date")
	private LocalDate startDate;
	
	@Column(name="end_date")
	private LocalDate endDate;
	
	private String notes;
	
	@OneToOne
	@MapsId
	@JoinColumn(name="book_id")
	private Book book;
}
