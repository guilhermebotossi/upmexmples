package br.inpe.climaespacial.swd.indexes.c.factories;

import br.inpe.climaespacial.swd.commons.factories.DefaultEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultCIndexEntityFactory extends DefaultEntityFactory<CIndexEntity> implements CIndexEntityFactory {

    @Inject
    private HelperFactory<CIndexEntity> cIndexEntityHeplerFactory;

    @Override
    protected CIndexEntity doCreate() {
        return cIndexEntityHeplerFactory.create();
    }

}
