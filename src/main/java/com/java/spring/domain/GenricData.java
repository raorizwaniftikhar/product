package com.java.spring.domain;

import java.util.ArrayList;

import org.bson.Document;

public class GenricData {

	Document meta = new Document();

	public GenricData(boolean timeStamp, boolean displayPrice, Document data) {
		if (timeStamp) {
			meta.append("timestamps", data.get("timeStamp"));
		}
		if (displayPrice) {
			Document includeTax = new Document();
			Document without_tax = new Document();
			@SuppressWarnings("unchecked")
			ArrayList<Price> prices = (ArrayList<Price>) data.get("price");
			for (Price price : prices) {
				without_tax.append("amount", price.getAmount());
				without_tax.append("currencey", price.getCurrency());
				if (price.isIncludes_tax()) {
					includeTax.append("amount", price.getAmount());
					includeTax.append("currencey", price.getCurrency());
				}
			}
			meta.append("display_price",
					new Document().append("with_tax", includeTax).append("without_tax", without_tax));
		}
	}

	public Document getMeta() {
		return meta;
	}

	public void setMeta(Document meta) {
		this.meta = meta;
	}

}
