package br.inpe.climaespacial.swd.values.rmp.value.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class RMPEntity {
	private ZonedDateTime timeTag;
	private Double rmp;
	
	public RMPEntity(ZonedDateTime timeTag, Double rmp) {
		this.timeTag = timeTag;
		this.rmp = rmp;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getRMP() {
		return rmp;
	}
	
}
