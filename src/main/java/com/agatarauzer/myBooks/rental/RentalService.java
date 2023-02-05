package com.agatarauzer.myBooks.rental;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.book.BookService;
import com.agatarauzer.myBooks.book.domain.Book;
import com.agatarauzer.myBooks.exception.notFound.RentalNotFoundException;
import com.agatarauzer.myBooks.rental.domain.Rental;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentalService {
	
	private final RentalRepository rentalRepository;
	private final BookService bookService;
	
	public List<Rental> getRentalsForBook(Long bookId) {
		return rentalRepository.findByBookId(bookId).orElseThrow(() -> new RentalNotFoundException("Rental for book: " + bookId + "not found"));
	}
	
	public Rental saveRentalForBook(Long bookId, Rental rental) {
		Book book = bookService.findBookById(bookId);
		rental.setBook(book);
		rentalRepository.save(rental);
		log.info("Rental for book with id: " + rental.getBook().getId() + " was saved in db");
		return rental;
	}
	
	public Rental updateRental(Rental rental) {
		Rental rentalUpdated = rentalRepository.findById(rental.getId())
			.orElseThrow(() -> new RentalNotFoundException("Rental id not found: " + rental.getId()));
		rentalUpdated.setStatus(rental.getStatus());
		rentalUpdated.setName(rental.getName());
		rentalUpdated.setStartDate(rental.getStartDate());
		rentalUpdated.setEndDate(rental.getEndDate());
		rentalUpdated.setNotes(rental.getNotes());
		log.info("Rental with id: " + rental.getId() + "was updated");
		return rentalRepository.save(rentalUpdated);
	}
	
	public void deleteRental(Long rentalId) {
		rentalRepository.findById(rentalId)
			.orElseThrow(() -> new RentalNotFoundException("Rental id not found: " + rentalId));
		rentalRepository.deleteById(rentalId);
		log.info("Rental with id: " + rentalId  + " was deleted");
	}
}
