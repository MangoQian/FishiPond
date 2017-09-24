package com.example.app3.data_bean;

import java.util.Date;


public class Feeding {
	private Integer fid;
	private Integer devicestatus;
	private Date vartime;
	private Device device;
	
	public Integer getFid() {
		return fid;
	}
	public void setFid(Integer fid) {
		this.fid = fid;
	}
	public Integer getDevicestatus() {
		return devicestatus;
	}
	public void setDevicestatus(Integer devicestatus) {
		this.devicestatus = devicestatus;
	}
	public Date getVartime() {
		return vartime;
	}
	public void setVartime(Date vartime) {
		this.vartime = vartime;
	}
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	
	
}
