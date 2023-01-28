package com.agatarauzer.myBooks.purchase;

import org.springframework.stereotype.Component;

import com.agatarauzer.myBooks.book.BookService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PurchaseMapper {
	
	private final BookService bookService;
	
	public Purchase mapToPurchase(PurchaseDto purchaseDto) {
		return Purchase.builder()
				.id(purchaseDto.getId())
				.price(purchaseDto.getPrice())
				.purchaseDate(purchaseDto.getPurchaseDate())
				.boughtFrom(purchaseDto.getBoughtFrom())
				.book(bookService.findBookById(purchaseDto.getBookId()))
				.build();
	}
	
	public PurchaseDto mapToPurchaseDto(Purchase purchase) {
		return PurchaseDto.builder()
				.id(purchase.getId())
				.price(purchase.getPrice())
				.purchaseDate(purchase.getPurchaseDate())
				.boughtFrom(purchase.getBoughtFrom())
				.bookId(purchase.getBook().getId())
				.build();
	}
}

