package br.inpe.climaespacial.swd.indexes.v.factories;

import br.inpe.climaespacial.swd.commons.factories.DefaultEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.indexes.v.entities.VIndexEntity;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultVIndexEntityFactory extends DefaultEntityFactory<VIndexEntity> implements VIndexEntityFactory {

    @Inject
    private HelperFactory<VIndexEntity> vIndexEntityHelperFactory;

    @Override
    protected VIndexEntity doCreate() {
        return vIndexEntityHelperFactory.create();
    }

}
