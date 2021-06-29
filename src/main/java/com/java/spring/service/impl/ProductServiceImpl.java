package com.java.spring.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.java.spring.config.QueryFilter;
import com.java.spring.domain.Product;
import com.java.spring.repository.ProductRepository;
import com.java.spring.service.ProductService;

/**
 * Service Implementation for managing {@link Product}.
 */
@Service
public class ProductServiceImpl implements ProductService {

	private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

	private final ProductRepository productRepository;

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public Product save(Product product) {
		log.debug("Request to save Product : {}", product);
		if (product.getId() != null) {
			Optional<Product> updateProduct = findOne(product.getId());
			if (product.getName().isBlank()) {
				product.setName(updateProduct.get().getName());
			}
			if (product.getSlug().isBlank()) {
				product.setSlug(updateProduct.get().getSlug());
			}
			if (product.getSku().isBlank()) {
				product.setSku(updateProduct.get().getSku());
			}
			if (product.isManage_stock() != updateProduct.get().isManage_stock()) {
				product.setManage_stock(updateProduct.get().isManage_stock());
			}
			if (product.getDescription().isBlank()) {
				product.setDescription(updateProduct.get().getDescription());
			}
			if (product.getStatus().isBlank()) {
				product.setStatus(updateProduct.get().getStatus());
			}
			if (product.getCommodity_type().isBlank()) {
				product.setCommodity_type(updateProduct.get().getCommodity_type());
			}
			if (product.getPrice() == null) {
				product.setPrice(updateProduct.get().getPrice());
			}
		}
		return productRepository.save(product);
	}

	@Override
	public Page<Product> findAll(Pageable pageable) {
		log.debug("Request to get all product");
		return productRepository.findAll(pageable);
	}

	@Override
	public Optional<Product> findOne(String id) {
		log.debug("Request to get Product : {}", id);
		return productRepository.findById(id);
	}

	@Override
	public void delete(String id) {
		log.debug("Request to delete Product : {}", id);
		productRepository.deleteById(id);
	}

	@Override
	public List<Product> findProductByFiltering(QueryFilter queryFilter) {
		System.out.println(queryFilter.toString());
		log.debug("Request to get Product : {} , {}, {}", queryFilter.getFiledName(), queryFilter.getValue(),
				queryFilter.getQueryOprator());
		List<Product> products = null;
		if (queryFilter.getQueryOprator().equalsIgnoreCase("eq")) {
			if (queryFilter.getFiledName().equalsIgnoreCase("name")) {
				products = productRepository.findByNameEqualsIgnoreCase(queryFilter.getValue());
			} else if (queryFilter.getFiledName().equalsIgnoreCase("slug")) {
				products = productRepository.findBySlugEqualsIgnoreCase(queryFilter.getValue());
			} else if (queryFilter.getFiledName().equalsIgnoreCase("status")) {
				products = productRepository.findByStatusEqualsIgnoreCase(queryFilter.getValue());
			} else if (queryFilter.getFiledName().equalsIgnoreCase("sku")) {
				products = productRepository.findBySkuEqualsIgnoreCase(queryFilter.getValue());
			} else if (queryFilter.getFiledName().equalsIgnoreCase("commodity_type")) {
				products = productRepository.findByCommodityType(queryFilter.getValue());
			} else if (queryFilter.getFiledName().equalsIgnoreCase("manage_stock")) {
				if (Boolean.parseBoolean(queryFilter.getValue()) == true) {
					products = productRepository.findByManageStockEqualsTrue();
				} else if (Boolean.parseBoolean(queryFilter.getValue()) == false) {
					products = productRepository.findByManageStockEqualsFalse();
				}
			}

		} else if (queryFilter.getQueryOprator().equalsIgnoreCase("like")) {
			String value = queryFilter.getValue().replaceAll("\\*", "");
			if (queryFilter.getFiledName().equalsIgnoreCase("name")) {
				if (queryFilter.getValue().startsWith("*") && queryFilter.getValue().endsWith("*")) {
					products = productRepository.findByNameContainingIgnoreCase(value);
				} else if (queryFilter.getValue().endsWith("*")) {
					products = productRepository.findByNameStartingWith(value);
				} else if (queryFilter.getValue().startsWith("*")) {
					products = productRepository.findByNameEndingWith(value);
				}
			} else if (queryFilter.getFiledName().equalsIgnoreCase("slug")) {
				if (queryFilter.getValue().startsWith("*") && queryFilter.getValue().endsWith("*")) {
					products = productRepository.findBySlugContainingIgnoreCase(value);
				} else if (queryFilter.getValue().endsWith("*")) {
					products = productRepository.findBySlugStartingWith(value);
				} else if (queryFilter.getValue().startsWith("*")) {
					products = productRepository.findBySlugEndingWith(value);
				}
			} else if (queryFilter.getFiledName().equalsIgnoreCase("status")) {
				if (queryFilter.getValue().startsWith("*") && queryFilter.getValue().endsWith("*")) {
					products = productRepository.findByStatusContainingIgnoreCase(value);
				} else if (queryFilter.getValue().endsWith("*")) {
					products = productRepository.findByStatusStartingWith(value);
				} else if (queryFilter.getValue().startsWith("*")) {
					products = productRepository.findByStatusEndingWith(value);
				}
			} else if (queryFilter.getFiledName().equalsIgnoreCase("sku")) {
				if (queryFilter.getValue().startsWith("*") && queryFilter.getValue().endsWith("*")) {
					products = productRepository.findBySkuContainingIgnoreCase(value);
				} else if (queryFilter.getValue().endsWith("*")) {
					products = productRepository.findBySkuStartingWith(value);
				} else if (queryFilter.getValue().startsWith("*")) {
					products = productRepository.findBySkuEndingWith(value);
				}
			} else if (queryFilter.getFiledName().equalsIgnoreCase("description")) {
				if (queryFilter.getValue().startsWith("*") && queryFilter.getValue().endsWith("*")) {
					products = productRepository.findByDescriptionContainingIgnoreCase(value);
				} else if (queryFilter.getValue().endsWith("*")) {
					products = productRepository.findByDescriptionStartingWith(value);
				} else if (queryFilter.getValue().startsWith("*")) {
					products = productRepository.findByDescriptionEndingWith(value);
				}
			}

		}
		return products;
	}

}
