package com.agatarauzer.myBooks.bookTransfer.domain;

import com.agatarauzer.myBooks.activity.ActivityOnBook;
import com.agatarauzer.myBooks.book.domain.Book;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="book_transfers")
public class BookTransfer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="transfer_type")
	@Enumerated(EnumType.STRING)
	private TransferType transferType;
	@Column(name="transfer_date")
	private LocalDate transferDate;
	@Column(name="transfer_status")
	@Enumerated(EnumType.STRING)
	private TransferStatus transferStatus;
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	@ManyToOne
	@JoinColumn(name = "activity_id")
	private ActivityOnBook activityOnBook;
}