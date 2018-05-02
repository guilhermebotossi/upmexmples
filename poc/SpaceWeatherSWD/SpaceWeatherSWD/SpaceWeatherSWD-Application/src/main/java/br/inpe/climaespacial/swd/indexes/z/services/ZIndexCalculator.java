package br.inpe.climaespacial.swd.indexes.z.services;

import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import java.util.List;

public interface ZIndexCalculator {

    ZIndex calculate(List<HourlyAverage> hourlyAverageList);

}
