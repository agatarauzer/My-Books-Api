package com.agatarauzer.myBooks.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="purchases")
public class Purchase {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="purchase_id")
	private Long id;
	
	@Column(name="price")
	private Double price;
	
	@Column(name="purchase_date")
	private LocalDate purchaseDate;
	
	@Column(name="bought_from")
	private String boughtFrom;
	
	@OneToOne(mappedBy="purchase")
	private Book book;

	public Purchase(Long id, Double price, LocalDate purchaseDate, String boughtFrom) {
		this.id = id;
		this.price = price;
		this.purchaseDate = purchaseDate;
		this.boughtFrom = boughtFrom;
	}
}



