package br.inpe.climaespacial.swd.commons.factories;

import br.inpe.climaespacial.swd.commons.services.DefaultTestService;
import br.inpe.climaespacial.swd.commons.services.TestService;
import javax.inject.Inject;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultTestServiceFactory.class, DefaultTestService.class})
public class FactoryTest {

    @Inject
    private TestServiceFactory testServiceFactory;

    @Test
    public void create_called_returnsInstance() {
        TestService testService = testServiceFactory.create();

        assertNotNull(testService);
        assertThat(testService, instanceOf(TestService.class));
    }
}
