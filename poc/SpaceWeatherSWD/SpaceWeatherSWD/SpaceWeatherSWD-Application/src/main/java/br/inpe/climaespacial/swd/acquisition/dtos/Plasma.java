package br.inpe.climaespacial.swd.acquisition.dtos;

import java.time.ZonedDateTime;
import javax.enterprise.context.Dependent;

@Dependent
public class Plasma {

	private ZonedDateTime timeTag;
	private Double density;
	private Double speed;
	private Double temperature;


	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public void setTimeTag(ZonedDateTime timeTag) {
		this.timeTag = timeTag;
	}

	public Double getDensity() {
		return density;
	}

	public void setDensity(Double density) {
		this.density = density;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public Double getTemperature() {
		return temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	@Override
	public String toString() {
		return "Plasma [timeTag=" + timeTag + ", density=" + density + ", speed=" + speed + ", temperature="
				+ temperature + "]";
	}
}
