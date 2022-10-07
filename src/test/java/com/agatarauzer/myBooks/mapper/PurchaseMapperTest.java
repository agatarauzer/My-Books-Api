package com.agatarauzer.myBooks.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.agatarauzer.myBooks.domain.Purchase;
import com.agatarauzer.myBooks.dto.PurchaseDto;

@SpringBootTest
public class PurchaseMapperTest {
	
	@Autowired
	PurchaseMapper purchaseMapper;
	
	private Purchase purchase;
	private PurchaseDto purchaseDto;
	
	@BeforeEach
	public void prepareTestData() {
		purchase = new Purchase(1L, 49.99, LocalDate.of(2022, 6, 21), "taniaksiazka.pl");
		purchaseDto = new PurchaseDto(1L, 49.99, LocalDate.of(2022, 6, 21), "taniaksiazka.pl");
	}
	
	@Test
	public void shouldMapToPurchaseDto() {
		PurchaseDto purchaseDtoMapped = purchaseMapper.mapToPurchaseDto(purchase);
		
		assertEquals(purchase.getId(), purchaseDtoMapped.getId());
		assertEquals(purchase.getPrice(), purchaseDtoMapped.getPrice());
		assertEquals(purchase.getPurchaseDate(), purchaseDtoMapped.getPurchaseDate());
		assertEquals(purchase.getBoughtFrom(), purchaseDtoMapped.getBoughtFrom());
	}
	
	@Test
	public void shouldMapToPurchase() {
		Purchase purchaseMapped = purchaseMapper.mapToPurchase(purchaseDto);
		
		assertEquals(purchaseDto.getId(), purchaseMapped.getId());
		assertEquals(purchaseDto.getPrice(), purchaseMapped.getPrice());
		assertEquals(purchaseDto.getPurchaseDate(), purchaseMapped.getPurchaseDate());
		assertEquals(purchaseDto.getBoughtFrom(), purchaseMapped.getBoughtFrom());
	}
}
