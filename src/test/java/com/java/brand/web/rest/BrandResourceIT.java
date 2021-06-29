//package com.java.brand.web.rest;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.List;
//
//import org.json.JSONObject;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import com.java.spring.BrandApplication;
//import com.java.spring.domain.Brands;
//import com.java.spring.repository.BrandsRepository;
//import com.java.spring.service.BrandsService;
//
///**
// * Integration tests for the {@link BrandResource} REST controller.
// */
//@SpringBootTest(classes = BrandApplication.class)
//@AutoConfigureMockMvc
//public class BrandResourceIT {
//
//	private static final String DEFAULT_TYPE = "AAAAAAAAAA";
//	private static final String UPDATED_TYPE = "BBBBBBBBBB";
//
//	private static final String DEFAULT_NAME = "AAAAAAAAAA";
//	private static final String UPDATED_NAME = "BBBBBBBBBB";
//
//	private static final String DEFAULT_SLUG = "AAAAAAAAAA";
//	private static final String UPDATED_SLUG = "BBBBBBBBBB";
//
//	private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
//	private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";
//
//	private static final String DEFAULT_STATUS = "live";
//	private static final String UPDATED_STATUS = "draft";
//
//	private static final String URL = "/api/brands";
//
//	@Autowired
//	private BrandsRepository brandRepository;
//
//	@Autowired
//	private BrandsService brandService;
//
//	@Autowired
//	private MockMvc restBrandMockMvc;
//
//	private Brands brand;
//
//	/**
//	 * Create an entity for this test.
//	 *
//	 * This is a static method, as tests for other entities might also need it, if
//	 * they test an entity which requires the current entity.
//	 */
//	public static Brands createEntity() {
//		Brands brand = new Brands().type(DEFAULT_TYPE).name(DEFAULT_NAME).slug(DEFAULT_SLUG)
//				.description(DEFAULT_DESCRIPTION).status(DEFAULT_STATUS);
//		return brand;
//	}
//
//	/**
//	 * Create an updated entity for this test.
//	 *
//	 * This is a static method, as tests for other entities might also need it, if
//	 * they test an entity which requires the current entity.
//	 */
//	public static Brands createUpdatedEntity() {
//		Brands brand = new Brands().type(UPDATED_TYPE).name(UPDATED_NAME).slug(UPDATED_SLUG)
//				.description(UPDATED_DESCRIPTION).status(UPDATED_STATUS);
//		return brand;
//	}
//
//	@BeforeEach
//	public void initTest() {
//		brandRepository.deleteAll();
//		brand = createEntity();
//	}
//
//	@Test
//	public void createBrand() throws Exception {
//		int databaseSizeBeforeCreate = brandRepository.findAll().size();
//		// Create the Brand
//		JSONObject data = new JSONObject();
//		data.put("data", brand);
//		restBrandMockMvc.perform(
//				post(URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(data)))
//				.andExpect(status().isCreated());
//
//		// Validate the Brand in the database
//		List<Brands> brandList = brandRepository.findAll();
//		assertThat(brandList).hasSize(databaseSizeBeforeCreate + 1);
//		Brands testBrand = brandList.get(brandList.size() - 1);
//		assertThat(testBrand.getType()).isEqualTo(DEFAULT_TYPE);
//		assertThat(testBrand.getName()).isEqualTo(DEFAULT_NAME);
//		assertThat(testBrand.getSlug()).isEqualTo(DEFAULT_SLUG);
//		assertThat(testBrand.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
//		assertThat(testBrand.getStatus()).isEqualTo(DEFAULT_STATUS);
//	}
//
//	@Test
//	public void createBrandWithExistingId() throws Exception {
//		int databaseSizeBeforeCreate = brandRepository.findAll().size();
//
//		// Create the Brand with an existing ID
//		brand.setId("existing_id");
//		JSONObject data = new JSONObject();
//		data.put("data", brand);
//		// An entity with an existing ID cannot be created, so this API call must fail
//		restBrandMockMvc.perform(
//				post(URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(data)))
//				.andExpect(status().isBadRequest());
//
//		// Validate the Brand in the database
//		List<Brands> brandList = brandRepository.findAll();
//		assertThat(brandList).hasSize(databaseSizeBeforeCreate);
//	}
//
//	@Test
//	public void checkTypeIsRequired() throws Exception {
//		int databaseSizeBeforeTest = brandRepository.findAll().size();
//		// set the field null
//		brand.setType(null);
//
//		// Create the Brand, which fails.
//		JSONObject data = new JSONObject();
//		data.put("data", brand);
//		restBrandMockMvc.perform(
//				post(URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(data)))
//				.andExpect(status().isBadRequest());
//
//		List<Brands> brandList = brandRepository.findAll();
//		assertThat(brandList).hasSize(databaseSizeBeforeTest);
//	}
//
//	@Test
//	public void checkNameIsRequired() throws Exception {
//		int databaseSizeBeforeTest = brandRepository.findAll().size();
//		// set the field null
//		brand.setName(null);
//
//		// Create the Brand, which fails.
//		JSONObject data = new JSONObject();
//		data.put("data", brand);
//		restBrandMockMvc.perform(
//				post(URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(data)))
//				.andExpect(status().isBadRequest());
//
//		List<Brands> brandList = brandRepository.findAll();
//		assertThat(brandList).hasSize(databaseSizeBeforeTest);
//	}
//
//	@Test
//	public void checkSlugIsRequired() throws Exception {
//		int databaseSizeBeforeTest = brandRepository.findAll().size();
//		// set the field null
//		brand.setSlug(null);
//
//		// Create the Brand, which fails.
//		JSONObject data = new JSONObject();
//		data.put("data", brand);
//		restBrandMockMvc.perform(
//				post(URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(data)))
//				.andExpect(status().isBadRequest());
//
//		List<Brands> brandList = brandRepository.findAll();
//		assertThat(brandList).hasSize(databaseSizeBeforeTest);
//	}
//
//	@Test
//	public void getAllBrands() throws Exception {
//		// Initialize the database
//		brandRepository.save(brand);
//
//		// Get all the brandList
//		restBrandMockMvc.perform(get(URL + "?sort=id,desc")).andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(jsonPath("$.data.[*].id").value(hasItem(brand.getId())))
//				.andExpect(jsonPath("$.data.[*].type").value(hasItem(DEFAULT_TYPE)))
//				.andExpect(jsonPath("$.data.[*].name").value(hasItem(DEFAULT_NAME)))
//				.andExpect(jsonPath("$.data.[*].slug").value(hasItem(DEFAULT_SLUG)))
//				.andExpect(jsonPath("$.data.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
//				.andExpect(jsonPath("$.data.[*].status").value(hasItem(DEFAULT_STATUS)));
//	}
//
//	@Test
//	public void getBrand() throws Exception {
//		// Initialize the database
//		brandRepository.save(brand);
//
//		// Get the brand
//		restBrandMockMvc.perform(get(URL + "/{id}", brand.getId())).andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(jsonPath("$.data.id").value(brand.getId()))
//				.andExpect(jsonPath("$.data.type").value(DEFAULT_TYPE))
//				.andExpect(jsonPath("$.data.name").value(DEFAULT_NAME))
//				.andExpect(jsonPath("$.data.slug").value(DEFAULT_SLUG))
//				.andExpect(jsonPath("$.data.description").value(DEFAULT_DESCRIPTION))
//				.andExpect(jsonPath("$.data.status").value(DEFAULT_STATUS));
//	}
//
//	@Test
//	public void getNonExistingBrand() throws Exception {
//		// Get the brand
//		restBrandMockMvc.perform(get(URL + "/{id}", Long.MAX_VALUE)).andExpect(status().isNoContent());
//	}
//
//	@Test
//	public void updateBrand() throws Exception {
//		// Initialize the database
//		brandService.save(brand);
//
//		int databaseSizeBeforeUpdate = brandRepository.findAll().size();
//
//		// Update the brand
//		Brands updatedBrand = brandRepository.findById(brand.getId()).get();
//		updatedBrand.type(UPDATED_TYPE).name(UPDATED_NAME).slug(UPDATED_SLUG).description(UPDATED_DESCRIPTION)
//				.status(UPDATED_STATUS);
//		JSONObject data = new JSONObject();
//		data.put("data", updatedBrand);
//		restBrandMockMvc.perform(put(URL + "/" + brand.getId()).contentType(MediaType.APPLICATION_JSON)
//				.content(TestUtil.convertObjectToJsonBytes(data))).andExpect(status().isOk());
//
//		// Validate the Brand in the database
//		List<Brands> brandList = brandRepository.findAll();
//		assertThat(brandList).hasSize(databaseSizeBeforeUpdate);
//		Brands testBrand = brandList.get(brandList.size() - 1);
//		assertThat(testBrand.getType()).isEqualTo(UPDATED_TYPE);
//		assertThat(testBrand.getName()).isEqualTo(UPDATED_NAME);
//		assertThat(testBrand.getSlug()).isEqualTo(UPDATED_SLUG);
//		assertThat(testBrand.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
//		assertThat(testBrand.getStatus()).isEqualTo(UPDATED_STATUS);
//	}
//
//	@Test
//	public void updateNonExistingBrand() throws Exception {
//		int databaseSizeBeforeUpdate = brandRepository.findAll().size();
//		JSONObject data = new JSONObject();
//		data.put("data", brand);
//		// If the entity doesn't have an ID, it will throw BadRequestAlertException
//		restBrandMockMvc.perform(put(URL + "/" + brand.getId()).contentType(MediaType.APPLICATION_JSON)
//				.content(TestUtil.convertObjectToJsonBytes(data))).andExpect(status().isBadRequest());
//
//		// Validate the Brand in the database
//		List<Brands> brandList = brandRepository.findAll();
//		assertThat(brandList).hasSize(databaseSizeBeforeUpdate);
//	}
//
//	@Test
//	public void deleteBrand() throws Exception {
//		// Initialize the database
//		brandService.save(brand);
//
//		int databaseSizeBeforeDelete = brandRepository.findAll().size();
//
//		// Delete the brand
//		restBrandMockMvc.perform(delete(URL + "/{id}", brand.getId()).accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isNoContent());
//
//		// Validate the database contains one less item
//		List<Brands> brandList = brandRepository.findAll();
//		assertThat(brandList).hasSize(databaseSizeBeforeDelete - 1);
//	}
//
//	@Test
//	public void getBrandByName() throws Exception {
//		// Initialize the database
//		brandRepository.save(brand);
//
//		// Get the brand
//		restBrandMockMvc.perform(get(URL + "/search?filter=eq(name,'" + brand.getName() + "')"))
//				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(jsonPath("$.data.id").value(brand.getId()))
//				.andExpect(jsonPath("$.data.type").value(DEFAULT_TYPE))
//				.andExpect(jsonPath("$.data.name").value(DEFAULT_NAME))
//				.andExpect(jsonPath("$.data.slug").value(DEFAULT_SLUG))
//				.andExpect(jsonPath("$.data.description").value(DEFAULT_DESCRIPTION))
//				.andExpect(jsonPath("$.data.status").value(DEFAULT_STATUS));
//	}
//
//	@Test
//	public void getAllBrandsBySlug() throws Exception {
//		// Initialize the database
//		brandRepository.save(brand);
//
//		// Get all the brandList by Slug
//		restBrandMockMvc.perform(get(URL + "/search?filter=eq(slug,'" + brand.getSlug() + "')"))
//				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(jsonPath("$.data.[*].id").value(hasItem(brand.getId())))
//				.andExpect(jsonPath("$.data.[*].type").value(hasItem(DEFAULT_TYPE)))
//				.andExpect(jsonPath("$.data.[*].name").value(hasItem(DEFAULT_NAME)))
//				.andExpect(jsonPath("$.data.[*].slug").value(hasItem(DEFAULT_SLUG)))
//				.andExpect(jsonPath("$.data.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
//				.andExpect(jsonPath("$.data.[*].status").value(hasItem(DEFAULT_STATUS)));
//	}
//
//	@Test
//	public void getAllBrandsByType() throws Exception {
//		// Initialize the database
//		brandRepository.save(brand);
//
//		// Get all the brandList by Type
//		restBrandMockMvc.perform(get(URL + "/search?filter=eq(status,'" + brand.getStatus() + "')"))
//				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(jsonPath("$.data.[*].id").value(hasItem(brand.getId())))
//				.andExpect(jsonPath("$.data.[*].type").value(hasItem(DEFAULT_TYPE)))
//				.andExpect(jsonPath("$.data.[*].name").value(hasItem(DEFAULT_NAME)))
//				.andExpect(jsonPath("$.data.[*].slug").value(hasItem(DEFAULT_SLUG)))
//				.andExpect(jsonPath("$.data.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
//				.andExpect(jsonPath("$.data.[*].status").value(hasItem(DEFAULT_STATUS)));
//	}
//}
