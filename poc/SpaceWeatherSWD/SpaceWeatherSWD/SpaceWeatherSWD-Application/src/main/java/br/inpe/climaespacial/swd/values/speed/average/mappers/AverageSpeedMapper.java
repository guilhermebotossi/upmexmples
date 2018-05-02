package br.inpe.climaespacial.swd.values.speed.average.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.speed.average.dtos.AverageSpeed;
import br.inpe.climaespacial.swd.values.speed.average.entities.AverageSpeedEntity;

public interface AverageSpeedMapper {

	List<AverageSpeed> map(List<AverageSpeedEntity> AverageSpeedEntityList);

}
