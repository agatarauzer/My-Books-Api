package com.agatarauzer.myBooks.purchase;

import org.springframework.stereotype.Component;

@Component
public class PurchaseMapper {
	
	public Purchase mapToPurchase(PurchaseDto purchaseDto) {
		return Purchase.builder()
				.price(purchaseDto.getPrice())
				.bookstore(purchaseDto.getBookstore())
				.build();
	}
	
	public PurchaseDto mapToPurchaseDto(Purchase purchase) {
		return PurchaseDto.builder()
				.price(purchase.getPrice())
				.bookstore(purchase.getBookstore())
				.build();
	}
}

