package com.agatarauzer.myBooks.mapper;

import org.springframework.stereotype.Component;

import com.agatarauzer.myBooks.domain.Purchase;
import com.agatarauzer.myBooks.dto.PurchaseDto;

@Component
public class PurchaseMapper {
	
	public PurchaseDto mapToPurchaseDto(Purchase purchase) {
		return PurchaseDto.builder()
				.id(purchase.getId())
				.price(purchase.getPrice())
				.purchaseDate(purchase.getPurchaseDate())
				.boughtFrom(purchase.getBoughtFrom())
				.build();
	}
	
	public Purchase mapToPurchase(PurchaseDto purchaseDto) {
		return Purchase.builder()
				.id(purchaseDto.getId())
				.price(purchaseDto.getPrice())
				.purchaseDate(purchaseDto.getPurchaseDate())
				.boughtFrom(purchaseDto.getBoughtFrom())
				.build();
	}
}

