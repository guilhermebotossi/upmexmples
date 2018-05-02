package br.inpe.climaespacial.swd.values.density.average.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.density.average.dtos.AverageDensity;

public interface AverageDensityRepository {

	List<AverageDensity> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
