package br.inpe.climaespacial.swd.values.bx.average.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class AverageBXEntity {
	private ZonedDateTime timeTag;
	private Double averageBX;
	
	public AverageBXEntity(ZonedDateTime timeTag, Double averageBX) {
		this.timeTag = timeTag;
		this.averageBX = averageBX;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getAverageBX() {
		return averageBX;
	}
	
}
