package br.inpe.climaespacial.swd.indexes.z.factories;

import br.inpe.climaespacial.swd.commons.factories.DefaultEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.indexes.z.entities.ZIndexEntity;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultZIndexEntityFactory extends DefaultEntityFactory<ZIndexEntity> implements ZIndexEntityFactory {

    @Inject
    private HelperFactory<ZIndexEntity> zIndexEntityHelperFactory;

    @Override
    protected ZIndexEntity doCreate() {
        return zIndexEntityHelperFactory.create();
    }
	
}