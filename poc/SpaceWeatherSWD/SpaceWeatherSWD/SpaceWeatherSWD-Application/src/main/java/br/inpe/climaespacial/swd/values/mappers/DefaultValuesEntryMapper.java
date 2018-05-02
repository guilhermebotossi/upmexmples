package br.inpe.climaespacial.swd.values.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.bt.average.dtos.AverageBT;
import br.inpe.climaespacial.swd.values.bt.value.dtos.BT;
import br.inpe.climaespacial.swd.values.bx.average.dtos.AverageBX;
import br.inpe.climaespacial.swd.values.bx.value.dtos.BX;
import br.inpe.climaespacial.swd.values.by.average.dtos.AverageBY;
import br.inpe.climaespacial.swd.values.by.value.dtos.BY;
import br.inpe.climaespacial.swd.values.bz.average.dtos.AverageBZ;
import br.inpe.climaespacial.swd.values.bz.value.dtos.BZ;
import br.inpe.climaespacial.swd.values.density.average.dtos.AverageDensity;
import br.inpe.climaespacial.swd.values.density.value.dtos.Density;
import br.inpe.climaespacial.swd.values.dpr.average.dtos.AverageDPR;
import br.inpe.climaespacial.swd.values.dpr.value.dtos.DPR;
import br.inpe.climaespacial.swd.values.dtos.BaseValue;
import br.inpe.climaespacial.swd.values.dtos.ValueEntry;
import br.inpe.climaespacial.swd.values.enums.ValuesEnum;
import br.inpe.climaespacial.swd.values.ey.average.dtos.AverageEY;
import br.inpe.climaespacial.swd.values.ey.value.dtos.EY;
import br.inpe.climaespacial.swd.values.factories.ValueEntryFactory;
import br.inpe.climaespacial.swd.values.rmp.average.dtos.AverageRMP;
import br.inpe.climaespacial.swd.values.rmp.value.dtos.RMP;
import br.inpe.climaespacial.swd.values.speed.average.dtos.AverageSpeed;
import br.inpe.climaespacial.swd.values.speed.value.dtos.Speed;
import br.inpe.climaespacial.swd.values.temperature.average.dtos.AverageTemperature;
import br.inpe.climaespacial.swd.values.temperature.value.dtos.Temperature;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultValuesEntryMapper implements ValuesEntryMapper {

    @Inject
    private ListFactory<ValueEntry> valueEntryListFactory;

    @Inject
    private ValueEntryFactory valueEntryFactory;

    @Override
    public List<ValueEntry> mapBT(List<BT> btList) {
        return map(btList, ValuesEnum.BT);
    }

    @Override
    public List<ValueEntry> mapBY(List<BY> byList) {
        return map(byList, ValuesEnum.BY);
    }

    @Override
    public List<ValueEntry> mapBX(List<BX> bxList) {
        return map(bxList, ValuesEnum.BX);
    }

    @Override
    public List<ValueEntry> mapBZ(List<BZ> bzList) {
        return map(bzList, ValuesEnum.BZ);
    }

    @Override
    public List<ValueEntry> mapDensity(List<Density> densityList) {
        return map(densityList, ValuesEnum.DENSITY);
    }

    @Override
    public List<ValueEntry> mapSpeed(List<Speed> speedList) {
        return map(speedList, ValuesEnum.SPEED);
    }

    @Override
    public List<ValueEntry> mapTemperature(List<Temperature> temperatureList) {
        return map(temperatureList, ValuesEnum.TEMPERATURE);
    }

    @Override
    public List<ValueEntry> mapEY(List<EY> eyList) {
        return map(eyList, ValuesEnum.EY);
    }

    @Override
    public List<ValueEntry> mapDPR(List<DPR> dprList) {
        return map(dprList, ValuesEnum.DPR);
    }

    @Override
    public List<ValueEntry> mapRMP(List<RMP> rmpList) {
        return map(rmpList, ValuesEnum.RMP);
    }

    @Override
    public List<ValueEntry> mapAverageBT(List<AverageBT> averageBTList) {
        return map(averageBTList, ValuesEnum.AVERAGE_BT);
    }

    @Override
    public List<ValueEntry> mapAverageBY(List<AverageBY> averageBYList) {
        return map(averageBYList, ValuesEnum.AVERAGE_BY);
    }

    @Override
    public List<ValueEntry> mapAverageBX(List<AverageBX> averageBXList) {
        return map(averageBXList, ValuesEnum.AVERAGE_BX);
    }

    @Override
    public List<ValueEntry> mapAverageBZ(List<AverageBZ> averageBZList) {
        return map(averageBZList, ValuesEnum.AVERAGE_BZ);
    }

    @Override
    public List<ValueEntry> mapAverageDensity(List<AverageDensity> averageDensityList) {
        return map(averageDensityList, ValuesEnum.AVERAGE_DENSITY);
    }

    @Override
    public List<ValueEntry> mapAverageSpeed(List<AverageSpeed> averageSpeedList) {
        return map(averageSpeedList, ValuesEnum.AVERAGE_SPEED);
    }

    @Override
    public List<ValueEntry> mapAverageTemperature(List<AverageTemperature> averageTemperatureList) {
        return map(averageTemperatureList, ValuesEnum.AVERAGE_TEMPERATURE);
    }

    @Override
    public List<ValueEntry> mapAverageEY(List<AverageEY> averageEYList) {
        return map(averageEYList, ValuesEnum.AVERAGE_EY);
    }

    @Override
    public List<ValueEntry> mapAverageDPR(List<AverageDPR> averageDPRList) {
        return map(averageDPRList, ValuesEnum.AVERAGE_DPR);
    }

    @Override
    public List<ValueEntry> mapAverageRMP(List<AverageRMP> averageRMPList) {
        return map(averageRMPList, ValuesEnum.AVERAGE_RMP);
    }

    private <T extends BaseValue> List<ValueEntry> map(List<T> baseValueList, ValuesEnum vEnum) {
        throwIfNull(baseValueList, vEnum.toString());

        List<ValueEntry> vel = valueEntryListFactory.create();

        baseValueList.forEach(bv -> {
            ValueEntry ve = valueEntryFactory.create();

            if (vEnum.getValor().equals("Valor")) {
                ve.setTimeTag(bv.getTimeTag());
            } else {
                ve.setTimeTag(bv.getTimeTag().plusMinutes(30));
            }
            ve.setValue(bv.getValue());
            vel.add(ve);
        });

        return vel;
    }

}
