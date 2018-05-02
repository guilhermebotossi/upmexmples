package br.inpe.climaespacial.swd.average.mappers;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import br.inpe.climaespacial.swd.average.factories.HourlyAverageEntityFactory;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultHourlyAverageEntityMapper implements HourlyAverageEntityMapper {

    @Inject
    private HourlyAverageEntityFactory hourlyAverageEntityFactory;

    @Override
    public HourlyAverageEntity map(HourlyAverage hourlyAverage) {

        throwIfNull(hourlyAverage, "hourlyAverage");

        HourlyAverageEntity cve = hourlyAverageToHourlyAverageEntity(hourlyAverage);

        return cve;
    }

    public HourlyAverageEntity hourlyAverageToHourlyAverageEntity(HourlyAverage cv) {
        HourlyAverageEntity hae = hourlyAverageEntityFactory.create();

        hae.setTimeTag(cv.getTimeTag());
        hae.setBxGsm(cv.getBxGsm());
        hae.setByGsm(cv.getByGsm());
        hae.setBzGsm(cv.getBzGsm());
        hae.setBt(cv.getBt());
        hae.setDensity(cv.getDensity());
        hae.setSpeed(cv.getSpeed());
        hae.setTemperature(cv.getTemperature());
        hae.setEy(cv.getEy());
        hae.setDpr(cv.getDpr());
        hae.setRmp(cv.getRmp());

        return hae;
    }

}
