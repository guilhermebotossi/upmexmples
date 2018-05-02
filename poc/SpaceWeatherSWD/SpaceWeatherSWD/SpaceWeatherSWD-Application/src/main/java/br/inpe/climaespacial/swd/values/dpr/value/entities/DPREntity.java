package br.inpe.climaespacial.swd.values.dpr.value.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class DPREntity {
	private ZonedDateTime timeTag;
	private Double dpr;
	
	public DPREntity(ZonedDateTime timeTag, Double dpr) {
		this.timeTag = timeTag;
		this.dpr = dpr;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getDPR() {
		return dpr;
	}
	
}
