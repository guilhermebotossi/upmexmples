package br.inpe.climaespacial.swd.commons.factories;

import br.inpe.climaespacial.swd.commons.entities.TestEntity;
import javax.inject.Inject;

public class DefaultTestEntityFactory extends DefaultEntityFactory<TestEntity> implements TestEntityFactory {

    @Inject
    private HelperFactory<TestEntity> testEntityHelperFactory;

    @Override
    protected TestEntity doCreate() {
        return testEntityHelperFactory.create();
    }

}
