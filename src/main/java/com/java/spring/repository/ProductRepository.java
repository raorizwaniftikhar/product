package com.java.spring.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.java.spring.domain.Product;

/**
 * Spring Data MongoDB repository for the Product entity.
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

	/* By Name Query */
	List<Product> findByNameEqualsIgnoreCase(String name);

	List<Product> findByNameStartingWith(String name);

	List<Product> findByNameEndingWith(String name);

	List<Product> findByNameContainingIgnoreCase(String name);

//	/* By Slug Query */
	List<Product> findBySlugEqualsIgnoreCase(String slug);

	List<Product> findBySlugStartingWith(String slug);

	List<Product> findBySlugEndingWith(String slug);

	List<Product> findBySlugContainingIgnoreCase(String slug);

//	/* By status Query */
	List<Product> findByStatusEqualsIgnoreCase(String status);

	List<Product> findByStatusContainingIgnoreCase(String status);

	List<Product> findByStatusStartingWith(String status);

	List<Product> findByStatusEndingWith(String status);

	List<Product> findBySkuContainingIgnoreCase(String sku);

	List<Product> findBySkuStartingWith(String sku);

	List<Product> findBySkuEndingWith(String sku);

	List<Product> findBySkuEqualsIgnoreCase(String sku);

	List<Product> findByDescriptionContainingIgnoreCase(String description);

	List<Product> findByDescriptionStartingWith(String description);

	List<Product> findByDescriptionEndingWith(String description);

	@Query("{'commodity_type' : physical }")
	List<Product> findByCommodityType(String commodity_type);

	@Query("{manage_stock : true }")
	List<Product> findByManageStockEqualsTrue();

	@Query("{manage_stock : false }")
	List<Product> findByManageStockEqualsFalse();

}
