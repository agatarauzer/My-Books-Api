package com.agatarauzer.myBooks.rental.domain;

import com.agatarauzer.myBooks.activity.ActivityOnBook;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="rentals")
public class Rental extends ActivityOnBook {
	@Enumerated(EnumType.STRING)
	private RentalStatus status;
	private String name;
	@Column(name="predicted_return_date")
	private LocalDate predictedReturnDate;
	private String notes;
}
