package br.inpe.climaespacial.swd.values.factories;

import br.inpe.climaespacial.swd.commons.factories.Factory;
import br.inpe.climaespacial.swd.values.dtos.ValueEntry;
import br.inpe.climaespacial.swd.values.dtos.ValuesData;
import br.inpe.climaespacial.swd.values.enums.ValuesEnum;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.*;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultValuesDataFactory implements ValuesDataFactory {

    @Inject
    private Factory<ValuesData> valuesDataFactory;

    @Override
    public ValuesData create(Map<ValuesEnum, List<ValueEntry>> valuesMap) {
        if (valuesMap == null || valuesMap.isEmpty()) {
            throw new RuntimeException("Parametro \"valuesMap\" null/empty.");
        }

        int size = ValuesEnum.values().length;

        if (valuesMap.size() < size) {
            throw new RuntimeException("Parametro \"valuesMap\" deve possuir todas as entradas do Enum ValuesEnum.");
        }

        for (ValuesEnum ve : ValuesEnum.values()) {
            if (!valuesMap.containsKey(ve) || valuesMap.get(ve) == null) {
                throw new RuntimeException("Parametro \"valuesMap[" + ve + "]\" inexistente ou null.");
            }
        }

        ValuesData vd = valuesDataFactory.create();

        vd.setBTList(valuesMap.get(BT));
        vd.setBYList(valuesMap.get(BY));
        vd.setBXList(valuesMap.get(BX));
        vd.setBZList(valuesMap.get(BZ));
        vd.setDensityList(valuesMap.get(DENSITY));
        vd.setSpeed(valuesMap.get(SPEED));
        vd.setTemperatureList(valuesMap.get(TEMPERATURE));
        vd.setEYList(valuesMap.get(EY));
        vd.setDPRList(valuesMap.get(DPR));
        vd.setRMPList(valuesMap.get(RMP));

        vd.setAverageBTList(valuesMap.get(AVERAGE_BT));
        vd.setAverageBYList(valuesMap.get(AVERAGE_BY));
        vd.setAverageBXList(valuesMap.get(AVERAGE_BX));
        vd.setAverageBZList(valuesMap.get(AVERAGE_BZ));
        vd.setAverageDensityList(valuesMap.get(AVERAGE_DENSITY));
        vd.setAverageSpeedList(valuesMap.get(AVERAGE_SPEED));
        vd.setAverageTemperatureList(valuesMap.get(AVERAGE_TEMPERATURE));
        vd.setAverageEYList(valuesMap.get(AVERAGE_EY));
        vd.setAverageDPRList(valuesMap.get(AVERAGE_DPR));
        vd.setAverageRMPList(valuesMap.get(AVERAGE_RMP));

        return vd;
    }
}
