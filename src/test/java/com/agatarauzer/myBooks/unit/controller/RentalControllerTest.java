package com.agatarauzer.myBooks.unit.controller;

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

import com.agatarauzer.myBooks.book.BookService;
import com.agatarauzer.myBooks.book.domain.Book;
import com.agatarauzer.myBooks.book.domain.Version;
import com.agatarauzer.myBooks.rental.RentalController;
import com.agatarauzer.myBooks.rental.RentalDto;
import com.agatarauzer.myBooks.rental.RentalMapper;
import com.agatarauzer.myBooks.rental.RentalService;
import com.agatarauzer.myBooks.rental.domain.Rental;
import com.agatarauzer.myBooks.rental.domain.RentalStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SpringJUnitWebConfig
@WebMvcTest(RentalController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RentalControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private RentalMapper rentalMapper;
	
	@MockBean
	private RentalService rentalService;
	
	@MockBean
	private BookService bookService;
	
	private Long rentalId;
	private Rental rental; 
	private RentalDto  rentalDto;
	private Long bookId;
	private Book book;
	
	@BeforeEach
	public void prepareTestData() {
		bookId = 1L;
		rentalId = 1L;
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
		rental =Rental.builder()
				.id(1L)
				.status(RentalStatus.BORROWED_FROM)
				.name("from Kate")
				.startDate(LocalDate.of(2022, 6, 21))
				.endDate(LocalDate.of(2023, 1, 5))
				.notes("Kate will need it in January!")
				.book(book)
				.build();
		rentalDto = RentalDto.builder()
				.id(1L)
				.status(RentalStatus.BORROWED_FROM)
				.name("from Kate")
				.startDate(LocalDate.of(2022, 6, 21))
				.endDate(LocalDate.of(2023, 1, 5))
				.notes("Kate will need it in January!")
				.build();
	}
	
	@Test
	public void shouldGetRentalsForBook() throws Exception {
		when(rentalService.getRentalsForBook(bookId)).thenReturn(List.of(rental));
		when(rentalMapper.mapToRentalDtoList(List.of(rental))).thenReturn(List.of(rentalDto));
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/v1/users/1/books/{bookId}/rentals", bookId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(200))
			.andExpect(jsonPath("$[0].status", is("BORROWED_FROM")))
			.andExpect(jsonPath("$[0].name", is("from Kate")))
			.andExpect(jsonPath("$[0].startDate", is("2022-06-21")))
			.andExpect(jsonPath("$[0].endDate", is("2023-01-05")))
			.andExpect(jsonPath("$[0].notes", is("Kate will need it in January!")));	
	}
	
	@Test 
	public void shouldAddRental() throws Exception {
		when(rentalService.saveRentalForBook(bookId, rental)).thenReturn(rental);
		when(rentalMapper.mapToRental(rentalDto)).thenReturn(rental);
		
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
				.create();
		String jsonRentalDto = gson.toJson(rentalDto);
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/v1/users/1/books/{bookId}/rentals", bookId)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonRentalDto))
			.andExpect(status().is(201))
			.andExpect(jsonPath("$.status", is("BORROWED_FROM")))
			.andExpect(jsonPath("$.name", is("from Kate")))
			.andExpect(jsonPath("$.startDate", is("2022-06-21")))
			.andExpect(jsonPath("$.endDate", is("2023-01-05")))
			.andExpect(jsonPath("$.notes", is("Kate will need it in January!")));	
	}
	
	@Test
	public void shouldUpdateRental() throws Exception {
		Rental rentalUpdated = Rental.builder()
				.id(rentalId)
				.status(RentalStatus.LENDED_TO)
				.name("to Kate")
				.startDate(LocalDate.of(2020, 5, 9))
				.endDate(LocalDate.of(2021, 8, 9))
				.notes("Not needed now")
				.build();
		when(rentalService.updateRental(rentalUpdated)).thenReturn(rentalUpdated);
		
		Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
			.create();
		String jsonRentalUpdated = gson.toJson(rentalUpdated);
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/v1/users/1/books/{bookId}/rentals/{rentalId}", bookId, rentalId)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonRentalUpdated))
			.andExpect(status().is(200))
			.andExpect(jsonPath("$.status", is("LENDED_TO")))
			.andExpect(jsonPath("$.name", is("to Kate")))
			.andExpect(jsonPath("$.startDate", is("2020-05-09")))
			.andExpect(jsonPath("$.endDate", is("2021-08-09")))
			.andExpect(jsonPath("$.notes", is("Not needed now")));
	}
	
	@Test
	public void shouldDeleteRental() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/v1/users/1/books/{bookId}/rentals/{rentalId}", bookId, rentalId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(200));
		
		verify(rentalService, times(1)).deleteRental(rentalId);
	}	
}
