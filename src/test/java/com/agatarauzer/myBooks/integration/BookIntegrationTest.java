/*package com.agatarauzer.myBooks.integration;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.agatarauzer.myBooks.service.BookService;

@SpringBootTest
public class BookIntegrationTest {

	private final BookService bookService;
	private final JdbcTemplate jdbcTemplate;
	private final DataSource dataSource;
	
	@Autowired
	public BookIntegrationTest(BookService bookService, JdbcTemplate jdbcTemplate, DataSource dataSource) {
		this.bookService = bookService;
		this.jdbcTemplate = jdbcTemplate;
		this.dataSource = dataSource;
	}
	
	@BeforeEach
	public void prepareDb() {
		cleanDb();
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false,
			"UTF-8", new ClassPathResource("insert-data.sql"));
		resourceDatabasePopulator.execute(dataSource);
	}
	
	public void cleanDb() {
		ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator(false, false,
			"UTF-8", new ClassPathResource("clear-database.sql"));
		resourceDatabasePopulator.execute(dataSource);
	}
	
	@Test
	public void test() {
		
	}
	
}

*/