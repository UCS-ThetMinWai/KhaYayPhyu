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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonView;
import com.khayayphyu.domain.constant.PackingType;
import com.khayayphyu.domain.jsonviews.DetailView;
import com.khayayphyu.domain.jsonviews.SummaryView;

@Entity
@Table(name = "product")
public class Product extends AbstractEntity {

	@JsonView(SummaryView.class)
	@Column(name = "productName")
	private String productName;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	@JsonView(DetailView.class)
	private List<Item> itemList;

	@JsonView(SummaryView.class)
	@OneToOne
	private SalePrice salePrice;

	@JsonView(SummaryView.class)
	@OneToOne(cascade = CascadeType.ALL)
	private PurchasePrice purchasePrice;

	@JsonView(SummaryView.class)
	@Column(name = "quantity")
	private int quantity;

	@Column(name = "peckagingType")
	@Enumerated(EnumType.STRING)
	@JsonView(SummaryView.class)
	private PackingType packagingType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "peckagingDate")
	@JsonView(SummaryView.class)
	private Date packagingDate;

	@JsonView(DetailView.class)
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<SalePrice> salePriceHistory;

	@JsonView(DetailView.class)
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
	private List<PurchasePrice> purchasePriceHistory;

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

	public List<Item> getItemList() {
		return itemList;
	}

	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
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

	public PurchasePrice getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(PurchasePrice purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public List<PurchasePrice> getPurchasePriceHistory() {
		return purchasePriceHistory;
	}

	public void setPurchasePriceHistory(List<PurchasePrice> purchasePriceHistory) {
		this.purchasePriceHistory = purchasePriceHistory;
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

	public boolean isSameSalePrice(Product product) {
		return salePrice.getAmount() == product.salePrice.getAmount();
	}

	public boolean isSamePurchaseePrice(Product product) {
		return purchasePrice.getAmount() == product.purchasePrice.getAmount();
	}

	public void updateSalePrice(SalePrice price) {
		addSalePriceHistory(salePrice);
		salePrice = price;
	}

	public void updatePurchasePrice(PurchasePrice purchasePrice) {
		addPurchasePriceHistory(purchasePrice);
		this.purchasePrice = purchasePrice;
	}

	public void addNewSalePrice(SalePrice newPrice) {
		addSalePriceHistory(salePrice);
		salePrice = newPrice;
	}

	public void addNewPurchasePrice(PurchasePrice newPurchasePrice) {
		addPurchasePriceHistory(newPurchasePrice);
		purchasePrice = newPurchasePrice;
	}

	public void addSalePriceHistory(SalePrice oldPrice) {
		if (salePriceHistory == null) {
			salePriceHistory = new ArrayList<>();
		}
		oldPrice.setProduct(this);
		salePriceHistory.add(oldPrice);
	}

	public void addPurchasePriceHistory(PurchasePrice oldPrice) {
		if (purchasePriceHistory == null) {
			purchasePriceHistory = new ArrayList<>();
		}
		oldPrice.setProduct(this);
		purchasePriceHistory.add(oldPrice);
	}

	public String toString() {
		return new ToStringBuilder(this).append(packagingType).append(packagingDate).toString();
	}

}
