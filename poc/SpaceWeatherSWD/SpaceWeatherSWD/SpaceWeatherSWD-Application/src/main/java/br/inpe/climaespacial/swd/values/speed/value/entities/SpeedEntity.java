package br.inpe.climaespacial.swd.values.speed.value.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class SpeedEntity {
	private ZonedDateTime timeTag;
	private Double speed;
	
	public SpeedEntity(ZonedDateTime timeTag, Double speed) {
		this.timeTag = timeTag;
		this.speed = speed;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getSpeed() {
		return speed;
	}
	
}
