package br.inpe.climaespacial.swd.average.mappers;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;

public interface HourlyAverageEntityMapper {

    HourlyAverageEntity map(HourlyAverage hourlyAverage);

}
