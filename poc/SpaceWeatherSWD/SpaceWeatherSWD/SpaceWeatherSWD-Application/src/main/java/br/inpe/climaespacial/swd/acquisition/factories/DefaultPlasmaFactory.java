package br.inpe.climaespacial.swd.acquisition.factories;

import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultPlasmaFactory implements PlasmaFactory {

    @Inject
    private HelperFactory<Plasma> plasmaHelperFactory;

    @Override
    public Plasma create() {
        return plasmaHelperFactory.create();
    }

}
