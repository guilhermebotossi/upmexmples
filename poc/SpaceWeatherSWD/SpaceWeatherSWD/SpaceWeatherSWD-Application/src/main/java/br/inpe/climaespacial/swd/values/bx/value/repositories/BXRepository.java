package br.inpe.climaespacial.swd.values.bx.value.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.bx.value.dtos.BX;

public interface BXRepository {

	List<BX> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
