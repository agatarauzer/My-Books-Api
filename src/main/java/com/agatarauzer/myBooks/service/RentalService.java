package com.agatarauzer.myBooks.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.domain.Rental;
import com.agatarauzer.myBooks.domain.enums.RentalStatus;
import com.agatarauzer.myBooks.exception.BookNotFoundException;
import com.agatarauzer.myBooks.exception.ReadingNotFoundException;
import com.agatarauzer.myBooks.exception.RentalNotFoundException;
import com.agatarauzer.myBooks.repository.BookRepository;
import com.agatarauzer.myBooks.repository.RentalRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RentalService {
	
	private final RentalRepository rentalRepository;
	private final BookRepository bookRepository;
	
	public Rental getRentalForBook(Long bookId) {
		bookRepository.findById(bookId)
			.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		return rentalRepository.findByBookId(bookId);
	}
	
	public Rental saveRental(Long bookId, Rental rental) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		book.setRental(rental);
		bookRepository.save(book);
		return rental;
	}
	
	public Rental updateRental(Long rentalId, Rental rental) {
		Rental rentalUpdated = rentalRepository.findById(rentalId)
				.orElseThrow(() -> new RentalNotFoundException("Rental id not found: " + rentalId));
		
		RentalStatus statusUpdated = Optional.ofNullable(rental.getStatus()).orElse(rentalUpdated.getStatus());
		String nameUpdated = Optional.ofNullable(rental.getName()).orElse(rentalUpdated.getName());
		LocalDate startDateUpdated = Optional.ofNullable(rental.getStartDate()).orElse(rentalUpdated.getStartDate());
		LocalDate endDateUpdated = Optional.ofNullable(rental.getEndDate()).orElse(rentalUpdated.getEndDate());
		String notesUpdated = Optional.ofNullable(rental.getNotes()).orElse(rentalUpdated.getNotes());
		
		rentalUpdated.setStatus(statusUpdated);
		rentalUpdated.setName(nameUpdated);
		rentalUpdated.setStartDate(startDateUpdated);
		rentalUpdated.setEndDate(endDateUpdated);
		rentalUpdated.setNotes(notesUpdated);
		
		return rentalRepository.save(rentalUpdated);
	}
	
	public void deleteRental(Long bookId, Long rentalId) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new BookNotFoundException("Book id not found: " + bookId));
		book.setRental(null);
		try {
			rentalRepository.deleteById(rentalId);
		} catch (DataAccessException exc) {
			throw new ReadingNotFoundException("Rental id not found: " + rentalId);
		}
	}
}
