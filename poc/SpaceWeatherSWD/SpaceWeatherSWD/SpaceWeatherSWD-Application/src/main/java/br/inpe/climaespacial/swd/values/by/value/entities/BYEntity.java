package br.inpe.climaespacial.swd.values.by.value.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class BYEntity {
	private ZonedDateTime timeTag;
	private Double by;
	
	public BYEntity(ZonedDateTime timeTag, Double by) {
		this.timeTag = timeTag;
		this.by = by;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getBY() {
		return by;
	}
	
}
