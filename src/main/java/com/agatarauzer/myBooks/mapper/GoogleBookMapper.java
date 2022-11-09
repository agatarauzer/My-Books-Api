package com.agatarauzer.myBooks.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookBasicDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookForUserDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBooksSearchResultDto;


@Service
public class GoogleBookMapper {
	
	public Book mapToBook(GoogleBookForUserDto googleBookForUserDto) {
		return Book.builder()
				.title(googleBookForUserDto.getTitle())
				.authors(googleBookForUserDto.getAuthors())
				.isbn(googleBookForUserDto.getIsbn())
				.publisher(googleBookForUserDto.getPublisher())
				.publishingDate(googleBookForUserDto.getPublishingDate())
				.language(googleBookForUserDto.getLanguage())
				.pages(googleBookForUserDto.getPages())
				.description(googleBookForUserDto.getDescription())
				.imageLink(googleBookForUserDto.getImageLink())
				.build();
	}
	
	public List<GoogleBookForUserDto> mapToGoogleBookForUserDtoList (GoogleBooksSearchResultDto googleSearchResultDto) {
		List<GoogleBookBasicDto> booksBasic = googleSearchResultDto.getBooks();
		List<GoogleBookForUserDto> booksForUser = new ArrayList<>();
		for (GoogleBookBasicDto bookBasic : booksBasic) {
			GoogleBookDto googleBookDto = bookBasic.getGoogleBookDto();
			String textSnippet = Optional.ofNullable(bookBasic.getSearchInfo())
							.map(b -> b.getTextSnippet())
							.orElse(null);
			GoogleBookForUserDto bookForUser = mapToGoogleBookForUserDto(googleBookDto, textSnippet);
			booksForUser.add(bookForUser);
		}
		return booksForUser;
	}
	
	private GoogleBookForUserDto mapToGoogleBookForUserDto(GoogleBookDto googleBookDto, String textSnippet) {
		String subtitle = Optional.ofNullable(googleBookDto.getSubtitle())
							.orElse("");
		String fullTitle = (googleBookDto.getTitle() + " " + subtitle).trim();
		String authors = Optional.ofNullable(googleBookDto.getAuthors())
							.map(b -> b.stream()
								.collect(Collectors.joining(", ")))
							.orElse(null);
		String description = Optional.ofNullable(googleBookDto.getDescription())
							.orElse(textSnippet);
		String ISBN = Optional.ofNullable(googleBookDto.getIsbn())
							.map(b -> b.stream()
								.map(c -> c.getIdentifier())
								.collect(Collectors.joining(", ")))
							.orElse(null);
		String link = Optional.ofNullable(googleBookDto.getImageLink())
							.map(b -> b.getLink())
							.orElse(null);
		
		return GoogleBookForUserDto.builder()
				.title(fullTitle)
				.authors(authors)
				.isbn(ISBN)
				.publisher(googleBookDto.getPublisher())
				.publishingDate(googleBookDto.getPublishingDate())
				.language(googleBookDto.getLanguage())
				.pages(googleBookDto.getPages())
				.description(description)
				.imageLink(link)
				.build();
	}
}

