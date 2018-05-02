package br.inpe.climaespacial.swd.home.services;

import br.inpe.climaespacial.swd.home.dtos.IndexData;
import br.inpe.climaespacial.swd.indexes.RangeDate;
import java.time.ZonedDateTime;

public interface IndexesService {

    RangeDate getRangeDate();

    IndexData getIndexData(ZonedDateTime farthestFromNow, ZonedDateTime nearestFromNow);

}
