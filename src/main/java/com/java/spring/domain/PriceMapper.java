package com.java.spring.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PriceMapper {

	@NotNull
	private Integer amount;
	@NotNull
	@Size(min = 3, max = 3)
	private String currency;
	private boolean includes_tax;

	Price price = new Price();

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
		price.setAmount(amount);
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
		price.setCurrency(currency);
	}

	public boolean isIncludes_tax() {
		return includes_tax;
	}

	public void setIncludes_tax(boolean includes_tax) {
		this.includes_tax = includes_tax;
		price.setIncludes_tax(includes_tax);
	}

	@JsonIgnore
	public Price getPriceObject() {
		return price;
	}

	@Override
	public String toString() {
		return "Price [amount=" + amount + ", currency=" + currency + ", includesTax=" + includes_tax + "]";
	}

}
