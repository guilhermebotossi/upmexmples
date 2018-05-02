package br.inpe.climaespacial.swd.values.ey.value.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class EYEntity {
	private ZonedDateTime timeTag;
	private Double ey;
	
	public EYEntity(ZonedDateTime timeTag, Double ey) {
		this.timeTag = timeTag;
		this.ey = ey;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getEY() {
		return ey;
	}
	
}
