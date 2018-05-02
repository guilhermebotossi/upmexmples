package br.inpe.climaespacial.swd.values.by.average.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class AverageBYEntity {
	private ZonedDateTime timeTag;
	private Double averageBY;
	
	public AverageBYEntity(ZonedDateTime timeTag, Double averageBY) {
		this.timeTag = timeTag;
		this.averageBY = averageBY;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getAverageBY() {
		return averageBY;
	}
	
}
