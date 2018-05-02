package br.inpe.climaespacial.swd.values.ey.average.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class AverageEYEntity {
	private ZonedDateTime timeTag;
	private Double averageEY;
	
	public AverageEYEntity(ZonedDateTime timeTag, Double averageEY) {
		this.timeTag = timeTag;
		this.averageEY = averageEY;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getAverageEY() {
		return averageEY;
	}
	
}
