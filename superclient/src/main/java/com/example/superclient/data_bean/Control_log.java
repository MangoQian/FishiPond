package com.example.superclient.data_bean;

import java.util.Date;

public class Control_log {
	private Integer clid;
	private String devicename;
	private String children_dname;
	private String content;
	private Date time;
	private Device device;
	
	public Integer getClid() {
		return clid;
	}
	public void setClid(Integer clid) {
		this.clid = clid;
	}

	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	public String getChildren_dname() {
		return children_dname;
	}
	public void setChildren_dname(String children_dname) {
		this.children_dname = children_dname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}


}
