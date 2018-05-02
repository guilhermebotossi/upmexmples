package br.inpe.climaespacial.swd.indexes.z.factories;

import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultZIndexFactory.class, ZIndex.class})
public class ZIndexFactoryTest {

    @Inject
    private ZIndexFactory zIndexFactory;

    @Test
    public void create_called_returnsBIndex() {
        ZIndex zi = zIndexFactory.create();

        assertNotNull(zi);
        assertEquals(ZIndex.class, zi.getClass());
    }

}
