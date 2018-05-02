package br.inpe.climaespacial.swd.values.temperature.average.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.temperature.average.dtos.AverageTemperature;

@Dependent
public class DefaultAverageTemperatureFactory implements AverageTemperatureFactory {

	@Override
	public AverageTemperature create(ZonedDateTime timeTag, Double value) {
		AverageTemperature AverageTemperature = new AverageTemperature();
		AverageTemperature.setTimeTag(timeTag);
		AverageTemperature.setValue(value);
		return AverageTemperature;
	}

}
