package com.agatarauzer.myBooks.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name="book")
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="book_id")
	private Long id;
	
	@NotNull
	@Column(name="title")
	private String title;
	
	@Column(name="ISBN")
	private int ISBN;
	
	@Column(name="author")
	private String author;
	
	@Column(name="language")
	private String language;
	
	@Column(name="publisher")
	private String publisher;
	
	@Column(name="description")
	private String description;
	
	@Column(name="publishing_date")
	private LocalDate publishingDate;
	
	@Column(name="pages")
	private int pages;
	
	@Column(name="price")
	private Double price;
	
	@Column(name="version")
	private Version version;
	
	@Column(name="purchase_date")
	private LocalDate purchaseDate;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="reading_id")
	private Reading reading;
	
}
