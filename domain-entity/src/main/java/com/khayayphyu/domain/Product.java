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
import javax.persistence.ManyToMany;
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
	private List<Price> salePriceHistory;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Price> buyPriceHistory;

	@Column(name = "productName")
	private String productName;
	
	@ManyToMany
	private List<Product> productList;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Price currentSalePrice;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Price currentBuyPrice;

	public Price getCurrentPrice() {
		return currentSalePrice;
	}

	public void setCurrentPrice(Price currentPrice) {
		this.currentSalePrice = currentPrice;
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
		return salePriceHistory;
	}

	public void setPriceList(List<Price> priceList) {
		this.salePriceHistory = priceList;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public List<Price> getSalePriceHistory() {
		return salePriceHistory;
	}

	public void setSalePriceHistory(List<Price> salePriceHistory) {
		this.salePriceHistory = salePriceHistory;
	}

	public List<Price> getBuyPriceHistory() {
		return buyPriceHistory;
	}

	public void setBuyPriceHistory(List<Price> buyPriceHistory) {
		this.buyPriceHistory = buyPriceHistory;
	}

	public Price getCurrentSalePrice() {
		return currentSalePrice;
	}

	public void setCurrentSalePrice(Price currentSalePrice) {
		this.currentSalePrice = currentSalePrice;
	}

	public Price getCurrentBuyPrice() {
		return currentBuyPrice;
	}

	public void setCurrentBuyPrice(Price currentBuyPrice) {
		this.currentBuyPrice = currentBuyPrice;
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
		return currentSalePrice.getSaleAmount() == product.currentSalePrice.getSaleAmount();
	}
	
	public void addPriceHistory(Price oldPrice) {
		if(salePriceHistory == null) {
			salePriceHistory = new ArrayList<>();
		}
		salePriceHistory.add(oldPrice);
	}

	public String toString() {
		return new ToStringBuilder(this).append(peckagingType).append(peckagingDate).toString();
	}

}
