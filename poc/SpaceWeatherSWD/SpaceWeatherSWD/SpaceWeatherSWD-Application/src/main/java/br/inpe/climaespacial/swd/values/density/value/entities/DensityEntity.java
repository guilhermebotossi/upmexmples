package br.inpe.climaespacial.swd.values.density.value.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class DensityEntity {
	private ZonedDateTime timeTag;
	private Double density;
	
	public DensityEntity(ZonedDateTime timeTag, Double density) {
		this.timeTag = timeTag;
		this.density = density;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getDensity() {
		return density;
	}
	
}
