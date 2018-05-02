package br.inpe.climaespacial.swd.indexes.b.repositories;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;

public interface BIndexHourlyAverageReaderRepository {

    HourlyAverage getHourlyAverage();
    
}
