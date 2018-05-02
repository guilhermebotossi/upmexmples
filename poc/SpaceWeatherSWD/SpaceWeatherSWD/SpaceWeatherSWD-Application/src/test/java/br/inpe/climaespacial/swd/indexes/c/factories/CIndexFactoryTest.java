package br.inpe.climaespacial.swd.indexes.c.factories;

import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultCIndexFactory.class, CIndex.class})
public class CIndexFactoryTest {

    @Inject
    private CIndexFactory cIndexFactory;

    @Test
    public void create_called_returnsBIndex() {
        CIndex ci = cIndexFactory.create();

        assertNotNull(ci);
        assertEquals(CIndex.class, ci.getClass());
    }

}
