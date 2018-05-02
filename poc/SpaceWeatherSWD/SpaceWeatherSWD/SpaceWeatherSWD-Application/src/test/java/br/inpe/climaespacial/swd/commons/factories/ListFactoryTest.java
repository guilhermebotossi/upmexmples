package br.inpe.climaespacial.swd.commons.factories;

import br.inpe.climaespacial.swd.commons.services.TestService;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultListFactory.class})
public class ListFactoryTest {

    @Inject
    private ListFactory<TestService> testServiceListFactory;

    @Test
    public void create_called_returnsInstanceOfList() {
        List<TestService> tsl = testServiceListFactory.create();

        assertNotNull(tsl);
        assertEquals(ArrayList.class, tsl.getClass());
    }
}
