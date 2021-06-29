package com.java.spring.config;

import java.util.Arrays;

/**
 * Application constants.
 */
public final class Constants {

	// Response and Swagger Implementation
	public static final String NO_CONTENT = "NO CONTENT";
	public static final String RECORD_NOT_FOUND = " No Record Found";
	public static final String X_BRAND_APP_ALERT = "X-Brand App-alert";
	public static final String X_BRAND_APP_PARAM = "X-Brand App-params";
	public static final String MESSAGE_200 = "Successful retrieval";
	public static final String MESSAGE_403 = "Access is denied";
	public static final String MESSAGE_404 = "URL not found";
	public static final String VARIABLE_LIST = "List";
	public static final String MESSAGE_201 = "Record Created";
	public static final String MESSAGE_400 = "Validation Error";
	public static final String RECORD_DELETE = "Record Successfully Delete";
	public static final String LIVE = "live";
	public static final String DRAFT = "draft";
	public static final String PHYSICAL = "physical";
	public static final String DIGITAL = "digital";

	private Constants() {
	}

	public static boolean doesObjectContainField(Object object, String fieldName) {
		return Arrays.stream(object.getClass().getDeclaredFields()).anyMatch(f -> f.getName().equals(fieldName));
	}
}
