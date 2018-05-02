package br.inpe.climaespacial.swd.values.temperature.average.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.temperature.average.dtos.AverageTemperature;

public interface AverageTemperatureFactory {

	AverageTemperature create(ZonedDateTime timeTag, Double AverageTemperature);


}
