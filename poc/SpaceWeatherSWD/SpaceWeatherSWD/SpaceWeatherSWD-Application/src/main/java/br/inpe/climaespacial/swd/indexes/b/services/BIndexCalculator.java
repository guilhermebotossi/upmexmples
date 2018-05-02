package br.inpe.climaespacial.swd.indexes.b.services;

import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;

public interface BIndexCalculator {

    BIndex calculate(HourlyAverage hourlyAverage);
    
}
