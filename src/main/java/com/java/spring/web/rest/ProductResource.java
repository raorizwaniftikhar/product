package com.java.spring.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.java.spring.config.Constants;
import com.java.spring.config.HeaderUtil;
import com.java.spring.config.QueryFilter;
import com.java.spring.domain.BrandProductRelation;
import com.java.spring.domain.Brands;
import com.java.spring.domain.GenricData;
import com.java.spring.domain.Product;
import com.java.spring.domain.ProductMapper;
import com.java.spring.exception.BadRequestAlertException;
import com.java.spring.service.BrandProductRelationService;
import com.java.spring.service.ProductService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

/**
 * REST controller for managing {@link com.java.spring.domain.Product}.
 */
@RestController
@RequestMapping(value = "/api/", produces = MediaType.APPLICATION_JSON_VALUE)

public class ProductResource {

	private final Logger log = LoggerFactory.getLogger(ProductResource.class);

	private static final String ENTITY_NAME = "product";

	@Value("${spring.application.name}")
	private String applicationName;
	@Value("${server.api.brand.url}")
	private String brandUrl;

	private final ProductService productService;

	private final RestTemplate restTemplate;

	private final BrandProductRelationService brandProductRelationService;

	public ProductResource(ProductService productService, RestTemplate restTemplate,
			BrandProductRelationService brandProductRelationService) {
		this.productService = productService;
		this.restTemplate = restTemplate;
		this.brandProductRelationService = brandProductRelationService;
	}

	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 400, message = Constants.MESSAGE_400),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403),
			@ApiResponse(code = 201, message = Constants.MESSAGE_201, response = Product.class, responseContainer = ENTITY_NAME) })
	@PostMapping("/product")
	public ResponseEntity<Product> createBrand(@Valid @RequestBody ProductMapper productMapper)
			throws URISyntaxException, NoSuchFieldException, SecurityException, IllegalArgumentException,
			IllegalAccessException {
		log.debug("REST request to save Product : {}", productMapper);
		if (productMapper.getId() != null) {
			throw new BadRequestAlertException("A new Product cannot already have an ID", ENTITY_NAME, "id exists");
		}
		if (productMapper.getStatus() != null && !productMapper.getStatus().equalsIgnoreCase(Constants.LIVE)
				&& !productMapper.getStatus().equalsIgnoreCase(Constants.DRAFT)) {
			throw new BadRequestAlertException("A new Product status is not equal to live/draft ", ENTITY_NAME, "");
		}
		if (productMapper.getCommodity_type() != null
				&& !productMapper.getCommodity_type().equalsIgnoreCase(Constants.PHYSICAL)
				&& !productMapper.getCommodity_type().equalsIgnoreCase(Constants.DIGITAL)) {
			throw new BadRequestAlertException("A new Product commodity_type is not equal to physical/digital ",
					ENTITY_NAME, "");
		}
		if (productMapper.getType() != null && !productMapper.getType().equalsIgnoreCase(ENTITY_NAME)) {
			throw new BadRequestAlertException("A new Product  object type is not equal to product ", ENTITY_NAME, "");
		}
		Product result = productMapper.getProductObject();
		result = productService.save(result);
		GenricData meta = new GenricData(false, true,
				new Document().append("timeStamp", result.getTimestamp()).append("price", result.getPrice()));
		result.setMeta(meta.getMeta());
		return ResponseEntity.created(new URI("/api/product/" + productMapper.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
				.body(result);
	}

	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403),
			@ApiResponse(code = 400, message = Constants.MESSAGE_400),
			@ApiResponse(code = 201, message = Constants.MESSAGE_201, response = Product.class, responseContainer = ENTITY_NAME) })
	@PutMapping("/product/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable String id, @Valid @RequestBody Product product)
			throws URISyntaxException {
		log.debug("REST request to update Product : {}", product);
		if (id == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id null");
		}
		if (product.getStatus() != null && !product.getStatus().equalsIgnoreCase(Constants.LIVE)
				&& !product.getStatus().equalsIgnoreCase(Constants.DRAFT)) {
			throw new BadRequestAlertException("A updated product status is not equal to live/draft ", ENTITY_NAME, "");
		}
		if (product.getCommodity_type() != null && !product.getCommodity_type().equalsIgnoreCase(Constants.PHYSICAL)
				&& !product.getCommodity_type().equalsIgnoreCase(Constants.DIGITAL)) {
			throw new BadRequestAlertException("A new Product commodity_type is not equal to physical/digital ",
					ENTITY_NAME, "");
		}
		if (product.getType() != null && !product.getType().equalsIgnoreCase(ENTITY_NAME)) {
			throw new BadRequestAlertException("A updated product  object type is not equal to product", ENTITY_NAME,
					"");
		}
		Product result = productService.save(product);
		GenricData meta = new GenricData(false, true,
				new Document().append("timeStamp", result.getTimestamp()).append("price", result.getPrice()));
		result.setMeta(meta.getMeta());
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId()))
				.body(product);
	}

	@GetMapping("/product")
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403),
			@ApiResponse(code = 400, message = Constants.MESSAGE_400),
			@ApiResponse(code = 200, message = Constants.MESSAGE_200, response = Product.class, responseContainer = Constants.VARIABLE_LIST) })
	public ResponseEntity<?> getAllProduct(Pageable pageable)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		log.debug("REST request to get a page of Product");
		Page<Product> products = productService.findAll(pageable);

		if (products == null || products.isEmpty()) {
			return ResponseEntity.noContent()
					.headers(HeaderUtil.createAlert(applicationName, Constants.RECORD_NOT_FOUND, null)).build();
		}
		List<Product> productList = new ArrayList<>();
		for (Product product : products.getContent()) {
			GenricData meta = new GenricData(true, true,
					new Document().append("timeStamp", product.getTimestamp()).append("price", product.getPrice()));
			product.setMeta(meta.getMeta());
			productList.add(product);
		}
		Page<Product> pagination = new PageImpl<Product>(productList, products.getPageable(),
				products.getNumberOfElements());
		return ResponseEntity.ok().body(pagination);
	}

	@ApiResponses(value = { @ApiResponse(code = 204, message = Constants.NO_CONTENT, responseHeaders = {
			@ResponseHeader(name = Constants.X_BRAND_APP_ALERT, response = String.class, description = Constants.RECORD_NOT_FOUND),
			@ResponseHeader(name = Constants.X_BRAND_APP_PARAM, response = String.class, description = "BRAND ID") }),
			@ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403),
			@ApiResponse(code = 200, message = Constants.MESSAGE_200, response = Product.class, responseContainer = ENTITY_NAME) })
	@GetMapping("/product/{id}")
	public ResponseEntity<Optional<Product>> getProduct(@PathVariable(name = "id", required = true) String id) {
		log.debug("REST request to get product : {}", id);
		Optional<Product> product = productService.findOne(id);

		if (product.isEmpty()) {
			return ResponseEntity.noContent()
					.headers(HeaderUtil.createAlert(applicationName, Constants.RECORD_NOT_FOUND, id)).build();
		}
		GenricData meta = new GenricData(true, true, new Document().append("timeStamp", product.get().getTimestamp())
				.append("price", product.get().getPrice()));
		product.get().setMeta(meta.getMeta());
		return ResponseEntity.ok().body(product);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.NO_CONTENT, responseHeaders = {
			@ResponseHeader(name = Constants.X_BRAND_APP_ALERT, response = String.class, description = Constants.RECORD_DELETE),
			@ResponseHeader(name = Constants.X_BRAND_APP_PARAM, response = String.class, description = "BRAND ID") }),
			@ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403) })
	@DeleteMapping("/product/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id", required = true) String id) {
		log.debug("REST request to delete product : {}", id);
		productService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
	}

	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403),
			@ApiResponse(code = 400, message = Constants.MESSAGE_400),
			@ApiResponse(code = 200, message = Constants.MESSAGE_200, response = Product.class, responseContainer = ENTITY_NAME) })
	@GetMapping("/product/search")
	public ResponseEntity<List<Product>> filter(@RequestParam(required = true, name = "filter") String filter) {
		log.debug("REST request to get product by filter : {}", filter);
		QueryFilter queryFilter = new QueryFilter(filter);
		if (!Constants.doesObjectContainField(new Product(), queryFilter.getFiledName())) {
			throw new BadRequestAlertException("Filed name does't exiest in product", ENTITY_NAME,
					queryFilter.getFiledName());
		}
		List<Product> products = productService.findProductByFiltering(queryFilter);
		if (products != null && products.size() > 0) {
			return ResponseEntity.noContent().headers(
					HeaderUtil.createAlert(applicationName, Constants.RECORD_NOT_FOUND, queryFilter.getFiledName()))
					.build();
		}
		List<Product> updatedProductList = new ArrayList<>();
		for (Product product : products) {
			GenricData meta = new GenricData(true, true,
					new Document().append("timeStamp", product.getTimestamp()).append("price", product.getPrice()));
			product.setMeta(meta.getMeta());
			updatedProductList.add(product);
		}
		return ResponseEntity.ok().body(updatedProductList);
	}

	@SuppressWarnings({ "null", "static-access" })
	@ApiResponses(value = { @ApiResponse(code = 204, message = Constants.NO_CONTENT, responseHeaders = {
			@ResponseHeader(name = Constants.X_BRAND_APP_ALERT, response = String.class, description = Constants.RECORD_NOT_FOUND),
			@ResponseHeader(name = Constants.X_BRAND_APP_PARAM, response = String.class, description = "BRAND ID") }),
			@ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403),
			@ApiResponse(code = 200, message = Constants.MESSAGE_200, response = Product.class, responseContainer = ENTITY_NAME) })
	@PostMapping("/product/{productId}/brands")
	public ResponseEntity<Brands> getProductBrandsRelationShip(
			@PathVariable(name = "productId", required = true) String productId, @RequestBody Brands brand) {
		log.debug("REST request to create relationship of product with brand : {}", productId);
		if (brand.getType() != null && !brand.getType().equalsIgnoreCase("brands")) {
			throw new BadRequestAlertException("A brands  object type is not equal to brands ", "brands", "");
		}
		Optional<Product> product = productService.findOne(productId);
		if (product.isEmpty()) {
			return ResponseEntity.noContent()
					.headers(HeaderUtil.createAlert(applicationName, Constants.RECORD_NOT_FOUND, productId)).build();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		String restBrand = restTemplate.exchange(brandUrl + "/" + brand.getId(), HttpMethod.GET, entity, String.class)
				.getBody();
		if (restBrand.isBlank()) {
			return ResponseEntity.noContent()
					.headers(HeaderUtil.createAlert(applicationName, Constants.RECORD_NOT_FOUND, brand.getId()))
					.build();
		}

		BrandProductRelation brandProductRelation = new BrandProductRelation();
		brandProductRelation.setBrandId(brand.getId());
		brandProductRelation.setType(brand.getType());
		brandProductRelation.setId(productId);
		brandProductRelationService.save(brandProductRelation);
		return ResponseEntity.ok().body(brand);
	}

	@ApiResponses(value = { @ApiResponse(code = 204, message = Constants.NO_CONTENT, responseHeaders = {
			@ResponseHeader(name = Constants.X_BRAND_APP_ALERT, response = String.class, description = Constants.RECORD_NOT_FOUND),
			@ResponseHeader(name = Constants.X_BRAND_APP_PARAM, response = String.class, description = "BRAND ID") }),
			@ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403),
			@ApiResponse(code = 200, message = Constants.MESSAGE_200, response = Product.class, responseContainer = ENTITY_NAME) })
	@PutMapping("/product/{productId}/brands")
	public ResponseEntity<Brands> addProductBrandsRelationShip(
			@PathVariable(name = "productId", required = true) String productId, @RequestBody Brands brand) {
		log.debug("REST request to update relationship of product with brand : {}", productId);
		if (brand.getType() != null && !brand.getType().equalsIgnoreCase("brands")) {
			throw new BadRequestAlertException("A brands  object type is not equal to brands ", "brands", "");
		}
		Optional<Product> product = productService.findOne(productId);
		if (product.isEmpty()) {
			return ResponseEntity.noContent()
					.headers(HeaderUtil.createAlert(applicationName, Constants.RECORD_NOT_FOUND, productId)).build();
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		String restBrand = restTemplate.exchange(brandUrl + "/" + brand.getId(), HttpMethod.GET, entity, String.class)
				.getBody();
		if (restBrand.isBlank()) {
			return ResponseEntity.noContent()
					.headers(HeaderUtil.createAlert(applicationName, Constants.RECORD_NOT_FOUND, brand.getId()))
					.build();
		}
		BrandProductRelation brandProductRelation = new BrandProductRelation();
		brandProductRelation.setBrandId(brand.getId());
		brandProductRelation.setType(brand.getType());
		brandProductRelation.setId(productId);
		brandProductRelationService.save(brandProductRelation);
		return ResponseEntity.ok().body(brand);
	}

	@ApiResponses(value = { @ApiResponse(code = 204, message = Constants.NO_CONTENT, responseHeaders = {
			@ResponseHeader(name = Constants.X_BRAND_APP_ALERT, response = String.class, description = Constants.RECORD_NOT_FOUND),
			@ResponseHeader(name = Constants.X_BRAND_APP_PARAM, response = String.class, description = "BRAND ID") }),
			@ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403),
			@ApiResponse(code = 200, message = Constants.MESSAGE_200, response = Product.class, responseContainer = ENTITY_NAME) })
	@DeleteMapping("/product/{productId}/brands")
	public ResponseEntity<Brands> deleteProductBrandsRelationShip(
			@PathVariable(name = "productId", required = true) String productId, @RequestBody Brands brand) {
		log.debug("REST request to delete relationship of product with brand : {}", productId);
		if (brand.getType() != null && !brand.getType().equalsIgnoreCase("brands")) {
			throw new BadRequestAlertException("A brands  object type is not equal to brands ", "brands", "");
		}
		brandProductRelationService.delete(productId);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, brand.getId()))
				.build();
	}

}
