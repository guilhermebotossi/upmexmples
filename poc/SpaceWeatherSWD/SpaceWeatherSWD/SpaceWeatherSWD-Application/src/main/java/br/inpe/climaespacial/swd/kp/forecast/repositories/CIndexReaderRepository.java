package br.inpe.climaespacial.swd.kp.forecast.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;

public interface CIndexReaderRepository {
    
    List<CIndex> getLastCIndexes(ZonedDateTime timeTag);
    
}
