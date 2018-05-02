package br.inpe.climaespacial.swd.average.repositories;

import java.time.ZonedDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import br.inpe.climaespacial.swd.calculation.dtos.MagPlasmaCalculated;

public interface MagPlasmaCalculatedReaderRepository {

	SimpleEntry<ZonedDateTime, List<MagPlasmaCalculated>> list();

}
