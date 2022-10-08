package com.agatarauzer.myBooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.domain.Rental;
import com.agatarauzer.myBooks.exception.BookNotFoundException;
import com.agatarauzer.myBooks.exception.ReadingNotFoundException;
import com.agatarauzer.myBooks.exception.RentalNotFoundException;
import com.agatarauzer.myBooks.repository.BookRepository;
import com.agatarauzer.myBooks.repository.RentalRepository;

@Service
public class RentalService {
	
	@Autowired
	private RentalRepository rentalRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	
	public Rental getRentalForBook(Long bookId) {
		bookRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		return rentalRepository.findByBookId(bookId);
	}
	
	public Rental saveRental(Long bookId, Rental rental) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		rental.setBook(book);
		return rentalRepository.save(rental);
	}
	
	public Rental updateRental(Long rentalId, Rental rental) {
		Rental rentalUpdated = rentalRepository.findById(rentalId)
				.orElseThrow(() -> new RentalNotFoundException("Rental id not found: " + rentalId));
		rentalUpdated.setStatus(rental.getStatus());
		rentalUpdated.setName(rental.getName());
		rentalUpdated.setStartDate(rental.getStartDate());
		rentalUpdated.setEndDate(rental.getEndDate());
		rentalUpdated.setNotes(rental.getNotes());
		return rentalRepository.save(rentalUpdated);
	}
	
	public void deleteRental(Long rentalId) {
		try {
			rentalRepository.deleteById(rentalId);
		} catch (DataAccessException exc) {
			throw new ReadingNotFoundException("Rental id not found: " + rentalId);
		}
	}
}
