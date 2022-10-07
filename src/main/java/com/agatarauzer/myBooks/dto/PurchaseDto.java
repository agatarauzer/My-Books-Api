package com.agatarauzer.myBooks.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PurchaseDto {
	private Long id;
	private Double price;
	private LocalDate purchaseDate;
	private String boughtFrom;
}