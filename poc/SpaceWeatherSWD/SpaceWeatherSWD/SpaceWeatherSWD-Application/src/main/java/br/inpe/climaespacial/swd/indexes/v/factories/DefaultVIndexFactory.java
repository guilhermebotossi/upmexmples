package br.inpe.climaespacial.swd.indexes.v.factories;

import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultVIndexFactory implements VIndexFactory {

    @Inject
    private HelperFactory<VIndex> vIndexHelperFactory;

    @Override
    public VIndex create() {
        return vIndexHelperFactory.create();
    }

}
