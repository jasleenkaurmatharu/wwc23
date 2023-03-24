package com.example.restservice.repository;

import com.example.restservice.entity.Product;

import com.example.restservice.entity.ProductCategory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		return Product.builder()
				.id(rs.getLong("id"))
				.userId(rs.getLong("userId"))
				.category(ProductCategory.valueOf(rs.getString("category")))
				.description(rs.getString("description"))
				.price(rs.getLong("price"))
				.build();

	}

}
