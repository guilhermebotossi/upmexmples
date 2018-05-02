package br.inpe.climaespacial.swd.indexes.c.repositories;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;

public interface CIndexHourlyAverageReaderRepository {

    HourlyAverage getHourlyAverage();
    
}
