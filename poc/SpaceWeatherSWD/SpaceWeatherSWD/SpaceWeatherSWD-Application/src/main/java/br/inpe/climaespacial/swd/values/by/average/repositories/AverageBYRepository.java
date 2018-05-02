package br.inpe.climaespacial.swd.values.by.average.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.by.average.dtos.AverageBY;

public interface AverageBYRepository {

	List<AverageBY> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
