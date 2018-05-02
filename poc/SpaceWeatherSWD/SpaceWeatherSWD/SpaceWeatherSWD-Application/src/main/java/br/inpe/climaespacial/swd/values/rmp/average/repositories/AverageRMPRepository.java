package br.inpe.climaespacial.swd.values.rmp.average.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.rmp.average.dtos.AverageRMP;

public interface AverageRMPRepository {

	List<AverageRMP> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
