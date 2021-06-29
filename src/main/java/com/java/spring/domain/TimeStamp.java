package com.java.spring.domain;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Base abstract class for entities which will hold definitions for created,
 * last modified, created by, last modified by attributes.
 */
public class TimeStamp implements Serializable {

	private static final long serialVersionUID = 1L;
	@CreatedDate
	@Field("created_at")
	private Instant created_at = Instant.now();

	@LastModifiedDate
	@Field("updated_at")
	private Instant updated_at = Instant.now();

	public Instant getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Instant created_at) {
		this.created_at = created_at;
	}

	public Instant getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Instant updated_at) {
		this.updated_at = updated_at;
	}

}
