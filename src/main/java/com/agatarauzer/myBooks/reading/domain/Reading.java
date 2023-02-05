package com.agatarauzer.myBooks.reading.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name="readings")
public class Reading {
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reading_id")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private ReadingStatus status;
	
	@Column(name="start_date")
	private LocalDate startDate;
	
	@Column(name="end_date")
	private LocalDate endDate;
	
	@Column(name="readed_pages")
	private Integer readedPages;
	
	private Integer rate;
	private String notes;
	
	@OneToOne
	@MapsId
	@JoinColumn(name="book_id")
	private Book book;
}
