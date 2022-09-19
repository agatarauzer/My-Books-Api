package com.agatarauzer.myBooks.domain;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="book")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="book_id")
	private Long id;
	
	@NotNull
	@Column(name="title")
	private String title;
	
	@Column(name="authors")
	private String authors;
	
	@Column(name="ISBNs_numbers")
	private String ISBN;
	
	@Column(name="publisher")
	private String publisher;
	
	@Column(name="publishing_date")
	private String publishingDate;
	
	@Column(name="language")
	private String language;
	
	@Column(name="pages")
	private int pages;
	
	@Column(name="description")
	private String description;
	
	@Column(name="image_link")
	private String imageLink;
	
	@Column(name="price")
	private Double price;
	
	@Column(name="purchase_date")
	private LocalDate purchaseDate;
	
	@Column(name="version")
	private Version version;
	
	@OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
	private Reading reading;
	
	@OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
	private Rental rental;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonIgnoreProperties("books")
	private User user;
	
	
	public Book(String title, String authors, String ISBN, String publisher, 
			String publishingDate, String language, int pages, String description, String imageLink) {
		this.title = title;
		this.authors = authors;
		this.ISBN = ISBN;
		this.publisher = publisher;
		this.publishingDate = publishingDate;
		this.language = language;
		this.pages = pages;
		this.imageLink = imageLink;
	}

	public Book(Long id, String title, String authors, String ISBN, String publisher, String publishingDate,
			String language, int pages, String description, String imageLink, Double price, LocalDate purchaseDate,
			Version version) {
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.ISBN = ISBN;
		this.publisher = publisher;
		this.publishingDate = publishingDate;
		this.language = language;
		this.pages = pages;
		this.description = description;
		this.imageLink = imageLink;
		this.price = price;
		this.purchaseDate = purchaseDate;
		this.version = version;
	}
}


