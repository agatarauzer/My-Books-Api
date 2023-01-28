package com.agatarauzer.myBooks.book.domain;

import java.time.LocalDate;

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
import javax.persistence.Table;

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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
}


