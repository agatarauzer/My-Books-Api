package com.agatarauzer.myBooks.book.domain;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.agatarauzer.myBooks.purchase.Purchase;
import com.agatarauzer.myBooks.reading.domain.Reading;
import com.agatarauzer.myBooks.rental.domain.Rental;
import com.agatarauzer.myBooks.user.User;

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
@Table(name="books")
public class Book {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="book_id")
	private Long id;
	
	private String title;
	private String authors;
	private String isbn;
	private String publisher;
	
	@Column(name="publishing_date")
	private String publishingDate;
	
	private String language;
	private Integer pages;
	private String description;
	
	@Column(name="image_link")
	private String imageLink;
	
	@Enumerated(EnumType.STRING)
	private Version version;
	
	@Column(name="creation_date")
	private LocalDate created;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToOne(mappedBy="book", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Reading reading;
	
	@OneToOne(mappedBy="book", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Purchase purchase;
	
	@OneToMany(mappedBy="book", cascade = CascadeType.ALL)
	private List<Rental> rentals;
}

