package com.agatarauzer.myBooks.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/users/{userId}/books/{bookId}/purchases")
@PreAuthorize("hasRole('USER_PAID') or hasRole('ADMIN')")
@RequiredArgsConstructor
public class PurchaseController {
	
	private final PurchaseService purchaseService;
	private final PurchaseMapper purchaseMapper;
	
	@GetMapping
	public ResponseEntity<PurchaseDto> getReadingForBook(@PathVariable final Long bookId) {
		Purchase purchase = purchaseService.getPurchaseForBook(bookId);
		return ResponseEntity.ok(purchaseMapper.mapToPurchaseDto(purchase));
	}
	
	@PostMapping
	public ResponseEntity<PurchaseDto> addPurchase(@PathVariable final Long bookId, @RequestBody final PurchaseDto purchaseDto) {
		Purchase purchase = purchaseMapper.mapToPurchase(purchaseDto);
		purchaseService.savePurchase(bookId, purchase);
		return new ResponseEntity<PurchaseDto>(purchaseDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/{purchaseId}")
	public ResponseEntity<PurchaseDto> updatePurchase(@PathVariable final Long purchaseId, @RequestBody final PurchaseDto purchaseDto) {
		Purchase purchase = purchaseMapper.mapToPurchase(purchaseDto);
		purchaseService.updatePurchase(purchaseId, purchase);
		return ResponseEntity.ok(purchaseDto);
	}
	
	@DeleteMapping("/{purchaseId}")
	public ResponseEntity<String> deletePurchase(@PathVariable final Long bookId, @PathVariable final Long purchaseId) {
		purchaseService.deletePurchase(bookId, purchaseId);
		return ResponseEntity.ok().body("Deleted purchase: " + purchaseId);
	}
}
