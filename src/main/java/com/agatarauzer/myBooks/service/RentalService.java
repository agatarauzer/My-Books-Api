package com.agatarauzer.myBooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.domain.Rental;
import com.agatarauzer.myBooks.repository.BookRepository;
import com.agatarauzer.myBooks.repository.RentalRepository;

@Service
public class RentalService {
	
	@Autowired
	private RentalRepository rentalRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	
	public Rental getRentalForBook(Long bookId) {
		return rentalRepository.findByBookId(bookId);
	}
	
	public Rental saveRental(Long bookId, Rental rental) {
		Book book = bookRepository.getReferenceById(bookId);
		rental.setBook(book);
		return rentalRepository.save(rental);
	}
	
	public Rental updateRental(Long rentalId, Rental rental) {
		Rental rentalUpdated = rentalRepository.getReferenceById(rentalId);
		rentalUpdated.setStatus(rental.getStatus());
		rentalUpdated.setName(rental.getName());
		rentalUpdated.setStartDate(rental.getStartDate());
		rentalUpdated.setEndDate(rental.getEndDate());
		rentalUpdated.setNotes(rental.getNotes());
		return rentalRepository.save(rentalUpdated);
	}
	
	public void deleteById(Long id) {
		rentalRepository.deleteById(id);
	}
}
