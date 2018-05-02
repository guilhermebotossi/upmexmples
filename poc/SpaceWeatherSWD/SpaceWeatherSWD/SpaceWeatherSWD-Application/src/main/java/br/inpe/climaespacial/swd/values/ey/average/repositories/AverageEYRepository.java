package br.inpe.climaespacial.swd.values.ey.average.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.ey.average.dtos.AverageEY;

public interface AverageEYRepository {

	List<AverageEY> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
