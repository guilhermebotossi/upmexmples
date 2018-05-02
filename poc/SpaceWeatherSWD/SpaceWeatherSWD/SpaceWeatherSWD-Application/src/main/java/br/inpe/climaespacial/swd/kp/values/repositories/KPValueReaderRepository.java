package br.inpe.climaespacial.swd.kp.values.repositories;

import java.util.List;

import br.inpe.climaespacial.swd.kp.dtos.KPValue;

public interface KPValueReaderRepository {

    List<KPValue> getLastKPIndexes();
}
