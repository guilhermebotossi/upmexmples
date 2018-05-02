package br.inpe.climaespacial.swd.indexes.b.factories;

import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultBIndexFactory implements BIndexFactory {

    @Inject
    private HelperFactory<BIndex> bIndexHelperFactory;

    @Override
    public BIndex create() {
        return bIndexHelperFactory.create();
    }
	
}