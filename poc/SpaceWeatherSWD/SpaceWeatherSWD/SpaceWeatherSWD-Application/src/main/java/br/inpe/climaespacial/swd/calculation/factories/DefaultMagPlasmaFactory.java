package br.inpe.climaespacial.swd.calculation.factories;

import br.inpe.climaespacial.swd.calculation.dtos.MagPlasma;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultMagPlasmaFactory implements MagPlasmaFactory {

    @Inject
    private HelperFactory<MagPlasma> magPlasmaHelperFactory;

    @Override
    public MagPlasma create() {
        return magPlasmaHelperFactory.create();
    }

}
