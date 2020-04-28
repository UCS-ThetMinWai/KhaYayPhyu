package com.khayayphyu.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.khayayphyu.domain.constant.PackingType;

@Entity
@Table(name = "product")
public class Product extends AbstractEntity {
	
	@OneToMany(mappedBy = "product", cascade  = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Price> priceList;
	
	@Column(name = "productName")
	private String productName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private RawProduct rawProduct;
	
	@Column(name = "quantity")
	private int quantity;
	
	@Column(name = "peckagingType")
	@Enumerated(EnumType.STRING)
	private PackingType peckagingType;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "peckagingDate")
	private Date peckagingDate;
	
	public Product() {
		super();
	}
	
	public Product(String boId) {
		super(boId);
	}
	
	public List<Price> getPriceList() {
		return priceList;
	}

	public void setPriceList(List<Price> priceList) {
		this.priceList = priceList;
	}

	public RawProduct getRawProduct() {
		return rawProduct;
	}
	public void setRawProduct(RawProduct rawProduct) {
		this.rawProduct = rawProduct;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public PackingType getPeckagingType() {
		return peckagingType;
	}
	public void setPeckagingType(PackingType peckagingType) {
		this.peckagingType = peckagingType;
	}
	public Date getPeckagingDate() {
		return peckagingDate;
	}
	public void setPeckagingDate(Date peckagingDate) {
		this.peckagingDate = peckagingDate;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String toString() {
		return new ToStringBuilder(this)
				.append(rawProduct)
				.append(peckagingType)
				.append(peckagingDate)
				.toString();
	}
	
}
