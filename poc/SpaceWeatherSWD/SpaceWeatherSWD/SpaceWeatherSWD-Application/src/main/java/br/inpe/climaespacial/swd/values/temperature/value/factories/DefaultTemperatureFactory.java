package br.inpe.climaespacial.swd.values.temperature.value.factories;

import java.time.ZonedDateTime;

import javax.enterprise.context.Dependent;

import br.inpe.climaespacial.swd.values.temperature.value.dtos.Temperature;

@Dependent
public class DefaultTemperatureFactory implements TemperatureFactory {

	@Override
	public Temperature create(ZonedDateTime timeTag, Double value) {
		Temperature temperature = new Temperature();
		temperature.setTimeTag(timeTag);
		temperature.setValue(value);
		return temperature;
	}

}
