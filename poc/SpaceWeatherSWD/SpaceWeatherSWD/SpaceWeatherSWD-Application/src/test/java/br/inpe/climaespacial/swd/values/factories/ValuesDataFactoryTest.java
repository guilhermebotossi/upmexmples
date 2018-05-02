package br.inpe.climaespacial.swd.values.factories;

import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_BT;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_BX;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_BY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_BZ;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_DENSITY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_DPR;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_EY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_RMP;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_SPEED;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_TEMPERATURE;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.BT;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.BX;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.BY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.BZ;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.DENSITY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.DPR;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.EY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.RMP;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.SPEED;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.TEMPERATURE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.values.dtos.ValueEntry;
import br.inpe.climaespacial.swd.values.dtos.ValuesData;
import br.inpe.climaespacial.swd.values.enums.ValuesEnum;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultValuesDataFactory.class})
public class ValuesDataFactoryTest {

    @Inject
    private ValuesDataFactory valuesDataFactory;

    @Test
    public void create_calledWithNull_throws() {
        RuntimeException re = null;
        try {
            valuesDataFactory.create(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Parametro \"valuesMap\" null/empty.", re.getMessage());
    }

    @Test
    public void create_calledWithEmptyMap_throws() {
        RuntimeException re = null;
        try {
            valuesDataFactory.create(new HashMap<ValuesEnum, List<ValueEntry>>());
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Parametro \"valuesMap\" null/empty.", re.getMessage());
    }

    @Test
    public void create_calledWithPartialKeys_throws() {
        RuntimeException re = null;
        try {
            Map<ValuesEnum, List<ValueEntry>> map = new HashMap<ValuesEnum, List<ValueEntry>>();
            map.put(BT, new ArrayList<ValueEntry>());
            map.put(BY, new ArrayList<ValueEntry>());
            map.put(BX, new ArrayList<ValueEntry>());
            valuesDataFactory.create(map);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Parametro \"valuesMap\" deve possuir todas as entradas do Enum ValuesEnum.", re.getMessage());
    }

    @Test
    public void create_calledWithAllKeysButNullValue_throws() {
        RuntimeException re = null;
        try {
            Map<ValuesEnum, List<ValueEntry>> map = new HashMap<ValuesEnum, List<ValueEntry>>();
            map.put(BT, new ArrayList<ValueEntry>());
            map.put(BY, new ArrayList<ValueEntry>());
            map.put(BX, new ArrayList<ValueEntry>());
            map.put(BZ, new ArrayList<ValueEntry>());
            map.put(DENSITY, new ArrayList<ValueEntry>());
            map.put(SPEED, new ArrayList<ValueEntry>());
            map.put(TEMPERATURE, new ArrayList<ValueEntry>());
            map.put(EY, new ArrayList<ValueEntry>());
            map.put(DPR, new ArrayList<ValueEntry>());
            map.put(RMP, new ArrayList<ValueEntry>());
            map.put(AVERAGE_BT, new ArrayList<ValueEntry>());
            map.put(AVERAGE_BY, new ArrayList<ValueEntry>());
            map.put(AVERAGE_BX, new ArrayList<ValueEntry>());
            map.put(AVERAGE_BZ, null);
            map.put(AVERAGE_DENSITY, new ArrayList<ValueEntry>());
            map.put(AVERAGE_SPEED, new ArrayList<ValueEntry>());
            map.put(AVERAGE_TEMPERATURE, new ArrayList<ValueEntry>());
            map.put(AVERAGE_EY, new ArrayList<ValueEntry>());
            map.put(AVERAGE_DPR, new ArrayList<ValueEntry>());
            map.put(AVERAGE_RMP, new ArrayList<ValueEntry>());

            valuesDataFactory.create(map);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Parametro \"valuesMap[" + ValuesEnum.AVERAGE_BZ.toString() + "]\" inexistente ou null.",
                re.getMessage());
    }

    @Test
    public void create_calledWithAllKeys_succeeds() {
        Map<ValuesEnum, List<ValueEntry>> map = new HashMap<ValuesEnum, List<ValueEntry>>();
        map.put(BT, new ArrayList<ValueEntry>());
        map.put(BY, new ArrayList<ValueEntry>());
        map.put(BX, new ArrayList<ValueEntry>());
        map.put(BZ, new ArrayList<ValueEntry>());
        map.put(DENSITY, new ArrayList<ValueEntry>());
        map.put(SPEED, new ArrayList<ValueEntry>());
        map.put(TEMPERATURE, new ArrayList<ValueEntry>());
        map.put(EY, new ArrayList<ValueEntry>());
        map.put(DPR, new ArrayList<ValueEntry>());
        map.put(RMP, new ArrayList<ValueEntry>());
        map.put(AVERAGE_BT, new ArrayList<ValueEntry>());
        map.put(AVERAGE_BY, new ArrayList<ValueEntry>());
        map.put(AVERAGE_BX, new ArrayList<ValueEntry>());
        map.put(AVERAGE_BZ, new ArrayList<ValueEntry>());
        map.put(AVERAGE_DENSITY, new ArrayList<ValueEntry>());
        map.put(AVERAGE_SPEED, new ArrayList<ValueEntry>());
        map.put(AVERAGE_TEMPERATURE, new ArrayList<ValueEntry>());
        map.put(AVERAGE_EY, new ArrayList<ValueEntry>());
        map.put(AVERAGE_DPR, new ArrayList<ValueEntry>());
        map.put(AVERAGE_RMP, new ArrayList<ValueEntry>());

        ValuesData vd = valuesDataFactory.create(map);

        assertNotNull(vd);

        assertNotNull(vd.getBTList());
        assertSame(map.get(BT), vd.getBTList());
        assertNotNull(vd.getBYList());
        assertSame(map.get(BY), vd.getBYList());
        assertNotNull(vd.getBXList());
        assertSame(map.get(BX), vd.getBXList());
        assertNotNull(vd.getBZList());
        assertSame(map.get(BZ), vd.getBZList());
        assertNotNull(vd.getDensityList());
        assertSame(map.get(DENSITY), vd.getDensityList());
        assertNotNull(vd.getSpeedList());
        assertSame(map.get(SPEED), vd.getSpeedList());
        assertNotNull(vd.getTemperatureList());
        assertSame(map.get(TEMPERATURE), vd.getTemperatureList());
        assertNotNull(vd.getEYList());
        assertSame(map.get(EY), vd.getEYList());
        assertNotNull(vd.getDPRList());
        assertSame(map.get(DPR), vd.getDPRList());
        assertNotNull(vd.getRMPList());
        assertSame(map.get(RMP), vd.getRMPList());

        assertNotNull(vd.getAverageBTList());
        assertSame(map.get(AVERAGE_BT), vd.getAverageBTList());
        assertNotNull(vd.getAverageBYList());
        assertSame(map.get(AVERAGE_BY), vd.getAverageBYList());
        assertNotNull(vd.getAverageBXList());
        assertSame(map.get(AVERAGE_BX), vd.getAverageBXList());
        assertNotNull(vd.getAverageBZList());
        assertSame(map.get(AVERAGE_BZ), vd.getAverageBZList());
        assertNotNull(vd.getAverageDensityList());
        assertSame(map.get(AVERAGE_DENSITY), vd.getAverageDensityList());
        assertNotNull(vd.getAverageSpeedList());
        assertSame(map.get(AVERAGE_SPEED), vd.getAverageSpeedList());
        assertNotNull(vd.getAverageTemperatureList());
        assertSame(map.get(AVERAGE_TEMPERATURE), vd.getAverageTemperatureList());
        assertNotNull(vd.getAverageEYList());
        assertSame(map.get(AVERAGE_EY), vd.getAverageEYList());
        assertNotNull(vd.getAverageDPRList());
        assertSame(map.get(AVERAGE_DPR), vd.getAverageDPRList());
        assertNotNull(vd.getAverageRMPList());
        assertSame(map.get(AVERAGE_RMP), vd.getAverageRMPList());

    }
}
