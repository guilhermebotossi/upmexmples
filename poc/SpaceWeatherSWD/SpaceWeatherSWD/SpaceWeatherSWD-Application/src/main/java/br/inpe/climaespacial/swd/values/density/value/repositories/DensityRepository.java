package br.inpe.climaespacial.swd.values.density.value.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.values.density.value.dtos.Density;

public interface DensityRepository {

	List<Density> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime);

}
