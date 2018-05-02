package br.inpe.climaespacial.swd.values.temperature.value.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.temperature.value.dtos.Temperature;
import br.inpe.climaespacial.swd.values.temperature.value.entities.TemperatureEntity;

public interface TemperatureMapper {

	List<Temperature> map(List<TemperatureEntity> temperatureEntityList);

}
