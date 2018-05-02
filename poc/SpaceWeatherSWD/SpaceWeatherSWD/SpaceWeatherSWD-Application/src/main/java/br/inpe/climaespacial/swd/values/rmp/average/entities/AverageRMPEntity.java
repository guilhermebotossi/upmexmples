package br.inpe.climaespacial.swd.values.rmp.average.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class AverageRMPEntity {
	private ZonedDateTime timeTag;
	private Double averageRMP;
	
	public AverageRMPEntity(ZonedDateTime timeTag, Double averageRMP) {
		this.timeTag = timeTag;
		this.averageRMP = averageRMP;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getAverageRMP() {
		return averageRMP;
	}
	
}
