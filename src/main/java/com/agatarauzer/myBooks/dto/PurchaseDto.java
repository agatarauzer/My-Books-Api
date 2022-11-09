package com.agatarauzer.myBooks.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class PurchaseDto {
	private Long id;
	private Double price;
	private LocalDate purchaseDate;
	private String boughtFrom;
}
