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

	@Column(name = "productName")
	private String productName;

	@ManyToMany
	private List<Product> productList;

	@OneToOne(cascade = CascadeType.ALL)
	private SalePrice salePrice;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "peckagingType")
	@Enumerated(EnumType.STRING)
	private PackingType packagingType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "peckagingDate")
	private Date packagingDate;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<SalePrice> salePriceHistory;

	public Product() {
		super();
	}

	public SalePrice getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(SalePrice currentPrice) {
		this.salePrice = currentPrice;
	}

	public Product(String boId) {
		super(boId);
	}

	public List<SalePrice> getPriceList() {
		return salePriceHistory;
	}

	public void setPriceList(List<SalePrice> priceList) {
		this.salePriceHistory = priceList;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public List<SalePrice> getSalePriceHistory() {
		return salePriceHistory;
	}

	public void setSalePriceHistory(List<SalePrice> salePriceHistory) {
		this.salePriceHistory = salePriceHistory;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public PackingType getPackagingType() {
		return packagingType;
	}

	public void setPackagingType(PackingType peckagingType) {
		this.packagingType = peckagingType;
	}

	public Date getPackagingDate() {
		return packagingDate;
	}

	public void setPackagingDate(Date peckagingDate) {
		this.packagingDate = peckagingDate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public boolean isSamePrice(Product product) {
		return salePrice.getAmount() == product.salePrice.getAmount();
	}

	public void updateSalePrice(SalePrice price) {
		addSalePriceHistory(salePrice);
		salePrice = price;
	}

	public void addNewSalePrice(SalePrice newPrice) {
		addSalePriceHistory(salePrice);
		salePrice = newPrice;
	}

	public void addSalePriceHistory(SalePrice oldPrice) {
		if (salePriceHistory == null) {
			salePriceHistory = new ArrayList<>();
		}
		oldPrice.setProduct(this);
		salePriceHistory.add(oldPrice);
	}

	public String toString() {
		return new ToStringBuilder(this).append(packagingType).append(packagingDate).toString();
	}

}
