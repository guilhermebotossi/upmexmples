package br.inpe.climaespacial.swd.indexes.v.factories;

import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultVIndexFactory.class, VIndex.class})
public class VIndexFactoryTest {

    @Inject
    private VIndexFactory vIndexFactory;

    @Test
    public void create_called_returnsVIndex() {
        VIndex vi = vIndexFactory.create();

        assertNotNull(vi);
        assertEquals(VIndex.class, vi.getClass());
    }

}
