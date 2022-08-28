package com.agatarauzer.myBooks.dto;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.agatarauzer.myBooks.domain.Reading;
import com.agatarauzer.myBooks.domain.Version;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookDto {
	
	private Long id;
	
	private String title;
	
	private List<String> author;
	
	private String ISBN;
	
	private String publisher;
	
	private String publishingYear;
	
	private String description;
	
	private int pages;
	
	private String language;
	
	private Double price;
	
	private LocalDate purchaseDate;
	
	private Version version;
	
	private Reading readingDetails;
}


