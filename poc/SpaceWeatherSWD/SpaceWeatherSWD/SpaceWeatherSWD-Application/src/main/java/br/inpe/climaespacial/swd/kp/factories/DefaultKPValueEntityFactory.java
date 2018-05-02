package br.inpe.climaespacial.swd.kp.factories;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.inpe.climaespacial.swd.commons.factories.DefaultEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;

@Dependent
public class DefaultKPValueEntityFactory extends DefaultEntityFactory<KPValueEntity> implements KPValueEntityFactory {

    @Inject
    private HelperFactory<KPValueEntity> kpValueEntityFactory;

    @Override
    protected KPValueEntity doCreate() {
        return kpValueEntityFactory.create();
    }

}
