package com.example.restservice.repository;

import com.example.restservice.entity.Product;
import com.example.restservice.entity.ProductCategory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class JdbcProductRepositoryIntTest {

	@Autowired
	@Qualifier("serviceNamedParameterJdbcTemplate")
	NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	ProductRepository productRepository;

	@BeforeEach
	void setUp() {
		jdbcTemplate.update("INSERT INTO product (userId, category, description, price)" + "VALUES ('1', 'BOOKS', 'Lord of the flies', '15');", new MapSqlParameterSource());
	}

	@AfterEach
	void tearDown() {
		jdbcTemplate.getJdbcTemplate().update("TRUNCATE TABLE product");
	}

	@Test
	@DisplayName("gets product from db by Category")
	void findProductByCategory() {

		//when
		List<Product> product = productRepository.findByCategory(ProductCategory.BOOKS);

		//then
		assertEquals("BOOKS", product.get(0).category().toString());

	}

	@Test
	@DisplayName("gets product from db by userId")
	void findProductByUserId() {

		//given
		Product product = Product.builder().userId(2L).category(ProductCategory.BOOKS).description("Lord of the flies").price(15L).build();

		//when
		productRepository.save(product);
		Optional<Product> productSave = productRepository.findByUserId(2L);

		//then
		assertEquals(product.userId(), productSave.get().userId());
	}

	@Test
	@DisplayName("delete product")
	void deleteProduct() {

		//given
		Product.builder().userId(2L).category(ProductCategory.CLOTHES).description("Jumper").price(23L).build();

		//when
		productRepository.deleteByUserId(2L);

		//then
		assertTrue(productRepository.findByUserId(2L).isEmpty());
	}


//	@Test
//	@DisplayName("update product")
//	void updateProduct() {
//
//		//given
//		//Product product = Product.builder().userId(1L).category(ProductCategory.BOOKS).price(23L).build();
//
//		//when
//		productRepository.updateOne(1L, 23L);
//
//		//then
//		assertEquals(23L, productRepository.findByUserId(1L).get().price());
//
//	}


}
