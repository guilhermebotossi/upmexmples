package br.inpe.climaespacial.swd.values.density.average.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class AverageDensityEntity {
	private ZonedDateTime timeTag;
	private Double averageDensity;
	
	public AverageDensityEntity(ZonedDateTime timeTag, Double averageDensity) {
		this.timeTag = timeTag;
		this.averageDensity = averageDensity;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getAverageDensity() {
		return averageDensity;
	}
	
}
