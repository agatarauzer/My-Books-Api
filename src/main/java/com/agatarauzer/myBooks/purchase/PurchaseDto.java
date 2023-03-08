package com.agatarauzer.myBooks.purchase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto {
	private Double price;
	private String bookstore;
}
