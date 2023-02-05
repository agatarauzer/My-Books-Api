package com.agatarauzer.myBooks.purchase;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PurchaseMapper {
	
	public Purchase mapToPurchase(PurchaseDto purchaseDto) {
		return Purchase.builder()
				.id(purchaseDto.getId())
				.price(purchaseDto.getPrice())
				.purchaseDate(purchaseDto.getPurchaseDate())
				.boughtFrom(purchaseDto.getBoughtFrom())
				.build();
	}
	
	public PurchaseDto mapToPurchaseDto(Purchase purchase) {
		return PurchaseDto.builder()
				.id(purchase.getId())
				.price(purchase.getPrice())
				.purchaseDate(purchase.getPurchaseDate())
				.boughtFrom(purchase.getBoughtFrom())
				.build();
	}
}

