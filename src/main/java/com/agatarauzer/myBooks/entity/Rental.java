package com.agatarauzer.myBooks.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="rental")
public class Rental {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="rental_status")
	private RentalStatus status;
	
	@Column(name="name")
	private String name;
	
	@Column(name="start_date")
	private LocalDate start;
	
	@Column(name="end_date")
	private LocalDate end;
}
