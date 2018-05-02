package br.inpe.climaespacial.swd.values.dpr.average.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class AverageDPREntity {
	private ZonedDateTime timeTag;
	private Double averageDPR;
	
	public AverageDPREntity(ZonedDateTime timeTag, Double averageDPR) {
		this.timeTag = timeTag;
		this.averageDPR = averageDPR;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getAverageDPR() {
		return averageDPR;
	}
	
}
