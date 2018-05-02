package br.inpe.climaespacial.swd.values.factories;

import java.util.List;
import java.util.Map;

import br.inpe.climaespacial.swd.values.dtos.ValueEntry;
import br.inpe.climaespacial.swd.values.dtos.ValuesData;
import br.inpe.climaespacial.swd.values.enums.ValuesEnum;

public interface ValuesDataFactory {

	ValuesData create(Map<ValuesEnum, List<ValueEntry>> valuesMap);

}
