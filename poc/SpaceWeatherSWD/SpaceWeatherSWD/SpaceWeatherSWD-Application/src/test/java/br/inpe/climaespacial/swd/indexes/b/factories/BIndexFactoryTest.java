package br.inpe.climaespacial.swd.indexes.b.factories;

import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultBIndexFactory.class, BIndex.class})
public class BIndexFactoryTest {

    @Inject
    private BIndexFactory bIndexFactory;

    @Test
    public void create_called_returnsBIndex() {
        BIndex bi = bIndexFactory.create();

        assertNotNull(bi);
        assertEquals(BIndex.class, bi.getClass());
    }

}
