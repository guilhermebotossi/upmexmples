package br.inpe.climaespacial.swd.acquisition.dtos;

import java.time.ZonedDateTime;
import javax.enterprise.context.Dependent;

@Dependent
public class Mag {

	private ZonedDateTime timeTag;
	private Double bxGsm;
	private Double byGsm;
	private Double bzGsm;
	private Double latGsm;
	private Double lonGsm;
	private Double bt;

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public void setTimeTag(ZonedDateTime timeTag) {
		this.timeTag = timeTag;
	}

	public Double getBxGsm() {
		return bxGsm;
	}

	public void setBxGsm(Double bxGsm) {
		this.bxGsm = bxGsm;
	}

	public Double getByGsm() {
		return byGsm;
	}

	public void setByGsm(Double byGsm) {
		this.byGsm = byGsm;
	}

	public Double getBzGsm() {
		return bzGsm;
	}

	public void setBzGsm(Double bzGsm) {
		this.bzGsm = bzGsm;
	}

	public Double getLatGsm() {
		return latGsm;
	}

	public void setLatGsm(Double latGsm) {
		this.latGsm = latGsm;
	}

	public Double getLonGsm() {
		return lonGsm;
	}

	public void setLonGsm(Double lonGsm) {
		this.lonGsm = lonGsm;
	}

	public Double getBt() {
		return bt;
	}

	public void setBt(Double bt) {
		this.bt = bt;
	}

	@Override
	public String toString() {
		return "Mag [timeTag=" + timeTag + ", bxGsm=" + bxGsm + ", byGsm=" + byGsm + ", bzGsm=" + bzGsm + ", latGsm="
				+ latGsm + ", lonGsm=" + lonGsm + ", bt=" + bt + "]";
	}
}
