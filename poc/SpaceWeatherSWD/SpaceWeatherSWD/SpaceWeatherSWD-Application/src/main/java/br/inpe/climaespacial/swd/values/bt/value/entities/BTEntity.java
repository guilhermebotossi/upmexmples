package br.inpe.climaespacial.swd.values.bt.value.entities;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

@Dependent
public class BTEntity {
	private ZonedDateTime timeTag;
	private Double bt;
	
	public BTEntity(ZonedDateTime timeTag, Double bt) {
		this.timeTag = timeTag;
		this.bt = bt;
	}

	public ZonedDateTime getTimeTag() {
		return timeTag;
	}

	public Double getBT() {
		return bt;
	}
	
}
