package br.inpe.climaespacial.swd.values.speed.value.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.speed.value.dtos.Speed;

public interface SpeedRepository {

	List<Speed> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
