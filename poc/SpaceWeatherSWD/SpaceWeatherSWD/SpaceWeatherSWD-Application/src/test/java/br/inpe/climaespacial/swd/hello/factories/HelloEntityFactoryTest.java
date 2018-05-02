package br.inpe.climaespacial.swd.hello.factories;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.UUIDProvider;
import br.inpe.climaespacial.swd.hello.entities.HelloEntity;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultHelloEntityFactory.class, HelloEntity.class})
public class HelloEntityFactoryTest {

    @Produces
    @Mock
    private UUIDProvider uuidProvider;

    @Produces
    @Mock
    private DateTimeProvider dateTimeProvider;

    @Inject
    private HelloEntityFactory helloEntityFactory;

    @Test
    public void create_called_returnsHelloEntity() {
        HelloEntity he = helloEntityFactory.create();

        assertThat(he, instanceOf(HelloEntity.class));
    }

}
