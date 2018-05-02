package br.inpe.climaespacial.swd.indexes.v.helpers;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.commons.helpers.BaseDataFillerHelper;
import br.inpe.climaespacial.swd.indexes.b.factories.HourlyAverageFactory;

@Dependent
public class DefaultHourlyAverageDataFillerHelper extends BaseDataFillerHelper<HourlyAverage> implements HourlyAverageDataFillerHelper  {
    
    @Inject
    private HourlyAverageFactory hourlyAverageFactory;

    @Override
    protected HourlyAverage create() {
        return hourlyAverageFactory.create();
    }
    
//    @Override
//    public HourlyAverage createTimeTagable() {
//
//        return hourlyAverageFactory.create();
//    }

}
