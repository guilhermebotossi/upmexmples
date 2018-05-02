package br.inpe.climaespacial.swd.values.bt.average.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class AverageBTEntity {
	private ZonedDateTime timeTag;
	private Double averageBT;
	
	public AverageBTEntity(ZonedDateTime timeTag, Double averageBT) {
		this.timeTag = timeTag;
		this.averageBT = averageBT;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getAverageBT() {
		return averageBT;
	}
	
}
