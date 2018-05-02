package br.inpe.climaespacial.swd.values.temperature.average.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class AverageTemperatureEntity {
	private ZonedDateTime timeTag;
	private Double AverageTemperature;
	
	public AverageTemperatureEntity(ZonedDateTime timeTag, Double AverageTemperature) {
		this.timeTag = timeTag;
		this.AverageTemperature = AverageTemperature;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getAverageTemperature() {
		return AverageTemperature;
	}
	
}
