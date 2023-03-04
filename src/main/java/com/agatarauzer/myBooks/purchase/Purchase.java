package com.agatarauzer.myBooks.purchase;

import com.agatarauzer.myBooks.activity.ActivityOnBook;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="purchases")
public class Purchase extends ActivityOnBook {

	private Double price;
	@Column(name="bookstore")
	private String bookstore;
}
