package br.inpe.climaespacial.swd.values.bt.average.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.bt.average.dtos.AverageBT;

public interface AverageBTRepository {

	List<AverageBT> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
