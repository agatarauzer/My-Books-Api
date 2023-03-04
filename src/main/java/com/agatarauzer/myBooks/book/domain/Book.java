package com.agatarauzer.myBooks.book.domain;

import com.agatarauzer.myBooks.bookTransfer.domain.BookTransfer;
import com.agatarauzer.myBooks.owner.Owner;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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
	@JoinColumn(name = "owner_id")
	private Owner owner;
	
	@OneToMany(mappedBy="book", cascade = CascadeType.PERSIST)
	private List<BookTransfer> bookTransfers;
}
