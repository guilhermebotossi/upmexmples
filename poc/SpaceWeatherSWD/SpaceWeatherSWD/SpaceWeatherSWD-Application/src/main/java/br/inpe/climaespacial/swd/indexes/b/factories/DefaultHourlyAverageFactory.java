package br.inpe.climaespacial.swd.indexes.b.factories;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultHourlyAverageFactory implements HourlyAverageFactory {

    @Inject
    private HelperFactory<HourlyAverage> hourlyAverageHelperFactory;

    @Override
    public HourlyAverage create() {
        return hourlyAverageHelperFactory.create();
    }

}
