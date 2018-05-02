
package br.inpe.climaespacial.swd.indexes;

import java.time.ZonedDateTime;

public interface FirstHourlyAverageToCalculateIndexesReaderRepository {
    
    ZonedDateTime getFirstHour();
    
}
