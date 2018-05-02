package br.inpe.climaespacial.swd.values.bt.value.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.bt.value.dtos.BT;

public interface BTRepository {

	List<BT> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
