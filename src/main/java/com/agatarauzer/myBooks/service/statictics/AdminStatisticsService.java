package com.agatarauzer.myBooks.service.statictics;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.repository.BookRepository;
import com.agatarauzer.myBooks.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminStatisticsService {
	
	private final UserRepository userRepository;
	private final BookRepository bookRepository;
	
	public Map<String, Long> findUsersRegisteredByMonth() {
		return userRepository.findByRegistrationMonth();
	}
	
	public Map<String, Long> findUsersRegistredByMonthByRole(String role) {
		return userRepository.findByRegistrationMonthAndRole(role);
	}
	
	public Double calculateAvgNumberOfBooksForUsers() {
		List<Book> books = IterableUtils.toList(bookRepository.findAll());
		if (books.isEmpty()) {
			return 0.0;
		}
		return bookRepository.findAvgBooksForUser();
	}
}
