package com.agatarauzer.myBooks.mapper;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Purchase;
import com.agatarauzer.myBooks.dto.PurchaseDto;


@Service
public class PurchaseMapper {
	public PurchaseDto mapToPurchaseDto(Purchase purchase) {
		return new PurchaseDto(purchase.getId(),
				purchase.getPrice(),
				purchase.getPurchaseDate(),
				purchase.getBoughtFrom());
	}
	
	public Purchase mapToPurchase(PurchaseDto purchaseDto) {
		return new Purchase(purchaseDto.getId(),
				purchaseDto.getPrice(),
				purchaseDto.getPurchaseDate(),
				purchaseDto.getBoughtFrom());
	}
}

