package br.inpe.climaespacial.swd.indexes.b.mappers;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.indexes.b.factories.HourlyAverageFactory;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultHourlyAverageMapper implements HourlyAverageMapper {

    @Inject
    private HourlyAverageFactory hourlyAverageFactory;

    @Inject
    private ListFactory<HourlyAverage> hourlyAverageListFactory;

    @Override
    public HourlyAverage map(HourlyAverageEntity hourlyAverageEntity) {
        if (hourlyAverageEntity == null) {
            return null;
        }

        HourlyAverage ha = hourlyAverageFactory.create();

        ha.setTimeTag(hourlyAverageEntity.getTimeTag());
        ha.setBxGsm(hourlyAverageEntity.getBxGsm());
        ha.setByGsm(hourlyAverageEntity.getByGsm());
        ha.setBzGsm(hourlyAverageEntity.getBzGsm());
        ha.setBt(hourlyAverageEntity.getBt());
        ha.setDensity(hourlyAverageEntity.getDensity());
        ha.setTemperature(hourlyAverageEntity.getTemperature());
        ha.setSpeed(hourlyAverageEntity.getSpeed());
        ha.setEy(hourlyAverageEntity.getEy());
        ha.setDpr(hourlyAverageEntity.getDpr());
        ha.setRmp(hourlyAverageEntity.getRmp());

        return ha;
    }

    @Override
    public List<HourlyAverage> mapAll(List<HourlyAverageEntity> hourlyAverageEntityList) {

        List<HourlyAverage> hal = hourlyAverageListFactory.create();

        for (HourlyAverageEntity hae : hourlyAverageEntityList) {
            hal.add(map(hae));
        }

        return hal;
    }
}
