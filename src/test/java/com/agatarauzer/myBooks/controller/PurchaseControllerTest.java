package com.agatarauzer.myBooks.controller;

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

import com.agatarauzer.myBooks.domain.Purchase;
import com.agatarauzer.myBooks.dto.PurchaseDto;
import com.agatarauzer.myBooks.mapper.PurchaseMapper;
import com.agatarauzer.myBooks.service.BookService;
import com.agatarauzer.myBooks.service.PurchaseService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SpringJUnitWebConfig
@WebMvcTest(PurchaseController.class)
@AutoConfigureMockMvc(addFilters = false)
public class PurchaseControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private PurchaseMapper purchaseMapper;
	
	@MockBean
	private PurchaseService purchaseService;
	
	@MockBean
	private BookService bookService;
	
	private Long purchaseId;
	private Purchase purchase; 
	private PurchaseDto  purchaseDto;
	private Long bookId;
	
	@BeforeEach
	public void prepareTestData() {
		bookId = 1L;
		purchaseId = 1L;
		purchase = Purchase.builder()
				.id(purchaseId)
				.price(48.90)
				.purchaseDate(LocalDate.of(2022, 7, 12))
				.boughtFrom("empik.com")
				.build();
		purchaseDto = PurchaseDto.builder()
				.id(purchaseId)
				.price(48.90)
				.purchaseDate(LocalDate.of(2022, 7, 12))
				.boughtFrom("empik.com")
				.build();
	}
	
	@Test
	public void shouldGetRentalForBook() throws Exception {
		when(purchaseService.getPurchaseForBook(bookId)).thenReturn(purchase);
		when(purchaseMapper.mapToPurchaseDto(purchase)).thenReturn(purchaseDto);
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/v1/users/1/books/{bookId}/purchases", bookId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(200))
			.andExpect(jsonPath("$.price", is(48.90)))
			.andExpect(jsonPath("$.purchaseDate", is("2022-07-12")))
			.andExpect(jsonPath("$.boughtFrom", is("empik.com")));	
	}
	
	@Test 
	public void shouldAddPurchase() throws Exception {
		when(purchaseService.savePurchase(bookId, purchase)).thenReturn(purchase);
		when(purchaseMapper.mapToPurchase(purchaseDto)).thenReturn(purchase);
		
		Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
			.create();
		String jsonPurchaseDto = gson.toJson(purchaseDto);
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/v1/users/1/books/{bookId}/purchases", bookId)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonPurchaseDto))
			.andExpect(status().is(200))
			.andExpect(jsonPath("$.price", is(48.90)))
			.andExpect(jsonPath("$.purchaseDate", is("2022-07-12")))
			.andExpect(jsonPath("$.boughtFrom", is("empik.com")));	
	}
	
	@Test
	public void shouldUpdatePurchase() throws Exception {
		Purchase purchaseUpdated = Purchase.builder()
				.id(purchaseId)
				.price(52.90)
				.purchaseDate(LocalDate.of(2022, 8, 15))
				.boughtFrom("www.empik.com")
				.build();
		when(purchaseService.updatePurchase(purchaseId, purchaseUpdated)).thenReturn(purchaseUpdated);
		
		Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
			.create();
		String jsonPurchaseUpdated = gson.toJson(purchaseUpdated);
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/v1/users/1/books/{bookId}/purchases/{purchaseId}", bookId, purchaseId)
				.contentType(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8")
				.content(jsonPurchaseUpdated))
			.andExpect(status().is(200))
			.andExpect(jsonPath("$.price", is(52.90)))
			.andExpect(jsonPath("$.purchaseDate", is("2022-08-15")))
			.andExpect(jsonPath("$.boughtFrom", is("www.empik.com")));	
	}
	
	@Test
	public void shouldDeletePurchase() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/v1/users/1/books/{bookId}/purchases/{purchaseId}", bookId, purchaseId)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().is(200));
		
		verify(purchaseService, times(1)).deletePurchase(bookId, purchaseId);
	}	
}
