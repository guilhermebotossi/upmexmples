package br.inpe.climaespacial.swd.values.temperature.value.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class TemperatureEntity {
	private ZonedDateTime timeTag;
	private Double temperature;
	
	public TemperatureEntity(ZonedDateTime timeTag, Double temperature) {
		this.timeTag = timeTag;
		this.temperature = temperature;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getTemperature() {
		return temperature;
	}
	
}
