package com.khayayphyu.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rawProduct")
public class RawProduct extends AbstractEntity {
	
	@Column(name = "productName")
	private String productName;
	
	@Column(name = "productType")
	private String productType;
	
	public RawProduct() {
		super();
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
}
