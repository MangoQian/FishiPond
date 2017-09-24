package com.example.app3.data_bean;

import java.util.Date;

public class Notice {
	private Integer noid;
	private String article;
	private String htmls;
	private Date time;
	private Integer status;
	public Integer getNoid() {
		return noid;
	}
	public void setNoid(Integer noid) {
		this.noid = noid;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public String getHtmls() {
		return htmls;
	}
	public void setHtmls(String htmls) {
		this.htmls = htmls;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	

}
