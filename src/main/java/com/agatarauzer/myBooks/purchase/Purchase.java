package com.agatarauzer.myBooks.purchase;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="purchases")
public class Purchase {
	@Id
	@Column(name="purchase_id")
	private Long id;
	
	private Double price;
	
	@Column(name="purchase_date")
	private LocalDate purchaseDate;
	
	@Column(name="bought_from")
	private String boughtFrom;
	
	@OneToOne
	@MapsId
	@JoinColumn(name="book_id")
	private Book book;
}

