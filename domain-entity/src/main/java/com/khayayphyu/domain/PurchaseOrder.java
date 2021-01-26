package com.khayayphyu.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.khayayphyu.domain.jsonviews.SummaryView;
import com.khayayphyu.domain.jsonviews.Views;

@Entity
@Table(name = "purchaseOrder")
public class PurchaseOrder extends AbstractEntity {

	@Column(name = "price")
	@JsonView(SummaryView.class)
	private int price;
	
	@Column(name = "amount")
	@JsonView(SummaryView.class)
	private int amount;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Purchase purchase;

	@JsonView(Views.Comprehensive.class)
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	@JsonView(SummaryView.class)
	@Column(name = "quantity")
	private int quantity;

	@JsonView(SummaryView.class)
	@Temporal(TemporalType.DATE)
	@Column(name = "date")
	private Date date;

	public PurchaseOrder() {
		super();
		setBoId("PRCHSOR-" + System.nanoTime());
	}

	public PurchaseOrder(String boId) {
		super(boId);
	}

	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

}
