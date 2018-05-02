package br.inpe.climaespacial.swd.values.dpr.average.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.dpr.average.dtos.AverageDPR;

public interface AverageDPRRepository {

	List<AverageDPR> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
