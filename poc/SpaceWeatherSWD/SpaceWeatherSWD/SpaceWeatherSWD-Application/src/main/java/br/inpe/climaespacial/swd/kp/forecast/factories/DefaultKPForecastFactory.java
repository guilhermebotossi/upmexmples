package br.inpe.climaespacial.swd.kp.forecast.factories;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;

@Dependent
public class DefaultKPForecastFactory implements KPForecastFactory {

    @Inject
    private HelperFactory<KPForecast> kpForecastHelperFactory;

    @Override
    public KPForecast create() {
        return kpForecastHelperFactory.create();
    }

}
