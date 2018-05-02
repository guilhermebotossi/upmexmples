package br.inpe.climaespacial.swd.values.by.value.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.by.value.dtos.BY;

public interface BYRepository {

	List<BY> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
