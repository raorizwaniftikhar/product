package com.java.spring.domain;

import org.bson.Document;

public class RelationShips {

	Document meta = new Document();

	public RelationShips(boolean brand, Document data) {
		if (brand) {
			meta.append("brand", data.get(""));
		}
	}

	public Document getMeta() {
		return meta;
	}

	public void setMeta(Document meta) {
		this.meta = meta;
	}

}
