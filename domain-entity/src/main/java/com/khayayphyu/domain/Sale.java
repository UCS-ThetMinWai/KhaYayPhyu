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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.khayayphyu.domain.jsonviews.DetailView;
import com.khayayphyu.domain.jsonviews.SummaryView;

@Entity
@Table(name="sale")
public class Sale extends AbstractEntity {
	
	@JsonView(SummaryView.class)
	@ManyToOne(fetch = FetchType.EAGER)
	private Customer customer;
	
	@JsonView(DetailView.class)
	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<SaleOrder> saleOrderList;
	
	@JsonView(DetailView.class)
	@ManyToOne(fetch = FetchType.LAZY)
	private User saleBy;
	
	@JsonView(SummaryView.class)
	@Column(name = "total")
	private int total;
	
	@JsonView(SummaryView.class)
	@Column(name = "payAmount")
	private int payAmount;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "saleDate")
	@JsonView(SummaryView.class)
	private Date saleDate;
	
	public Sale() {
		super();
	}
	
	public Sale(String boId) {
		super(boId);
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<SaleOrder> getSaleOrderList() {
		return saleOrderList;
	}
	public void setSaleOrderList(List<SaleOrder> saleOrderList) {
		this.saleOrderList = saleOrderList;
	}
	
	public int getBalance() {
		return total - payAmount;
	}

	public User getSaleBy() {
		return saleBy;
	}

	public void setSaleBy(User saleBy) {
		this.saleBy = saleBy;
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

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}
}
