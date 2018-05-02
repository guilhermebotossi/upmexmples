package br.inpe.climaespacial.swd.kp.factories;

import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.kp.dtos.KP;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultKPFactory implements KPFactory {
    
    @Inject
    private HelperFactory<KP> kpHelperFactory;

    @Override
    public KP create() {
        return kpHelperFactory.create();
    }

}
