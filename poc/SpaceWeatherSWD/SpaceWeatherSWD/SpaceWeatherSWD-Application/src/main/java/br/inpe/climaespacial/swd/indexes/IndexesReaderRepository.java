package br.inpe.climaespacial.swd.indexes;

import java.time.ZonedDateTime;

public interface IndexesReaderRepository {
    
    ZonedDateTime lastIndexesDate();
    
}
