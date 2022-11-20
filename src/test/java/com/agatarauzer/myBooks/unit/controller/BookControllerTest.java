package com.agatarauzer.myBooks.unit.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

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

import com.agatarauzer.myBooks.controller.BookController;
import com.agatarauzer.myBooks.domain.Book;
import com.agatarauzer.myBooks.domain.enums.Version;
import com.agatarauzer.myBooks.dto.BookDto;
import com.agatarauzer.myBooks.mapper.BookMapper;
import com.agatarauzer.myBooks.service.BookService;
import com.agatarauzer.myBooks.service.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SpringJUnitWebConfig
@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private BookMapper bookMapper;
	
	@MockBean
	private BookService bookService;
	
	@MockBean
	private UserService userService;
	
	private Long userId;
	private Long bookId;
	private Book book;
	private BookDto bookDto;
	
	@BeforeEach
	private void prepareTestData() {
		userId = 1L; 
		bookId = 1L;
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
			.copies(1)
			.build();
		bookDto = BookDto.builder()
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
			.copies(1)
			.build();
	}
	
	//not working, gives error: expected: a collection with size <1>, but was <0>
	
	@Test 
	public void shouldGetUserBooks() throws Exception {
		List<Book> booksList = List.of(book);
		List<BookDto> booksDtoList = List.of(bookDto);
		
		when(bookService.findBooksByUser(userId, 0, 5, "id", "asc")).thenReturn(booksList);
		when(bookMapper.mapToBookDtoList(booksList)).thenReturn(booksDtoList);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/v1/users/{userId}/books", userId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(200))
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].title", is("Java. Podstawy. Wydanie IX")))
			.andExpect(jsonPath("$[0].authors", is("Cay S. Horstmann,Gary Cornell")))
			.andExpect(jsonPath("$[0].isbn", is("8324677615, 9788324677610")))
			.andExpect(jsonPath("$[0].publisher", is("Helion")))
			.andExpect(jsonPath("$[0].publishingDate", is("2013-12-09")))
			.andExpect(jsonPath("$[0].language", is("pl")))
			.andExpect(jsonPath("$[0].pages", is(864)))
			.andExpect(jsonPath("$[0].description", is("Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...")))
			.andExpect(jsonPath("$[0].imageLink", is("http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api")))
			.andExpect(jsonPath("$[0].version", is("BOOK")))
			.andExpect(jsonPath("$[0].copies", is(1)));
	}
	
	@Test
	public void shouldGetBookById() throws Exception {
		when(bookService.findBookById(bookId)).thenReturn(book);
		when(bookMapper.mapToBookDto(book)).thenReturn(bookDto);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/v1/users/1/books/{bookId}", bookId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(200))
			.andExpect(jsonPath("$.title", is("Java. Podstawy. Wydanie IX")))
			.andExpect(jsonPath("$.authors", is("Cay S. Horstmann,Gary Cornell")))
			.andExpect(jsonPath("$.isbn", is("8324677615, 9788324677610")))
			.andExpect(jsonPath("$.publisher", is("Helion")))
			.andExpect(jsonPath("$.publishingDate", is("2013-12-09")))
			.andExpect(jsonPath("$.language", is("pl")))
			.andExpect(jsonPath("$.pages", is(864)))
			.andExpect(jsonPath("$.description", is("Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...")))
			.andExpect(jsonPath("$.imageLink", is("http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api")))
			.andExpect(jsonPath("$.version", is("BOOK")))
			.andExpect(jsonPath("$.copies", is(1)));
	}
	
	@Test
	public void sholudAddBook() throws Exception {
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
			.andExpect(status().is(201))
			.andExpect(jsonPath("$.title", is("Java. Podstawy. Wydanie IX")))
			.andExpect(jsonPath("$.authors", is("Cay S. Horstmann,Gary Cornell")))
			.andExpect(jsonPath("$.isbn", is("8324677615, 9788324677610")))
			.andExpect(jsonPath("$.publisher", is("Helion")))
			.andExpect(jsonPath("$.publishingDate", is("2013-12-09")))
			.andExpect(jsonPath("$.language", is("pl")))
			.andExpect(jsonPath("$.pages", is(864)))
			.andExpect(jsonPath("$.description", is("Kolejne wydanie tej cenionej książki zostało zaktualizowane o wszystkie nowości...")))
			.andExpect(jsonPath("$.imageLink", is("http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api")))
			.andExpect(jsonPath("$.version", is("BOOK")))
			.andExpect(jsonPath("$.copies", is(1)));
	}
	
	@Test
	public void shouldUpdateBook() throws Exception {
		Book bookUpdated = Book.builder()
				.id(bookId)
				.title("Java. Podstawy. Wydanie IX_changed")
				.authors("Cay S. Horstmann,Gary Cornell_changed")
				.isbn("8324677615, 0000000000000")
				.publisher("Helion_changed")
				.publishingDate("2022-12-22")
				.language("pl_changed")
				.pages(900)
				.description("Kolejne wydanie_changed")
				.imageLink("http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api_changed")
				.version(Version.E_BOOK)
				.copies(2)
				.build();
			
		when(bookService.updateBook(1L, bookUpdated)).thenReturn(bookUpdated);

		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
				.create();
		String jsonBook = gson.toJson(bookUpdated);
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/v1/users/{userId}/books/{bookId}", userId, bookId)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonBook))
			.andExpect(status().is(200))
			.andExpect(jsonPath("$.title", is("Java. Podstawy. Wydanie IX_changed")))
			.andExpect(jsonPath("$.authors", is("Cay S. Horstmann,Gary Cornell_changed")))
			.andExpect(jsonPath("$.isbn", is("8324677615, 0000000000000")))
			.andExpect(jsonPath("$.publisher", is("Helion_changed")))
			.andExpect(jsonPath("$.publishingDate", is("2022-12-22")))
			.andExpect(jsonPath("$.language", is("pl_changed")))
			.andExpect(jsonPath("$.pages", is(900)))
			.andExpect(jsonPath("$.description", is("Kolejne wydanie_changed")))
			.andExpect(jsonPath("$.imageLink", is("http://books.google.com/books/content?id=UEdjAgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api_changed")))
			.andExpect(jsonPath("$.version", is("E_BOOK")))
			.andExpect(jsonPath("$.copies", is(2)));
	}
	
	@Test
	public void sholudDeleteBook() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/v1/users/1/books/{bookId}", bookId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(200));
		
		verify(bookService, times(1)).deleteBook(bookId);
	}
}
