package br.inpe.climaespacial.swd.values.bz.value.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class BZEntity {
	private ZonedDateTime timeTag;
	private Double bz;
	
	public BZEntity(ZonedDateTime timeTag, Double bz) {
		this.timeTag = timeTag;
		this.bz = bz;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getBZ() {
		return bz;
	}
	
}
