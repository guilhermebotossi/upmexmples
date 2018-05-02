package br.inpe.climaespacial.swd.values.dpr.value.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.dpr.value.dtos.DPR;

public interface DPRRepository {

	List<DPR> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
