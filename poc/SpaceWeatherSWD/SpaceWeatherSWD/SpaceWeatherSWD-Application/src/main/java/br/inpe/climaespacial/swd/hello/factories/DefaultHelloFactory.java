package br.inpe.climaespacial.swd.hello.factories;

import br.inpe.climaespacial.swd.commons.factories.HelperFactory;
import br.inpe.climaespacial.swd.hello.dtos.Hello;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class DefaultHelloFactory implements HelloFactory {

    @Inject
    private HelperFactory<Hello> helloHelperFactory;

    @Override
    public Hello create() {
        return helloHelperFactory.create();
    }

}
