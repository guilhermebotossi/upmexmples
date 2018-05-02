package br.inpe.climaespacial.swd.commons.factories;

import br.inpe.climaespacial.swd.commons.services.TestService;
import javax.inject.Inject;

public class DefaultTestServiceFactory implements TestServiceFactory {

    @Inject
    private HelperFactory<TestService> testServiceHelperFactory;

    @Override
    public TestService create() {
        return testServiceHelperFactory.create();
    }

}
