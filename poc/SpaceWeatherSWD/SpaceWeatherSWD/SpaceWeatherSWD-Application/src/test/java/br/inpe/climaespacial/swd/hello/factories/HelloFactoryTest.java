package br.inpe.climaespacial.swd.hello.factories;

import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.hello.dtos.Hello;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultHelloFactory.class, Hello.class})
public class HelloFactoryTest {

    @Inject
    private HelloFactory helloFactory;

    @Test
    public void create_called_returnsHello() {

        Hello h = helloFactory.create();

        assertNotNull(h);
        assertEquals(Hello.class, h.getClass());
    }
}
