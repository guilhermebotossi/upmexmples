package br.inpe.climaespacial.swd.values.bz.value.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.bz.value.dtos.BZ;

public interface BZRepository {

	List<BZ> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
