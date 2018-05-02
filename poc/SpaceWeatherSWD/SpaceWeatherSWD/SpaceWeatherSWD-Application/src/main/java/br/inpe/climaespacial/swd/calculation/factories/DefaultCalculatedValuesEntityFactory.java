package br.inpe.climaespacial.swd.calculation.factories;

import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;
import br.inpe.climaespacial.swd.commons.factories.DefaultEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultCalculatedValuesEntityFactory extends DefaultEntityFactory<CalculatedValuesEntity>
        implements CalculatedValuesEntityFactory {

    @Inject
    private HelperFactory<CalculatedValuesEntity> calculatedValuesEntityHelperFactory;

    @Override
    protected CalculatedValuesEntity doCreate() {
        return calculatedValuesEntityHelperFactory.create();
    }

}
