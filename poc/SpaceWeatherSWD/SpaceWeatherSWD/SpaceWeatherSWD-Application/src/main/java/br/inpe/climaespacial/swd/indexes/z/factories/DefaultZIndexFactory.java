package br.inpe.climaespacial.swd.indexes.z.factories;

import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultZIndexFactory implements ZIndexFactory {

    @Inject
    private HelperFactory<ZIndex> zIndexHelperFactory;

    @Override
    public ZIndex create() {
        return zIndexHelperFactory.create();
    }

}
