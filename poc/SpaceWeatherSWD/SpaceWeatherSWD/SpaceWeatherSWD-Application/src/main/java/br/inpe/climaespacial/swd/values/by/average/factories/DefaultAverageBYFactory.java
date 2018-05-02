package br.inpe.climaespacial.swd.values.by.average.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.by.average.dtos.AverageBY;

@Dependent
public class DefaultAverageBYFactory implements AverageBYFactory {

	@Override
	public AverageBY create(ZonedDateTime timeTag, Double value) {
		AverageBY averageBY = new AverageBY();
		averageBY.setTimeTag(timeTag);
		averageBY.setValue(value);
		return averageBY;
	}

}
