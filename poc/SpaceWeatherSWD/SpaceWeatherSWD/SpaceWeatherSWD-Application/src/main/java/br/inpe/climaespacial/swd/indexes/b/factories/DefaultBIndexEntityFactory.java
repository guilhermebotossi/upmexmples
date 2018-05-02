package br.inpe.climaespacial.swd.indexes.b.factories;

import br.inpe.climaespacial.swd.commons.factories.DefaultEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.indexes.b.entities.BIndexEntity;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultBIndexEntityFactory extends DefaultEntityFactory<BIndexEntity> implements BIndexEntityFactory {

    @Inject
    private HelperFactory<BIndexEntity> bIndexEntityHelperFactory;

    @Override
    protected BIndexEntity doCreate() {
        return bIndexEntityHelperFactory.create();
    }

}
