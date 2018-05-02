package br.inpe.climaespacial.swd.home.dtos;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import java.util.List;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(IndexData.class)
public class IndexDataTest {

    @Inject
    private IndexData indexData;

    @Test
    public void bEntriesTest() {
        List<IndexEntry> iel = mockList(IndexEntry.class);

        indexData.setbEntries(iel);

        assertSame(iel, indexData.getbEntries());
    }

    @Test
    public void cEntriesTest() {
        List<IndexEntry> iel = mockList(IndexEntry.class);

        indexData.setcEntries(iel);

        assertSame(iel, indexData.getcEntries());
    }

    @Test
    public void vEntriesTest() {
        List<IndexEntry> iel = mockList(IndexEntry.class);

        indexData.setvEntries(iel);

        assertSame(iel, indexData.getvEntries());
    }

    @Test
    public void zEntriesTest() {
        List<IndexEntry> iel = mockList(IndexEntry.class);

        indexData.setzEntries(iel);

        assertSame(iel, indexData.getzEntries());
    }

}
