package com.agatarauzer.myBooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agatarauzer.myBooks.domain.Purchase;
import com.agatarauzer.myBooks.dto.PurchaseDto;
import com.agatarauzer.myBooks.mapper.PurchaseMapper;
import com.agatarauzer.myBooks.service.PurchaseService;

@RestController
@RequestMapping("/v1/users/{userId}/books/{bookId}")
public class PurchaseController {
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private PurchaseMapper purchaseMapper;
	
	
	@GetMapping("/purchases")
	public PurchaseDto getReadingForBook(@PathVariable Long bookId) {
		Purchase purchase = purchaseService.getPurchaseForBook(bookId);
		return purchaseMapper.mapToPurchaseDto(purchase);
	}
	
	@PostMapping("/purchases")
	public PurchaseDto addPurchase(@PathVariable Long bookId, @RequestBody PurchaseDto purchaseDto) {
		Purchase purchase = purchaseMapper.mapToPurchase(purchaseDto);
		purchaseService.savePurchase(bookId, purchase);
		return purchaseDto;
	}
	
	@PutMapping("/purchases/{purchaseId}")
	public PurchaseDto updatePurchase(@PathVariable Long purchaseId, @RequestBody PurchaseDto purchaseDto) {
		Purchase purchase = purchaseMapper.mapToPurchase(purchaseDto);
		purchaseService.updatePurchase(purchaseId, purchase);
		return purchaseDto;
	}
	
	@DeleteMapping("/purchases/{purchaseId}")
	public String deletePurchase(@PathVariable Long purchaseId) {
		purchaseService.deletePurchase(purchaseId);
		return "Deleted purchase: " + purchaseId;
	}
}
