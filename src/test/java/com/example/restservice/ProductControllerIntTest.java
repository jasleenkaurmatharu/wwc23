package com.example.restservice;

import com.example.restservice.entity.ProductCategory;
import com.example.restservice.payload.ProductModel;
import com.example.restservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tomakehurst.wiremock.client.WireMock;
import lombok.AllArgsConstructor;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ProductController.class)
@AllArgsConstructor
class ProductControllerIntTest {


	@Autowired
	protected WebTestClient webTestClient;
	@Autowired
	MockMvc mockMvc;
	ProductService productService;
	private ObjectWriter ow;

	@Autowired
	public ProductControllerIntTest(ObjectMapper objectMapper) {
		this.ow = objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).writer();
	}

	@Test
	void testMockRequest_Get2XX() throws Exception {

		ProductModel product = ProductModel.builder().category(ProductCategory.BOOKS).userId(123L).description("Harry Potter").price(12L).build();
		productService.save(product);
		String category = ProductCategory.BOOKS.name();

		stubFor(WireMock.get(urlPathMatching("/products/" + category))
				.willReturn(aResponse()
						.withBody(ow.writeValueAsString(product))
						.withStatus(HttpStatus.OK_200)));

	}


}
