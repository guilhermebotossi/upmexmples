package br.inpe.climaespacial.swd.values.speed.average.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class AverageSpeedEntity {
	private ZonedDateTime timeTag;
	private Double AverageSpeed;
	
	public AverageSpeedEntity(ZonedDateTime timeTag, Double AverageSpeed) {
		this.timeTag = timeTag;
		this.AverageSpeed = AverageSpeed;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getAverageSpeed() {
		return AverageSpeed;
	}
	
}
