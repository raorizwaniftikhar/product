package com.java.spring.domain;

import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.Field;

public class Price {

	@Field("amount")
	private Integer amount;
	@Field("currencey")
	@Size(min = 3, max = 3)
	private String currency;
	@Field("includes_tax")
	private boolean includes_tax;
	

	public Integer getAmount() {
		return amount;
	}

	public Price amount(Integer amount) {
		this.amount = amount;
		return this;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public Price currency(String currency) {
		this.currency = currency;
		return this;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public boolean isIncludes_tax() {
		return includes_tax;
	}

	public Price includes_tax(boolean includes_tax) {
		this.includes_tax = includes_tax;
		return this;
	}

	public void setIncludes_tax(boolean includes_tax) {
		this.includes_tax = includes_tax;
	}

	@Override
	public String toString() {
		return "Price [amount=" + amount + ", currency=" + currency + ", includesTax=" + includes_tax + "]";
	}

}
