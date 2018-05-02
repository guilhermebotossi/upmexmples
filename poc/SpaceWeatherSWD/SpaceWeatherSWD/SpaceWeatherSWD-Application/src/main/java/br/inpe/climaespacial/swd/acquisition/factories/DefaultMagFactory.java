package br.inpe.climaespacial.swd.acquisition.factories;

import br.inpe.climaespacial.swd.acquisition.dtos.Mag;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultMagFactory implements MagFactory {

    @Inject
    private HelperFactory<Mag> magHelperFactory;

    @Override
    public Mag create() {
        return magHelperFactory.create();
    }

}
