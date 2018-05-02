package br.inpe.climaespacial.swd.values.bz.average.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.bz.average.dtos.AverageBZ;

public interface AverageBZRepository {

	List<AverageBZ> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
