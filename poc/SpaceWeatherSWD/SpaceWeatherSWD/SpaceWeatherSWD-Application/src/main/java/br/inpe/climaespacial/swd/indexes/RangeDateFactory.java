package br.inpe.climaespacial.swd.indexes;

import java.time.ZonedDateTime;

public interface RangeDateFactory {
    
    RangeDate create(ZonedDateTime firstAcquisitionDate, ZonedDateTime lastAcquisitionDate);

}
