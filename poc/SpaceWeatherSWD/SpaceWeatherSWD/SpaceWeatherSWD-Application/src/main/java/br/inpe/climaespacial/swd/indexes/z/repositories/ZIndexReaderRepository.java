package br.inpe.climaespacial.swd.indexes.z.repositories;

import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import java.time.ZonedDateTime;
import java.util.List;

public interface ZIndexReaderRepository {

    ZonedDateTime getNextHourToBeCalculated();
    
    List<ZIndex> listByPeriod(ZonedDateTime farthestFromNow, ZonedDateTime nearestFromNow);
    
}
