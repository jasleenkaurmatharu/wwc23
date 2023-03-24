package com.example.restservice;

import com.example.restservice.entity.Product;
import com.example.restservice.payload.ProductModel;
import com.example.restservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private final ProductService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void createProduct(@RequestBody ProductModel productModel) {
		service.save(productModel);
	}

	@GetMapping("/{category}")
	public ResponseEntity<List<Product>> getProduct(@PathVariable("category")String category) {
		return new ResponseEntity<>(service.findByCategory(category), HttpStatus.OK);
	}

	@GetMapping("/getID/{userId}")
	public ResponseEntity<Optional<Product>> getProduct(@PathVariable("userId")long userId) {
		return new ResponseEntity<>(service.findByUserId(userId), HttpStatus.OK);
	}

//	@PutMapping("/update/{userId}")
//	@ResponseStatus(HttpStatus.OK)
//	public void updateOneProduct(@PathVariable("userId")long userId, @RequestBody ProductModel productModel) {
//		service.updateOneProduct(userId, productModel);
//	}

//	@PutMapping("/update/{userId}")
//	@ResponseStatus(HttpStatus.OK)
//	public void updatePrice(@PathVariable("userId")long userId, @RequestBody long price) {
//		service.updateOneProduct(userId, price);
//	}

	@DeleteMapping("/delete/{userId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteOneProduct(@PathVariable("userId")long userId) {
		service.deleteProduct(userId);
	}



}
