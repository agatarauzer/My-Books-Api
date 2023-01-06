package com.agatarauzer.myBooks.rental;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.book.BookRepository;
import com.agatarauzer.myBooks.book.domain.Book;
import com.agatarauzer.myBooks.exception.notFound.BookNotFoundException;
import com.agatarauzer.myBooks.exception.notFound.RentalNotFoundException;
import com.agatarauzer.myBooks.rental.domain.Rental;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalService {
	
	private final RentalRepository rentalRepository;
	private final BookRepository bookRepository;
	
	public Rental getRentalForBook(Long bookId) {
		bookRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		return rentalRepository.findByBookId(bookId).orElseThrow(() -> new RentalNotFoundException("Rental for book: " + bookId + "not found"));
	}
	
	public Rental saveRental(Long bookId, Rental rental) {
		Book book = bookRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		book.setRental(rental);
		bookRepository.save(book);
		log.info("Rental for book with id: " + bookId + " was saved in db");
		return rental;
	}
	
	public Rental updateRental(Long rentalId, Rental rental) {
		Rental rentalUpdated = rentalRepository.findById(rentalId)
			.orElseThrow(() -> new RentalNotFoundException("Rental id not found: " + rentalId));
		rentalUpdated.setStatus(rental.getStatus());
		rentalUpdated.setName(rental.getName());
		rentalUpdated.setStartDate(rental.getStartDate());
		rentalUpdated.setEndDate(rental.getEndDate());
		rentalUpdated.setNotes(rental.getNotes());
		log.info("Rental with id: " + rentalId + "was updated");
		return rentalRepository.save(rentalUpdated);
	}
	
	public void deleteRental(Long bookId, Long rentalId) {
		Book book = bookRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		book.setRental(null);
		rentalRepository.findById(rentalId)
			.orElseThrow(() -> new RentalNotFoundException("Rental id not found: " + rentalId));
		rentalRepository.deleteById(rentalId);
		log.info("Rental with id: " + rentalId  + " was deleted");
	}
}
