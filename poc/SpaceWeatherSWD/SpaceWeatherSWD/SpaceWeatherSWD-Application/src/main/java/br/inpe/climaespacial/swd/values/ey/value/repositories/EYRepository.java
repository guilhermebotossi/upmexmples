package br.inpe.climaespacial.swd.values.ey.value.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.ey.value.dtos.EY;

public interface EYRepository {

	List<EY> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
