package br.inpe.climaespacial.swd.calculation.factories;

import br.inpe.climaespacial.swd.calculation.dtos.MagPlasmaCalculated;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultMagPlasmaCalculatedFactory implements MagPlasmaCalculatedFactory {

    @Inject
    private HelperFactory<MagPlasmaCalculated> magPlasmaCalculatedHelperFactory;

    @Override
    public MagPlasmaCalculated create() {
        return magPlasmaCalculatedHelperFactory.create();
    }

}
