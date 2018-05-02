package br.inpe.climaespacial.swd.kp.values.mappers;

import java.util.List;

import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.values.dtos.KPValueEntry;

public interface KPValueEntryMapper {

    List<KPValueEntry> mapKPValue(List<KPValue> kpValueList);


}
