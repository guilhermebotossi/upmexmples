package br.inpe.climaespacial.swd.values.bz.average.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class AverageBZEntity {
	private ZonedDateTime timeTag;
	private Double averageBZ;
	
	public AverageBZEntity(ZonedDateTime timeTag, Double averageBZ) {
		this.timeTag = timeTag;
		this.averageBZ = averageBZ;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getAverageBZ() {
		return averageBZ;
	}
	
}
