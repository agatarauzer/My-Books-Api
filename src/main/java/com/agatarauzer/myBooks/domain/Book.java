package com.agatarauzer.myBooks.domain;

import java.time.LocalDate;
import java.util.List;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
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
	
	@Column(name="author")
	private List<String> author;
	
	//ISBN with 13 digits
	@Column(name="ISBN")
	private String ISBN;
	
	@Column(name="publisher")
	private String publisher;
	
	@Column(name="publishing_year")
	private String publishingYear ;
	
	@Column(name="description")
	private String description;
	
	@Column(name="pages")
	private int pages;
	
	@Column(name="language")
	private String language;
	
	@Column(name="price")
	private Double price;
	
	@Column(name="purchase_date")
	private LocalDate purchaseDate;
	
	@Column(name="version")
	private Version version;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="reading_id")
	private Reading readingDetails;
	
	
	public Book(String title, List<String> author, String ISBN, String publisher, 
			String publishingYear, String language, int pages) {
		this.title = title;
		this.author = author;
		this.ISBN = ISBN;
		this.publisher = publisher;
		this.publishingYear = publishingYear;
		this.language = language;
		this.pages = pages;
	}
}


