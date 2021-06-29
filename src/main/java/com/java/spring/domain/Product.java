package com.java.spring.domain;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Brand.
 */
@Document(collection = "product")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@NotNull
	@Field("type")
	@Indexed
	private String type;

	@Field("name")
	@Indexed
	private String name;

	@Field("slug")
	@Indexed(unique = true)
	private String slug;
	@Field("sku")
	@Indexed
	private String sku;
	@Field("description")
	@Indexed
	private String description;

	@Field("manage_stock")
	private boolean manage_stock;

	@Field("status")
	@Indexed
	private String status;

	@Field("commodity_type")
	@Indexed
	private String commodity_type;

	@Field("price")
	private List<Price> price;

	@Field("timestamp")
	@JsonIgnore
	TimeStamp timestamp = new TimeStamp();

	private org.bson.Document meta;

	private org.bson.Document relationships;

	public TimeStamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(TimeStamp timestamp) {
		this.timestamp = timestamp;
	}

	public org.bson.Document getMeta() {
		return meta;
	}

	public void setMeta(org.bson.Document meta) {
		this.meta = meta;
	}

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
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isManage_stock() {
		return manage_stock;
	}

	public void setManage_stock(boolean manage_stock) {
		this.manage_stock = manage_stock;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCommodity_type() {
		return commodity_type;
	}

	public void setCommodity_type(String commodity_type) {
		this.commodity_type = commodity_type;
	}

	public List<Price> getPrice() {
		return price;
	}

	public void setPrice(List<Price> price) {
		this.price = price;
	}

	public org.bson.Document getRelationships() {
		return relationships;
	}

	public void setRelationships(org.bson.Document relationships) {
		this.relationships = relationships;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", type=" + type + ", name=" + name + ", slug=" + slug + ", sku=" + sku
				+ ", description=" + description + ", manage_stock=" + manage_stock + ", status=" + status
				+ ", commodity_type=" + commodity_type + ", price=" + price + ", timestamp=" + timestamp + ", meta="
				+ meta + ", relationships=" + relationships + "]";
	}

}
