package br.inpe.climaespacial.swd.values.dtos;

import java.time.ZonedDateTime;

public class BaseValue {

	private ZonedDateTime timeTag;
	private Double value;
	
	
	public ZonedDateTime getTimeTag() {
		return timeTag;
	}
	public void setTimeTag(ZonedDateTime timeTag) {
		this.timeTag = timeTag;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	
}
