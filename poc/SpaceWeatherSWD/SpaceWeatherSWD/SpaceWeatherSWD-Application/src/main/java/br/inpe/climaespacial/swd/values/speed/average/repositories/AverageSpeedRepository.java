package br.inpe.climaespacial.swd.values.speed.average.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.speed.average.dtos.AverageSpeed;

public interface AverageSpeedRepository {

	List<AverageSpeed> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
