package br.inpe.climaespacial.swd.values.speed.value.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.values.speed.value.dtos.Speed;
import br.inpe.climaespacial.swd.values.speed.value.entities.SpeedEntity;

public interface SpeedMapper {

	List<Speed> map(List<SpeedEntity> speedEntityList);

}
