package br.poc.mapper.dto;

import java.time.LocalDateTime;

public class MagDTO {
	
	private LocalDateTime timeTag;
	private double bxGsm;
	private double byGsm;
	private double bzGsm;
	private double latGsm;
	private double lonGsm;
	private double bt;
	
	
	public LocalDateTime getTimeTag() {
		return timeTag;
	}
	public void setTimeTag(LocalDateTime timeTag) {
		this.timeTag = timeTag;
	}
	public double getBxGsm() {
		return bxGsm;
	}
	public void setBxGsm(double bxGsm) {
		this.bxGsm = bxGsm;
	}
	public double getByGsm() {
		return byGsm;
	}
	public void setByGsm(double byGsm) {
		this.byGsm = byGsm;
	}
	public double getBzGsm() {
		return bzGsm;
	}
	public void setBzGsm(double bzGsm) {
		this.bzGsm = bzGsm;
	}
	public double getLatGsm() {
		return latGsm;
	}
	public void setLatGsm(double latGsm) {
		this.latGsm = latGsm;
	}
	public double getLonGsm() {
		return lonGsm;
	}
	public void setLonGsm(double lonGsm) {
		this.lonGsm = lonGsm;
	}
	public double getBt() {
		return bt;
	}
	public void setBt(double bt) {
		this.bt = bt;
	}
	


}
