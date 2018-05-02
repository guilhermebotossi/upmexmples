package br.inpe.climaespacial.swd.values.speed.average.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.speed.average.dtos.AverageSpeed;

@Dependent
public class DefaultAverageSpeedFactory implements AverageSpeedFactory {

	@Override
	public AverageSpeed create(ZonedDateTime timeTag, Double value) {
		AverageSpeed AverageSpeed = new AverageSpeed();
		AverageSpeed.setTimeTag(timeTag);
		AverageSpeed.setValue(value);
		return AverageSpeed;
	}

}
