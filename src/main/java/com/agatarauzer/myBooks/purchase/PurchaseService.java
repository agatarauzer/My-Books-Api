package com.agatarauzer.myBooks.purchase;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.book.Book;
import com.agatarauzer.myBooks.book.BookRepository;
import com.agatarauzer.myBooks.exception.notFound.BookNotFoundException;
import com.agatarauzer.myBooks.exception.notFound.PurchaseNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseService {
	
	private final PurchaseRepository purchaseRepository;
	private final BookRepository bookRepository;
	
	public Purchase getPurchaseForBook(Long bookId) {
		bookRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		return purchaseRepository.findByBookId(bookId).orElseThrow(() -> new PurchaseNotFoundException("Purchase for book: " + bookId + "not found"));	
	}
	
	public Purchase savePurchase(Long bookId, Purchase purchase) {
		Book book = bookRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		book.setPurchase(purchase);
		bookRepository.save(book);
		log.info("Purchase for book with id: " + bookId + " was saved in db");
		return purchase;
	}
	
	public Purchase updatePurchase(Long purchaseId, Purchase purchase) {
		Purchase purchaseUpdated = purchaseRepository.findById(purchaseId)
			.orElseThrow(() -> new PurchaseNotFoundException("Purchase id not found: " + purchaseId));
		purchaseUpdated.setPrice(purchase.getPrice());
		purchaseUpdated.setPurchaseDate(purchase.getPurchaseDate());
		purchaseUpdated.setBoughtFrom(purchase.getBoughtFrom());
		log.info("Purchase with id: " + purchaseId + " was updated");
		return purchaseRepository.save(purchaseUpdated);
	}
	
	public void deletePurchase(Long bookId, Long purchaseId) {
		Book book = bookRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		book.setPurchase(null);
		purchaseRepository.findById(purchaseId)
			.orElseThrow(() -> new PurchaseNotFoundException("Purchase id not found: " + purchaseId));
		purchaseRepository.deleteById(purchaseId);
			log.info("Purchase with id: " + purchaseId + " was deleted");
	}
}
