package br.inpe.climaespacial.swd.indexes.c.repositories;

import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import java.time.ZonedDateTime;
import java.util.List;

public interface CIndexReaderRepository {
	
    ZonedDateTime getNextHourToBeCalculated();
    
    List<CIndex> listByPeriod(ZonedDateTime farthestFromNow, ZonedDateTime nearestFromNow);
}
