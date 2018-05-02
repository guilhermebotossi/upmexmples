package br.inpe.climaespacial.swd.indexes.b.repositories;

import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import java.time.ZonedDateTime;
import java.util.List;

public interface BIndexReaderRepository {

    ZonedDateTime getNextHourToBeCalculated();
    
    List<BIndex> listByPeriod(ZonedDateTime farthestFromNow, ZonedDateTime nearestFromNow);
    
}
