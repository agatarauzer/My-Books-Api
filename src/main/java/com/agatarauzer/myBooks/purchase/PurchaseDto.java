package com.agatarauzer.myBooks.purchase;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto {
	private Long id;
	private Double price;
	private LocalDate purchaseDate;
	private String boughtFrom;
}
