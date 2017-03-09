package br.poc.mapper.dto;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PlasmaDTO {
	
	private LocalDateTime timeTag;
	private double density;
	private double speed;
	private double temperature;
	
	public LocalDateTime getTimeTag() {
		return timeTag;
	}
	public void setTimeTag(LocalDateTime timeTag) {
		this.timeTag = timeTag;
	}
	public double getDensity() {
		return density;
	}
	public void setDensity(double density) {
		this.density = density;
	}
	public double getSpeed() {
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	
	@Override
	public String toString() {
		return "PlasmaDTO [timeTag=" + timeTag + ", density=" + density + ", speed=" + speed + ", temperature="
				+ temperature + "]";
	}
}
