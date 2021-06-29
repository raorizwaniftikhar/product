package com.java.spring.config;

public class QueryFilter {

	private String filedName;
	private String queryOprator;
	private String value;

	public QueryFilter(String filter) {
		String[] queryArray = filter.split(",");
		value = queryArray[1].replace(")", "");
		if ((value.startsWith("\"") || value.startsWith("'")) && (value.endsWith("\"") || value.endsWith("'"))) {
			value = value.replaceAll("\"", "").replaceAll("'", "");
		}
		queryArray = queryArray[0].replace("(", " ").split(" ");
		queryOprator = queryArray[0];
		filedName = queryArray[1];
	}

	public String getFiledName() {
		return filedName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public String getQueryOprator() {
		return queryOprator;
	}

	public void setQueryOprator(String queryOprator) {
		this.queryOprator = queryOprator;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "QueryFilter [filedName=" + filedName + ", queryOprator=" + queryOprator + ", value=" + value + "]";
	}

}
