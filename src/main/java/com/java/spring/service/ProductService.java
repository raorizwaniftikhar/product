package com.java.spring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.java.spring.config.QueryFilter;
import com.java.spring.domain.Product;

/**
 * Service Interface for managing {@link Product}.
 */
public interface ProductService {

	Product save(Product product);

	Page<Product> findAll(Pageable pageable);

	Optional<Product> findOne(String id);

	void delete(String id);

	List<Product> findProductByFiltering(QueryFilter queryFilter);

}
