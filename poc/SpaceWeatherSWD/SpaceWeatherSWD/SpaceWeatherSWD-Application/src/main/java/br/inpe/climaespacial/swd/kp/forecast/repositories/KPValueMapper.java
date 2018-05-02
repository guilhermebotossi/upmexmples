package br.inpe.climaespacial.swd.kp.forecast.repositories;

import java.util.List;

import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;

public interface KPValueMapper {

    KPValue map(KPValueEntity kpValueEntity);

    List<KPValue> map(List<KPValueEntity> kpValueEntityList);

}
