package com.agatarauzer.myBooks.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
	
	@Column(name="title")
	private String title;
	
	@Column(name="ISBN")
	private int ISBN;
	
	@ManyToMany
	@JoinTable(name="book_author",
				joinColumns=@JoinColumn(name="book_id"),
				inverseJoinColumns=@JoinColumn(name="author_id") )
	private List<Author> authors;
	
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
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="id")
	private Reading reading;
	
}
