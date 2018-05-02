package br.inpe.climaespacial.swd.values.rmp.value.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.rmp.value.dtos.RMP;

public interface RMPRepository {

	List<RMP> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
