package br.inpe.climaespacial.swd.hello.factories;

import br.inpe.climaespacial.swd.commons.factories.DefaultEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.hello.entities.HelloEntity;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultHelloEntityFactory extends DefaultEntityFactory<HelloEntity> implements HelloEntityFactory {

    @Inject
    private HelperFactory<HelloEntity> helloEntityHelperFactory;

    @Override
    protected HelloEntity doCreate() {
        return helloEntityHelperFactory.create();
    }

}
