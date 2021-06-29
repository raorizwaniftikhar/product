package com.java.spring.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Product.
 */
public class ProductMapper implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	@NotNull
	private String type;

	@NotNull
	private String name;

	@NotNull
	private String slug;
	@NotNull
	private String sku;
	@NotNull
	private String description;

	@NotNull
	private boolean manage_stock;

	private String status;

	@NotNull
	private String commodity_type;
	@NotNull
	private List<PriceMapper> price;

	Product product = new Product();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		product.setType(type);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		product.setName(name);
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
		product.setSlug(slug);
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
		product.setSku(sku);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		product.setDescription(description);
	}

	public boolean isManage_stock() {
		return manage_stock;
	}

	public void setManage_stock(boolean manage_stock) {
		this.manage_stock = manage_stock;
		product.setManage_stock(manage_stock);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
		product.setStatus(status);
	}

	public String getCommodity_type() {
		return commodity_type;
	}

	public void setCommodity_type(String commodity_type) {
		this.commodity_type = commodity_type;
		product.setCommodity_type(commodity_type);
	}

	public List<PriceMapper> getPrice() {
		return price;
	}

	public void setPrice(List<PriceMapper> price) {
		this.price = price;
		List<Price> priceList = new ArrayList<>();
		for (PriceMapper priceMapper : price) {
			Price pri = priceMapper.getPriceObject();
			priceList.add(pri);
		}
		product.setPrice(priceList);
	}

	@JsonIgnore
	public Product getProductObject() {
		return product;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", type=" + type + ", name=" + name + ", slug=" + slug + ", sku=" + sku
				+ ", description=" + description + ", manageStock=" + manage_stock + ", status=" + status
				+ ", commodityType=" + commodity_type + ", price=" + price + "]";
	}

}
