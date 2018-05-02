package br.inpe.climaespacial.swd.indexes.v.repositories;

import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import java.time.ZonedDateTime;
import java.util.List;

public interface VIndexReaderRepository {

    ZonedDateTime getNextHourToBeCalculated();
    
    List<VIndex> listByPeriod(ZonedDateTime farthestFromNow, ZonedDateTime nearestFromNow);

    List<VIndex> getLastVIndexesFromDateTime(ZonedDateTime zdt);
    
}
