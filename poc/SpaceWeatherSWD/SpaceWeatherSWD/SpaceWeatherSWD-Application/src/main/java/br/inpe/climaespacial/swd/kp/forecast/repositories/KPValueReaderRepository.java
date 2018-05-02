package br.inpe.climaespacial.swd.kp.forecast.repositories;

import br.inpe.climaespacial.swd.kp.dtos.KPValue;

public interface KPValueReaderRepository {

    KPValue getLastKPValue();

}
