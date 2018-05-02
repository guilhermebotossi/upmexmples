package br.inpe.climaespacial.swd.indexes.v.services;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import java.util.List;

public interface VIndexCalculator {
    
    VIndex calculate(List<HourlyAverage> hourlyAverageList, List<VIndex> vIndexList);
    
}
