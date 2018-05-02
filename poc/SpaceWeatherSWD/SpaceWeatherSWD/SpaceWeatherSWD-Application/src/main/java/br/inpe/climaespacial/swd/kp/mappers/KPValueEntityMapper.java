package br.inpe.climaespacial.swd.kp.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.entities.KPEntity;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;

public interface KPValueEntityMapper {

    List<KPValueEntity> map(List<KPValue> kpvl, KPEntity kpEntity);

}
