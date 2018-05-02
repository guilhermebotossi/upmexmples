package br.inpe.climaespacial.swd.indexes.v.repositories;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;

import java.util.List;

public interface VIndexHourlyAverageReaderRepository {
    
    List<HourlyAverage> getHourlyAverages();
    
}
