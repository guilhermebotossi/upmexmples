package br.inpe.climaespacial.swd.acquisition.factories;

import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.commons.factories.DefaultEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultMagEntityFactory extends DefaultEntityFactory<MagEntity> implements MagEntityFactory {

    @Inject
    private HelperFactory<MagEntity> magEntityHelperFactory;

    @Override
    protected MagEntity doCreate() {
        return magEntityHelperFactory.create();
    }

}
