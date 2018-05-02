package br.inpe.climaespacial.swd.average.factories;

import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import br.inpe.climaespacial.swd.commons.factories.DefaultEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultHourlyAverageEntityFactory extends DefaultEntityFactory<HourlyAverageEntity> implements HourlyAverageEntityFactory {

    @Inject
    private HelperFactory<HourlyAverageEntity> hourlyAverageEntityHelperFactory;

    @Override
    protected HourlyAverageEntity doCreate() {
        return hourlyAverageEntityHelperFactory.create();
    }

}
