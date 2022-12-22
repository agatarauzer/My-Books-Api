package com.agatarauzer.myBooks.unit.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.agatarauzer.myBooks.purchase.Purchase;
import com.agatarauzer.myBooks.purchase.PurchaseDto;
import com.agatarauzer.myBooks.purchase.PurchaseMapper;

@SpringBootTest
public class PurchaseMapperTest {
	
	@Autowired
	PurchaseMapper purchaseMapper;
	
	private Purchase purchase;
	private PurchaseDto purchaseDto;
	
	@BeforeEach
	public void prepareTestData() {
		purchase = Purchase.builder()
				.id(1L)
				.price(49.99)
				.purchaseDate(LocalDate.of(2022, 6, 21))
				.boughtFrom("taniaksiazka.pl")
				.build();
		purchaseDto = PurchaseDto.builder()
				.id(1L)
				.price(49.99)
				.purchaseDate(LocalDate.of(2022, 6, 21))
				.boughtFrom("taniaksiazka.pl")
				.build();
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
