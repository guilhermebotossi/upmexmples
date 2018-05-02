package br.inpe.climaespacial.swd.values.temperature.value.factories;

import java.time.ZonedDateTime;

import br.inpe.climaespacial.swd.values.temperature.value.dtos.Temperature;

public interface TemperatureFactory {

	Temperature create(ZonedDateTime timeTag, Double temperature);


}
