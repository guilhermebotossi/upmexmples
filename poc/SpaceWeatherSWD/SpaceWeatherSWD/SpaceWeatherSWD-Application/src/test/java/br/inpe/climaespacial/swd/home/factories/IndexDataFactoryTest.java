package br.inpe.climaespacial.swd.home.factories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.home.dtos.IndexData;
import br.inpe.climaespacial.swd.home.dtos.IndexEntry;
import java.util.List;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultIndexDataFactory.class
})
public class IndexDataFactoryTest {

    @Inject
    private IndexDataFactory indexDataFactory;

    @Test
    public void create_called_returnsIndexData() {
        
        List<IndexEntry> bIndexEntryList = mockList(IndexEntry.class);
        List<IndexEntry> cIndexEntryList = mockList(IndexEntry.class);
        List<IndexEntry> vIndexEntryList = mockList(IndexEntry.class);
        List<IndexEntry> zIndexEntryList = mockList(IndexEntry.class);
        
        IndexData id = indexDataFactory.create(bIndexEntryList, cIndexEntryList, vIndexEntryList, zIndexEntryList);
        
        assertNotNull(id);
        assertEquals(IndexData.class, id.getClass());
        assertSame(bIndexEntryList, id.getbEntries());
        assertSame(cIndexEntryList, id.getcEntries());
        assertSame(vIndexEntryList, id.getvEntries());
        assertSame(zIndexEntryList, id.getzEntries());
    }       

}
