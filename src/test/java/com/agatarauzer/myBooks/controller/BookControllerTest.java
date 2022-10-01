package com.agatarauzer.myBooks.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.domain.User;
import com.agatarauzer.myBooks.domain.Version;
import com.agatarauzer.myBooks.dto.BookDto;
import com.agatarauzer.myBooks.mapper.BookMapper;
import com.agatarauzer.myBooks.service.BookService;
import com.agatarauzer.myBooks.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SpringJUnitWebConfig
@WebMvcTest(BookController.class)
public class BookControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private BookMapper bookMapper;
	
	@MockBean
	private BookService bookService;
	
	@MockBean
	private UserService userService;
	
	
	@Test 
	public void shouldGetUserBooks() throws Exception {
		Long userId = 1L; 
		Book book = new Book(1L, "Java. Podstawy. Wydanie IX","Cay S. Horstmann,Gary Cornell", "9788324677610, 8324677615", "Helion", "2013-12-09", "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
				89.00, LocalDate.of(2018, 9, 5), Version.PAPER);
		BookDto bookDto = new BookDto(1L, "Java. Podstawy. Wydanie IX","Cay S. Horstmann,Gary Cornell", "9788324677610, 8324677615", "Helion", "2013-12-09", "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
				89.00, LocalDate.of(2018, 9, 5), Version.PAPER);
		List<Book> booksList = List.of(book);
		List<BookDto> booksDtoList = List.of(bookDto);
		
		when(bookService.findBooksByUser(userId)).thenReturn(booksList);
		when(bookMapper.mapToBookDtoList(booksList)).thenReturn(booksDtoList);
		
		mockMvc.perform(MockMvcRequestBuilders
					.get("/v1/users/{userId}/books", userId)
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(200))
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].title", is("Java. Podstawy. Wydanie IX")))
			.andExpect(jsonPath("$[0].pages", is(864)))
			.andExpect(jsonPath("$[0].isbn", is("9788324677610, 8324677615")));
	}
	
	@Test
	public void shouldGetBookById() throws Exception {
		Long bookId = 1L;
		Book book = new Book(bookId, "Java. Podstawy. Wydanie IX","Cay S. Horstmann,Gary Cornell", "9788324677610, 8324677615", "Helion", "2013-12-09", "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
				89.00, LocalDate.of(2018, 9, 5), Version.PAPER);
		BookDto bookDto = new BookDto(1L, "Java. Podstawy. Wydanie IX","Cay S. Horstmann,Gary Cornell", "9788324677610, 8324677615", "Helion", "2013-12-09", "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
				89.00, LocalDate.of(2018, 9, 5), Version.PAPER);
		
		when(bookService.findBookById(bookId)).thenReturn(book);
		when(bookMapper.mapToBookDto(book)).thenReturn(bookDto);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/v1/users/1/books/{bookId}", bookId)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().is(200))
		.andExpect(jsonPath("$.title", is("Java. Podstawy. Wydanie IX")))
		.andExpect(jsonPath("$.authors", is("Cay S. Horstmann,Gary Cornell")))
		.andExpect(jsonPath("$.isbn", is("9788324677610, 8324677615")))
		.andExpect(jsonPath("$.publisher", is("Helion")))
		.andExpect(jsonPath("$.publishingDate", is("2013-12-09")))
		.andExpect(jsonPath("$.language", is("pl")))
		.andExpect(jsonPath("$.pages", is(864)));
	}
	
	@Test
	public void sholudAddBook() throws Exception {
		Long bookId = 1L;
		Long userId = 1L;
		User user = new User(1L, "Tomasz", "Malinowski", "tomasz.malinowski@gmail.com", "tommal", "tom_mal_password");
		Book book = new Book(bookId, "Java. Podstawy. Wydanie IX","Cay S. Horstmann,Gary Cornell", "9788324677610, 8324677615", "Helion", "2013-12-09", "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
				89.00, LocalDate.of(2018, 9, 5), Version.PAPER);
		BookDto bookDto = new BookDto(bookId, "Java. Podstawy. Wydanie IX","Cay S. Horstmann,Gary Cornell", "9788324677610, 8324677615", "Helion", "2013-12-09", "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
				89.00, LocalDate.of(2018, 9, 5), Version.PAPER);
		
		when(bookMapper.mapToBook(bookDto)).thenReturn(book);
		when(bookService.saveBookForUser(userId, book)).thenReturn(book);
		when(bookMapper.mapToBookDto(book)).thenReturn(bookDto);
		
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
						.create();
		String jsonBookDto = gson.toJson(bookDto);
		
		mockMvc.perform(MockMvcRequestBuilders
						.post("/v1/users/{userId}/books", userId)
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(jsonBookDto))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.title", is("Java. Podstawy. Wydanie IX")))
				.andExpect(jsonPath("$.authors", is("Cay S. Horstmann,Gary Cornell")))
				.andExpect(jsonPath("$.isbn", is("9788324677610, 8324677615")))
				.andExpect(jsonPath("$.publisher", is("Helion")))
				.andExpect(jsonPath("$.publishingDate", is("2013-12-09")))
				.andExpect(jsonPath("$.language", is("pl")))
				.andExpect(jsonPath("$.pages", is(864)));
	}
	
	@Test
	public void shouldUpdateBook() throws Exception {
		Long bookId = 1L;
		Book book = new Book(bookId, "Java. Podstawy. Wydanie IX", "Cay S. Horstmann,Gary Cornell", "9788324677610, 8324677615", "Helion", "2013-12-09", "pl", 864, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
				89.00, LocalDate.of(2018, 9, 5), Version.PAPER);
		Book bookUpdated = new Book(bookId, "Java. Podstawy. Wydanie IX", "Cay S. Horstmann,Gary Cornell", "9788324677789, 8324677455", "Helion", "2017-12-09", "pl", 900, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
				89.00, LocalDate.of(2018, 9, 5), Version.PAPER);
		BookDto bookUpdatedDto = new BookDto(bookId, "Java. Podstawy. Wydanie IX", "Cay S. Horstmann,Gary Cornell", "9788324677789, 8324677455", "Helion", "2017-12-09", "pl", 900, "Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...", 
				  "http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api",
				89.00, LocalDate.of(2018, 9, 5), Version.PAPER);
		
		when(bookService.updateBook(1L, book)).thenReturn(bookUpdated);
	
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
				.create();
		String jsonBookDto = gson.toJson(bookUpdatedDto);
		
		mockMvc.perform(MockMvcRequestBuilders
						.put("/v1/users/1/books/{bookId}", bookId)
						.contentType(MediaType.APPLICATION_JSON)
						.characterEncoding("UTF-8")
						.content(jsonBookDto))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.title", is("Java. Podstawy. Wydanie IX")))
				.andExpect(jsonPath("$.isbn", is("9788324677789, 8324677455")))
				.andExpect(jsonPath("$.publishingDate", is("2017-12-09")))
				.andExpect(jsonPath("$.pages", is(900)));
	}
	
	@Test
	public void sholudDeleteBook() throws Exception {
		Long bookId = 1L;
		doNothing().when(bookService).deleteBook(bookId);
		
		mockMvc.perform(MockMvcRequestBuilders
						.delete("/v1/users/1/books/{bookId}", bookId)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(200));
	}
}
