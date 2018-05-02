package br.inpe.climaespacial.swd.values.temperature.value.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.temperature.value.dtos.Temperature;

public interface TemperatureRepository {

	List<Temperature> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
