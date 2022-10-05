package com.agatarauzer.myBooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.domain.Purchase;
import com.agatarauzer.myBooks.exception.BookNotFoundException;
import com.agatarauzer.myBooks.exception.PurchaseNotFoundException;
import com.agatarauzer.myBooks.repository.BookRepository;
import com.agatarauzer.myBooks.repository.PurchaseRepository;

@Service
public class PurchaseService {
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	
	public Purchase getPurchaseForBook(Long bookId) {
		bookRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		return purchaseRepository.findByBookId(bookId);
	}
	
	public Purchase savePurchase(Long bookId, Purchase purchase) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		purchase.setBook(book);
		return purchaseRepository.save(purchase);
	}
	
	public Purchase updatePurchase(Long purchaseId, Purchase purchase) {
		Purchase purchaseUpdated = purchaseRepository.findById(purchaseId)
				.orElseThrow(() -> new PurchaseNotFoundException("Purchase id not found: " + purchaseId));
		purchaseUpdated.setPrice(purchase.getPrice());
		purchaseUpdated.setPurchaseDate(purchase.getPurchaseDate());
		purchaseUpdated.setBoughtFrom(purchase.getBoughtFrom());
		return purchaseRepository.save(purchaseUpdated);
	}
	
	public void deleteById(Long purchaseId) {
		try {
			purchaseRepository.deleteById(purchaseId);
		} catch (DataAccessException exc) {
			throw new PurchaseNotFoundException("Purchase id not found: " + purchaseId);
		}
	}
}
