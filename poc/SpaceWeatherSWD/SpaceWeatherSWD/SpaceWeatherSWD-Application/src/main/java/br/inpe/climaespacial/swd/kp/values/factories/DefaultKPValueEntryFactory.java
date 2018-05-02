package br.inpe.climaespacial.swd.kp.values.factories;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.kp.values.dtos.KPValueEntry;

@Dependent
public class DefaultKPValueEntryFactory implements KPValueEntryFactory {
    
    @Inject
    private HelperFactory<KPValueEntry> kpForecastEntryHelperFactory;

    @Override
    public KPValueEntry create() {
        return kpForecastEntryHelperFactory.create();
    }

}
