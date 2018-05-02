package br.inpe.climaespacial.swd.indexes.c.factories;

import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultCIndexFactory implements CIndexFactory {

    @Inject
    private HelperFactory<CIndex> cIndexHelperFactory;

    @Override
    public CIndex create() {
        return cIndexHelperFactory.create();
    }

}
