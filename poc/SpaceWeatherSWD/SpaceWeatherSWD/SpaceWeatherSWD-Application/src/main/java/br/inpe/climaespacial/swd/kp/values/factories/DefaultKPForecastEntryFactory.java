package br.inpe.climaespacial.swd.kp.values.factories;

import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.kp.values.dtos.KPForecastEntry;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultKPForecastEntryFactory implements KPForecastEntryFactory {
    
    @Inject
    private HelperFactory<KPForecastEntry> kpForecastEntryHelperFactory;

    @Override
    public KPForecastEntry create() {
        return kpForecastEntryHelperFactory.create();
    }

}
