package br.inpe.climaespacial.swd.values.temperature.average.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.temperature.average.dtos.AverageTemperature;

public interface AverageTemperatureRepository {

	List<AverageTemperature> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
