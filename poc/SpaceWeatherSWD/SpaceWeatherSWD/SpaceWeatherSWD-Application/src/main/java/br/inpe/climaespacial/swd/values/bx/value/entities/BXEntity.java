package br.inpe.climaespacial.swd.values.bx.value.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class BXEntity {
	private ZonedDateTime timeTag;
	private Double bx;
	
	public BXEntity(ZonedDateTime timeTag, Double bx) {
		this.timeTag = timeTag;
		this.bx = bx;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getBX() {
		return bx;
	}
	
}
