package com.khayayphyu.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.khayayphyu.domain.jsonviews.DetailView;
import com.khayayphyu.domain.jsonviews.SummaryView;
import com.khayayphyu.domain.jsonviews.Views;
import com.khayayphyu.domain.jsonviews.Views.Comprehensive;

@Entity
@Table(name = "purchase")
public class Purchase extends AbstractEntity {

	@JsonView(Comprehensive.class)
	@OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<PurchaseOrder> purchaseOrderList;

	@JsonView(Views.Comprehensive.class)
	@ManyToOne(fetch = FetchType.LAZY)
	private Customer customer;

	@JsonView(SummaryView.class)
	@Column(name = "total")
	private int total;

	@JsonView(SummaryView.class)
	@Column(name = "payAmount")
	private int payAmount;

	@JsonView(DetailView.class)
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Temporal(TemporalType.DATE)
	@Column(name = "purchaseDate")
	@JsonView(SummaryView.class)
	private Date purchaseDate;

	public Purchase() {
		super();
	}

	public Purchase(String boId) {
		super(boId);
	}

	public Date getPurchaseDate() {
		return purchaseDate;
	}

	public List<PurchaseOrder> getPurchaseOrderList() {
		return purchaseOrderList;
	}

	public void setPurchaseOrderList(List<PurchaseOrder> purchaseOrderList) {
		this.purchaseOrderList = purchaseOrderList;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(int payAmount) {
		this.payAmount = payAmount;
	}

	public int getBalance() {
		return total - payAmount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String toString() {
		return new ToStringBuilder(this).append(purchaseDate).toString();
	}
}
