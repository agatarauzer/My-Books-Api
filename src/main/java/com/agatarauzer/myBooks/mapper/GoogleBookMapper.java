package com.agatarauzer.myBooks.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.GoogleBookForUserDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.ImageLinkDto;
import com.agatarauzer.myBooks.dto.GoogleBooks.IsbnDto;


@Service
public class GoogleBookMapper {
	
	public GoogleBookForUserDto mapToGoogleBookForUserDto(GoogleBookDto googleBookDto) {
		
		//retrieve subtitle
		String fullTitle = "";
		if (googleBookDto.getSubtitle() == null) {
			fullTitle = googleBookDto.getTitle();
		}
		else {
			fullTitle = googleBookDto.getTitle() + " " + googleBookDto.getSubtitle();
		}
		
		//retrieve authors list
		String authors = "";
		List<String> authorsDto = googleBookDto.getAuthors();
		if (authorsDto == null) {
			authors = null;
		}
		else {	
			authors = authorsDto.stream().collect(Collectors.joining(","));
		}
		
		//retrieve list of isbns numbers
		String ISBNs = "";
		List<IsbnDto> ISBNDtos = googleBookDto.getISBNs();
		if (ISBNDtos.isEmpty()) {
			ISBNs = "empty";
		}
		else {
			ISBNs = ISBNDtos.stream().map(c -> c.getIdentifier()).collect(Collectors.joining(", "));	
		}
		
		//retrieve image link
		String link = "";
		ImageLinkDto imageLink = googleBookDto.getImageLink();
		if (imageLink == null) {
			link = null;
		}
		else {
			link = imageLink.getLink();
		}
	
		return new GoogleBookForUserDto(fullTitle,
										authors,
										googleBookDto.getPublisher(),
										googleBookDto.getPublishingDate(),
										googleBookDto.getDescription(),
										ISBNs,
										googleBookDto.getPages(),
										googleBookDto.getLanguage(),
										link);
	}
	
	public Book mapToBook(GoogleBookForUserDto googleBookForUserDto) {
		return new Book(googleBookForUserDto.getTitle(),
						googleBookForUserDto.getAuthors(),
						googleBookForUserDto.getISBNs(),
						googleBookForUserDto.getPublisher(),
						googleBookForUserDto.getPublishingDate(),
						googleBookForUserDto.getLanguage(),
						googleBookForUserDto.getPages(),
						googleBookForUserDto.getDescription(),
						googleBookForUserDto.getImageLink()
						);			
	}
}

