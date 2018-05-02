package br.inpe.climaespacial.swd.values.temperature.average.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.temperature.average.dtos.AverageTemperature;
import br.inpe.climaespacial.swd.values.temperature.average.entities.AverageTemperatureEntity;

public interface AverageTemperatureMapper {

	List<AverageTemperature> map(List<AverageTemperatureEntity> AverageTemperatureEntityList);

}
