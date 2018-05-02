package br.inpe.climaespacial.swd.acquisition.factories;

import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.calculation.factories.PlasmaEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.DefaultEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultPlasmaEntityFactory extends DefaultEntityFactory<PlasmaEntity> implements PlasmaEntityFactory {

    @Inject
    private HelperFactory<PlasmaEntity> plasmaEntityHelperFactory;

    @Override
    protected PlasmaEntity doCreate() {
        return plasmaEntityHelperFactory.create();
    }

}
