package com.loiane.model;

import javax.persistence.Entity;

/**
 * Represents ExtJS Filter object from Request.
 * Following are an example of request object
 * filter	[{"type":"numeric","comparison":"lt","value":5,"field":"id"}]
 * 
 * @author Loiane Groner
 * http://loianegroner.com (English)
 * http://loiane.com (Portuguese)
 */
@Entity
public class ExtJSFilter {

	private String type;
	private String comparison;
	private String value;
	private String field;
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getComparison() {
		return comparison;
	}
	
	public void setComparison(String comparison) {
		this.comparison = comparison;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
}
