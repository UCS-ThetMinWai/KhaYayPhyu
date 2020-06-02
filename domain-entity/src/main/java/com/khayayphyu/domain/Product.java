package com.khayayphyu.domain;

import java.util.ArrayList;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.khayayphyu.domain.constant.PackingType;

@Entity
@Table(name = "product")
public class Product extends AbstractEntity {

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Price> priceList;

	@Column(name = "productName")
	private String productName;
	
	@OneToOne
	private Price currentPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	private RawProduct rawProduct;

	public Price getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Price currentPrice) {
		this.currentPrice = currentPrice;
	}

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
	
	public boolean isSamePrice(Product product) {
		return currentPrice.getSaleAmount() == product.currentPrice.getSaleAmount();
	}
	
	public void addPriceHistory(Price oldPrice) {
		if(priceList == null) {
			priceList = new ArrayList<>();
		}
		priceList.add(oldPrice);
	}

	public String toString() {
		return new ToStringBuilder(this).append(rawProduct).append(peckagingType).append(peckagingDate).toString();
	}

}
