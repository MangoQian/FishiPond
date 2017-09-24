package com.example.app3.data_bean;

import java.util.Date;
import java.util.Set;


public class Device {
	private int did;
	private String devicecode;

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	private String devicename;
	private String deviceaddress;
	private Integer devicetype;
	public Integer getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(Integer devicetype) {
		this.devicetype = devicetype;
	}
	private Date time;
	private Pond pond;
	private Set<Aerator> aerator;
	private Set<Feeding> feeding;
	private Set<Dissolveoxygen> dissolveoxygen;
	private Set<Control_log> control_log;
	
	public Set<Aerator> getAerator() {
		return aerator;
	}
	public void setAerator(Set<Aerator> aerator) {
		this.aerator = aerator;
	}
	public Set<Feeding> getFeeding() {
		return feeding;
	}
	public void setFeeding(Set<Feeding> feeding) {
		this.feeding = feeding;
	}
	public Set<Dissolveoxygen> getDissolveoxygen() {
		return dissolveoxygen;
	}
	public void setDissolveoxygen(Set<Dissolveoxygen> dissolveoxygen) {
		this.dissolveoxygen = dissolveoxygen;
	}
	public Set<Control_log> getControl_log() {
		return control_log;
	}
	public void setControl_log(Set<Control_log> control_log) {
		this.control_log = control_log;
	}
	
	public String getDevicecode() {
		return devicecode;
	}
	public void setDevicecode(String devicecode) {
		this.devicecode = devicecode;
	}
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	public String getDeviceaddress() {
		return deviceaddress;
	}
	public void setDeviceaddress(String deviceaddress) {
		this.deviceaddress = deviceaddress;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Pond getPond() {
		return pond;
	}
	public void setPond(Pond pond) {
		this.pond = pond;
	}

	
}
