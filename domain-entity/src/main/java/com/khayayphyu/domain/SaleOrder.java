package com.khayayphyu.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.khayayphyu.domain.jsonviews.DetailView;
import com.khayayphyu.domain.jsonviews.SummaryView;
import com.khayayphyu.domain.jsonviews.Views;

@Entity
@Table(name = "saleOrder")
public class SaleOrder extends AbstractEntity {

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "sale")
	private Sale sale;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonView(Views.InnerSummary.class)
	private Product product;

	@JsonView(SummaryView.class)
	@Column(name = "price")
	private int price;

	@JsonView(SummaryView.class)
	@Column(name = "amount")
	private int amount;

	@JsonView(SummaryView.class)
	@Column(name = "weight")
	private double weight;

	@JsonView(SummaryView.class)
	@Column(name = "quantity")
	private int quantity;

	public SaleOrder() {
		super();
	}

	public Sale getSale() {
		return sale;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
