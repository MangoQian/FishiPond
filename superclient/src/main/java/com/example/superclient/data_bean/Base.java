package com.example.superclient.data_bean;

import java.util.Date;
import java.util.Set;

public class Base {
	private Integer bid;
	private String basename;
	private String address;
	private String baseIntro;

	public String getBaseIntro() {
		return baseIntro;
	}

	public void setBaseIntro(String baseIntro) {
		this.baseIntro = baseIntro;
	}

	private Date basedate;

	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getBasename() {
		return basename;
	}
	public void setBasename(String basename) {
		this.basename = basename;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBasedate() {
		return basedate;
	}
	public void setBasedate(Date basedate) {
		this.basedate = basedate;
	}
	public Set<Pond> getPond() {
		return pond;
	}
	public void setPond(Set<Pond> pond) {
		this.pond = pond;
	}
	private Set<Pond> pond;

}
