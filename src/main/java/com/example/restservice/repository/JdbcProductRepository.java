package com.example.restservice.repository;

import com.example.restservice.entity.Product;
import com.example.restservice.entity.ProductCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcProductRepository implements ProductRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	private static final String SAVE_SQL = """
            INSERT INTO product(
            userId,
            category, 
            description, 
            price)
            VALUES (
            :userId,
            :category,
            :description,
            :price)
			""";

	private static final String UPDATE_SQL = """
            UPDATE product
            SET userId = :userId, 
            category = :category,
            description = :description,
            price = :price
            WHERE id = :id
			""";

	private static final String FIND_BY_CATEGORY_SQL = """
		SELECT * FROM product WHERE category = :category ORDER BY id""";

	private static final String FIND_BY_USER_ID_SQL = """
        SELECT * FROM product WHERE userId = :userId
			""";

	private static final String DELETE_BY_USER_ID_SQL = "DELETE FROM product WHERE userId = :userId";

	@Override
	public void save(Product product) {
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("category", product.category().name())
				.addValue("userId", product.userId())
				.addValue("description", product.description())
				.addValue("price", product.price());
		jdbcTemplate.update(SAVE_SQL, params);
	}

	@Override
	public List<Product> findByCategory(ProductCategory category) {
		SqlParameterSource params =
				new MapSqlParameterSource().addValue("category", category.name());
		return jdbcTemplate.query(FIND_BY_CATEGORY_SQL, params, new ProductRowMapper());

	}

	@Override
	public Optional<Product> findByUserId(Long userId) {
		SqlParameterSource params = new MapSqlParameterSource().addValue("userId", userId);
		return jdbcTemplate.query(FIND_BY_USER_ID_SQL, params, new ProductRowMapper()).stream().findFirst();
	}

//	@Override
//	public void updateOne(Product product) {
//		SqlParameterSource params = new MapSqlParameterSource()
//				.addValue("id", product.id())
//				.addValue("userId", product.userId())
//				.addValue("category", product.category().name())
//				.addValue("description", product.description())
//				.addValue("price", product.price());
//		jdbcTemplate.update(UPDATE_SQL, params);
//
//	}

	@Override
	public void deleteByUserId(Long userId) {
		SqlParameterSource namedParameters =
				new MapSqlParameterSource().addValue("userId", userId);
		jdbcTemplate.update(DELETE_BY_USER_ID_SQL, namedParameters);
	}

}
