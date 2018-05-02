package br.inpe.climaespacial.swd.kp.factories;

import br.inpe.climaespacial.swd.commons.factories.DefaultEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.kp.entities.KPEntity;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultKPEntityFactory extends DefaultEntityFactory<KPEntity> implements KPEntityFactory {
    
    @Inject
    private HelperFactory<KPEntity> kpEntityHelperFactory;
    
    @Override
    public KPEntity doCreate() {
        return kpEntityHelperFactory.create();
    }

}
