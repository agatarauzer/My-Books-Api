package com.agatarauzer.myBooks.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.agatarauzer.myBooks.domain.enums.Version;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="books")
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
	
	@Column(name="ISBN_numbers")
	private String isbn;
	
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
	
	@Enumerated(EnumType.STRING)
	@Column(name="version")
	private Version version;
	
	@Column(name="copies")
	private Integer copies;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="reading_id")
	private Reading reading;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="rental_id")
	private Rental rental;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="purchase_id")
	private Purchase purchase;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	

	public Book(String title, String authors, String isbn, String publisher, String publishingDate, 
			String language, int pages, String description, String imageLink) {
		this.title = title;
		this.authors = authors;
		this.isbn = isbn;
		this.publisher = publisher;
		this.publishingDate = publishingDate;
		this.language = language;
		this.pages = pages;
		this.description = description;
		this.imageLink = imageLink;
	}

	public Book(Long id, String title, String authors, String isbn, String publisher, String publishingDate,
			String language, int pages, String description, String imageLink,
			Version version, Integer copies) {
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.isbn = isbn;
		this.publisher = publisher;
		this.publishingDate = publishingDate;
		this.language = language;
		this.pages = pages;
		this.description = description;
		this.imageLink = imageLink;
		this.version = version;
		this.copies = copies;
	}
}


