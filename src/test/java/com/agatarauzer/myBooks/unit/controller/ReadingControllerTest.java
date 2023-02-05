package com.agatarauzer.myBooks.unit.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.agatarauzer.myBooks.book.BookService;
import com.agatarauzer.myBooks.book.domain.Book;
import com.agatarauzer.myBooks.book.domain.Version;
import com.agatarauzer.myBooks.reading.ReadingController;
import com.agatarauzer.myBooks.reading.ReadingDto;
import com.agatarauzer.myBooks.reading.ReadingMapper;
import com.agatarauzer.myBooks.reading.ReadingService;
import com.agatarauzer.myBooks.reading.domain.Reading;
import com.agatarauzer.myBooks.reading.domain.ReadingStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SpringJUnitWebConfig
@WebMvcTest(ReadingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReadingControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private ReadingMapper readingMapper;
	
	@MockBean
	private ReadingService readingService;
	
	@MockBean
	private BookService bookService;
	
	private Long readingId;
	private Reading reading; 
	private ReadingDto  readingDto;
	private Long bookId;
	private Book book;
	
	@BeforeEach
	public void prepareTestData() {
		bookId = 1L;
		readingId = 1L;
		book = Book.builder()
				.id(bookId)
				.title("Java. Podstawy. Wydanie IX")
				.authors("Cay S. Horstmann,Gary Cornell")
				.isbn("8324677615, 9788324677610")
				.publisher("Helion")
				.publishingDate("2013-12-09")
				.language("pl")
				.pages(864)
				.description("Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...")
				.imageLink("http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api")
				.version(Version.BOOK)
				.build();
		reading = Reading.builder()
				.id(readingId)
				.status(ReadingStatus.READED)
				.startDate(LocalDate.of(2020, 5, 9))
				.endDate(LocalDate.of(2022, 1, 24))
				.readedPages(820)
				.rate(4)
				.notes("Java basics...")
				.book(book)
				.build();	
		readingDto = ReadingDto.builder()
				.id(readingId)
				.status(ReadingStatus.READED)
				.startDate(LocalDate.of(2020, 5, 9))
				.endDate(LocalDate.of(2022, 1, 24))
				.readedPages(820)
				.rate(4)
				.notes("Java basics...")
				.build();
	}
	
	@Test
	public void shouldGetReadingForBook() throws Exception {
		when(readingService.getReadingForBook(bookId)).thenReturn(reading);
		when(readingMapper.mapToReadingDto(reading)).thenReturn(readingDto);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/v1/users/1/books/{bookId}/readings", bookId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(200))
			.andExpect(jsonPath("$.status", is("READED")))
			.andExpect(jsonPath("$.startDate", is("2020-05-09")))
			.andExpect(jsonPath("$.endDate", is("2022-01-24")))
			.andExpect(jsonPath("$.readedPages", is(820)))
			.andExpect(jsonPath("$.rate", is(4)))
			.andExpect(jsonPath("$.notes", is("Java basics...")));	
	}
	
	@Test 
	public void shouldAddReading() throws Exception {
		when(readingService.saveReadingForBook(bookId, reading)).thenReturn(reading);
		when(readingMapper.mapToReading(readingDto)).thenReturn(reading);
		
		Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
			.create();
		String jsonReadingDto = gson.toJson(readingDto);
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/v1/users/1/books/{bookId}/readings", bookId)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonReadingDto))
			.andExpect(status().is(201))
			.andExpect(jsonPath("$.status", is("READED")))
			.andExpect(jsonPath("$.startDate", is("2020-05-09")))
			.andExpect(jsonPath("$.endDate", is("2022-01-24")))
			.andExpect(jsonPath("$.readedPages", is(820)))
			.andExpect(jsonPath("$.rate", is(4)))
			.andExpect(jsonPath("$.notes", is("Java basics...")));	
	}
	
	@Test
	public void shouldUpdateReading() throws Exception {
		Reading readingUpdated = Reading.builder()
				.id(readingId)
				.status(ReadingStatus.IN_READING)
				.startDate(LocalDate.of(2021, 6, 10))
				.endDate(LocalDate.of(2022, 2, 25))
				.readedPages(350)
				.rate(5)
				.notes("Java basics")
				.build();
		when(readingService.updateReading(readingUpdated)).thenReturn(readingUpdated);
		
		Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
			.create();
		String jsonReadingUpdated = gson.toJson(readingUpdated);
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/v1/users/1/books/{bookId}/readings/{readingId}", bookId, readingId)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonReadingUpdated))
			.andExpect(status().is(200))
			.andExpect(jsonPath("$.status", is("IN_READING")))
			.andExpect(jsonPath("$.startDate", is("2021-06-10")))
			.andExpect(jsonPath("$.endDate", is("2022-02-25")))
			.andExpect(jsonPath("$.readedPages", is(350)))
			.andExpect(jsonPath("$.rate", is(5)))
			.andExpect(jsonPath("$.notes", is("Java basics")));	
	}
	
	@Test
	public void shouldDeleteReading() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/v1/users/1/books/{bookId}/readings/{readingId}", bookId, readingId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(200));	
		
		verify(readingService, times(1)).deleteReading(readingId);
	}	
}

