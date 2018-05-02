package br.inpe.climaespacial.swd.home.factories;

import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.home.dtos.IndexEntry;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultIndexEntryFactory.class
})
public class IndexEntryFactoryTest {

    @Inject
    private IndexEntryFactory indexEntryFactory;

    @Test
    public void create_called_returnsIndexData() {

        IndexEntry ie = indexEntryFactory.create();

        assertNotNull(ie);
        assertEquals(IndexEntry.class, ie.getClass());
    }

}
