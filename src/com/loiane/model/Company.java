package com.loiane.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.loiane.util.JsonDateSerializer;

/**
 * Company POJO
 * 
 * @author Loiane Groner
 * http://loianegroner.com (English)
 * http://loiane.com (Portuguese)
 */
@JsonAutoDetect
@Entity
@Table(name="COMPANY")
public class Company {

	private int id;
	private double price;
	private String company;
	private Date date;
	private String size;
	private byte visible;
	
	@Id
	@GeneratedValue
	@Column(name="ID")
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="PRICE", nullable=false)
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	@Column(name="COMPANY", nullable=false, length=255)
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	@JsonSerialize(using=JsonDateSerializer.class)
	@Column(name="DATE", nullable=false)
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	@Column(name="SIZE", nullable=false, length=40)
	public String getSize() {
		return size;
	}
	
	public void setSize(String size) {
		this.size = size;
	}
	
	@Column(name="VISIBLE", nullable=false, length=1)
	public byte getVisible() {
		return visible;
	}
	
	public void setVisible(byte visible) {
		this.visible = visible;
	}
}
