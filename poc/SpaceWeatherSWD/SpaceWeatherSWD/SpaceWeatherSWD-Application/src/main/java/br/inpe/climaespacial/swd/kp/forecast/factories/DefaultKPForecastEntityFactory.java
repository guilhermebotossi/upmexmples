package br.inpe.climaespacial.swd.kp.forecast.factories;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.DefaultEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.kp.forecast.entities.KPForecastEntity;

@Dependent
public class DefaultKPForecastEntityFactory extends DefaultEntityFactory<KPForecastEntity> implements KPForecastEntityFactory {
    
    @Inject
    private HelperFactory<KPForecastEntity> kpForecastEntityHelperFactory;

    @Override
    protected KPForecastEntity doCreate() {
        return kpForecastEntityHelperFactory.create();
    }
}
