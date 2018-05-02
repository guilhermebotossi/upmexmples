package br.inpe.climaespacial.swd.indexes.z.repositories;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import java.util.List;

public interface ZIndexHourlyAverageReaderRepository {

    List<HourlyAverage> getHourlyAverages();

}
