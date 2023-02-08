package com.agatarauzer.myBooks.purchase;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.book.BookService;
import com.agatarauzer.myBooks.book.domain.Book;
import com.agatarauzer.myBooks.exception.notFound.PurchaseNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseService {
	
	private final PurchaseRepository purchaseRepository;
	private final BookService bookService;

	public Purchase getPurchaseForBook(Long bookId) {
		return purchaseRepository.findByBookId(bookId).orElseThrow(() -> new PurchaseNotFoundException("Purchase for book: " + bookId + "not found"));	
	}
	
	public Purchase savePurchaseForBook(Long bookId, Purchase purchase) {
		Book book = bookService.findBookById(bookId);
		purchase.setBook(book);
		purchaseRepository.save(purchase);
		log.info("Purchase for book with id: " + purchase.getBook().getId() + " was saved in db");
		return purchase;
	}
	
	public Purchase updatePurchase(Purchase purchase) {
		purchaseRepository.findById(purchase.getId())
			.orElseThrow(() -> new PurchaseNotFoundException("Purchase id not found: " + purchase.getId()));
		purchaseRepository.save(purchase);
		log.info("Purchase with id: " + purchase.getId() + " was updated");
		return purchase;
	}
	
	public void deletePurchase(Long purchaseId) {
		purchaseRepository.findById(purchaseId)
			.orElseThrow(() -> new PurchaseNotFoundException("Purchase id not found: " + purchaseId));
		purchaseRepository.deleteById(purchaseId);
		log.info("Purchase with id: " + purchaseId + " was deleted");
	}
}


