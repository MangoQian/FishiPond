package com.example.superclient.data_bean;

import java.util.Date;
import java.util.Set;

public class Pond {
	private Integer pid;
	private String pondname;
	private String pondaddress;
	private Date pondtime;
	private User user;
	private String pondStatus;

	public String getPondStatus() {
		return pondStatus;
	}

	public void setPondStatus(String pondStatus) {
		this.pondStatus = pondStatus;
	}

	public String getPondIntro() {
		return pondIntro;
	}

	public void setPondIntro(String pondIntro) {
		this.pondIntro = pondIntro;
	}

	private Set<Device> device;
	private String pondIntro;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getPondname() {
		return pondname;
	}
	public void setPondname(String pondname) {
		this.pondname = pondname;
	}
	public String getPondaddress() {
		return pondaddress;
	}
	public void setPondaddress(String pondaddress) {
		this.pondaddress = pondaddress;
	}
	public Date getPondtime() {
		return pondtime;
	}
	public void setPondtime(Date pondtime) {
		this.pondtime = pondtime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Set<Device> getDevice() {
		return device;
	}
	public void setDevice(Set<Device> device) {
		this.device = device;
	}

}
