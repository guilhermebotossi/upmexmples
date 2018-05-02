package br.inpe.climaespacial.swd.values.bx.average.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.bx.average.dtos.AverageBX;

public interface AverageBXRepository {

	List<AverageBX> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
