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
		return new Book(googleBookForUserDto.getTitle(),
						googleBookForUserDto.getAuthors(),
						googleBookForUserDto.getISBN(),
						googleBookForUserDto.getPublisher(),
						googleBookForUserDto.getPublishingDate(),
						googleBookForUserDto.getLanguage(),
						googleBookForUserDto.getPages(),
						googleBookForUserDto.getDescription(),
						googleBookForUserDto.getImageLink()
						);			
	}
	
	public List<GoogleBookForUserDto> mapToGoogleBookForUserDtoList (GoogleBooksSearchResultDto googleSearchResultDto) {
		List<GoogleBookBasicDto> booksBasic = googleSearchResultDto.getBooks();
		List<GoogleBookForUserDto> booksForUser = new ArrayList<>();
		for (GoogleBookBasicDto bookBasic : booksBasic) {
			GoogleBookDto googleBookDto = bookBasic.getGoogleBookDto();
			String textSnippet = Optional.ofNullable(bookBasic.getSearchInfo())
							.map(b -> b.getTextSnippet())
							.orElse("");
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
		String ISBN = Optional.ofNullable(googleBookDto.getISBN())
							.map(b -> b.stream()
								.map(c -> c.getIdentifier())
								.collect(Collectors.joining(", ")))
							.orElse("ISBN_number");
		String link = Optional.ofNullable(googleBookDto.getImageLink())
							.map(b -> b.getLink())
							.orElse(null);
		
		return new GoogleBookForUserDto(fullTitle,
										authors,
										ISBN,
										googleBookDto.getPublisher(),
										googleBookDto.getPublishingDate(),
										googleBookDto.getLanguage(),
										googleBookDto.getPages(),
										description,
										link);
	}
}

