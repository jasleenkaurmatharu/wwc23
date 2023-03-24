package com.example.restservice.payload;

import com.example.restservice.entity.ProductCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@Builder(toBuilder = true)
public class ProductModel {

	private ProductCategory category;
	private Long userId;
	private String description;
	private Long price;

}
