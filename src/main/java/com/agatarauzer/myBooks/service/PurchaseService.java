package com.agatarauzer.myBooks.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.domain.Purchase;
import com.agatarauzer.myBooks.exception.BookNotFoundException;
import com.agatarauzer.myBooks.exception.PurchaseNotFoundException;
import com.agatarauzer.myBooks.repository.BookRepository;
import com.agatarauzer.myBooks.repository.PurchaseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {
	
	private final PurchaseRepository purchaseRepository;
	private final BookRepository bookRepository;
	
	public Purchase getPurchaseForBook(Long bookId) {
		bookRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		return purchaseRepository.findByBookId(bookId);
	}
	
	public Purchase savePurchase(Long bookId, Purchase purchase) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		book.setPurchase(purchase);
		bookRepository.save(book);
		return purchase;
	}
	
	public Purchase updatePurchase(Long purchaseId, Purchase purchase) {
		Purchase purchaseUpdated = purchaseRepository.findById(purchaseId)
				.orElseThrow(() -> new PurchaseNotFoundException("Purchase id not found: " + purchaseId));
	
		Double priceUpdated = Optional.ofNullable(purchase.getPrice()).orElse(purchaseUpdated.getPrice());
		LocalDate purchaseDateUpdated = Optional.ofNullable(purchase.getPurchaseDate()).orElse(purchaseUpdated.getPurchaseDate());
		String boughtFromUpdated = Optional.ofNullable(purchase.getBoughtFrom()).orElse(purchaseUpdated.getBoughtFrom());
		
		purchaseUpdated.setPrice(priceUpdated);
		purchaseUpdated.setPurchaseDate(purchaseDateUpdated);
		purchaseUpdated.setBoughtFrom(boughtFromUpdated);
		
		return purchaseRepository.save(purchaseUpdated);
	}
	
	public void deletePurchase(Long bookId, Long purchaseId) {
		Book book = bookRepository.findById(bookId).get();
		book.setPurchase(null);
		try {
			purchaseRepository.deleteById(purchaseId);
		} catch (DataAccessException exc) {
			throw new PurchaseNotFoundException("Purchase id not found: " + purchaseId);
		}
	}
}
