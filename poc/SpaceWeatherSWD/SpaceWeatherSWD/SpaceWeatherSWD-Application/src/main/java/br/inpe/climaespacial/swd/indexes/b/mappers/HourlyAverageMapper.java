package br.inpe.climaespacial.swd.indexes.b.mappers;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import java.util.List;

public interface HourlyAverageMapper {

    HourlyAverage map(HourlyAverageEntity hourlyAverageEntity);

    List<HourlyAverage> mapAll(List<HourlyAverageEntity> hourlyAverageEntityList);
    
}
