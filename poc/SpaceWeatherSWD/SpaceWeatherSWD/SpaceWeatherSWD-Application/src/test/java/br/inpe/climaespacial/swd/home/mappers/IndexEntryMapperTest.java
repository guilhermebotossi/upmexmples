package br.inpe.climaespacial.swd.home.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.home.dtos.IndexEntry;
import br.inpe.climaespacial.swd.home.factories.IndexEntryFactory;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    DefaultIndexEntryMapper.class
})
public class IndexEntryMapperTest {

    private static final double DELTA = 0.001;

    private static final double VALUE_1 = 1.0;

    private static final double VALUE_2 = 2.0;

    @Produces
    @Mock
    private ListFactory<IndexEntry> indexEntryListFactory;

    @Produces
    @Mock
    private IndexEntryFactory indexEntryFactory;

    @Inject
    private IndexEntryMapper indexEntryMapper;

    @Test
    public void mapB_calledWithNullList_throws() {
        RuntimeException re = null;

        try {
            indexEntryMapper.mapB(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"bIndexList\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapB_called_returnIndexEntryListOfB() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        BIndex bi1 = new BIndex();
        bi1.setTimeTag(timeTag);
        bi1.setPreValue(VALUE_1);
        bi1.setPostValue(VALUE_2);

        BIndex bi2 = new BIndex();
        bi2.setTimeTag(timeTag);
        bi2.setPreValue(VALUE_1);
        bi2.setPostValue(VALUE_2);

        when(indexEntryListFactory.create()).thenReturn(new ArrayList<>());
        when(indexEntryFactory.create()).thenReturn(new IndexEntry());

        List<BIndex> bIndexList = Arrays.asList(bi1, bi2);

        List<IndexEntry> biel = indexEntryMapper.mapB(bIndexList);

        assertNotNull(biel);
        assertEquals(2, biel.size());

        IndexEntry ie1 = biel.get(0);
        assertEquals(bi1.getTimeTag(), ie1.getTimeTag());
        assertEquals(VALUE_1, ie1.getPreValue(), DELTA);
        assertEquals(VALUE_2, ie1.getPostValue(), DELTA);

        IndexEntry ie2 = biel.get(1);
        assertEquals(bi2.getTimeTag(), ie2.getTimeTag());
        assertEquals(VALUE_1, ie2.getPreValue(), DELTA);
        assertEquals(VALUE_2, ie2.getPostValue(), DELTA);
    }

    @Test
    public void mapC_calledWithNullList_throws() {
        RuntimeException re = null;

        try {
            indexEntryMapper.mapC(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"cIndexList\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapC_called_returnIndexEntryListOfC() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        CIndex ci1 = new CIndex();
        ci1.setTimeTag(timeTag);
        ci1.setPreValue(VALUE_1);
        ci1.setPostValue(VALUE_2);

        CIndex ci2 = new CIndex();
        ci2.setTimeTag(timeTag);
        ci2.setPreValue(VALUE_1);
        ci2.setPostValue(VALUE_2);

        when(indexEntryListFactory.create()).thenReturn(new ArrayList<>());
        when(indexEntryFactory.create()).thenReturn(new IndexEntry());

        List<CIndex> cIndexList = Arrays.asList(ci1, ci2);

        List<IndexEntry> ciel = indexEntryMapper.mapC(cIndexList);

        assertNotNull(ciel);
        assertEquals(2, ciel.size());

        IndexEntry ie1 = ciel.get(0);
        assertEquals(ci1.getTimeTag(), ie1.getTimeTag());
        assertEquals(VALUE_1, ie1.getPreValue(), DELTA);
        assertEquals(VALUE_2, ie1.getPostValue(), DELTA);

        IndexEntry ie2 = ciel.get(1);
        assertEquals(ci2.getTimeTag(), ie2.getTimeTag());
        assertEquals(VALUE_1, ie2.getPreValue(), DELTA);
        assertEquals(VALUE_2, ie2.getPostValue(), DELTA);
    }

    @Test
    public void mapV_calledWithNullList_throws() {
        RuntimeException re = null;

        try {
            indexEntryMapper.mapV(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"vIndexList\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapV_called_returnIndexEntryListOfV() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        VIndex vi1 = new VIndex();
        vi1.setTimeTag(timeTag);
        vi1.setPreValue(VALUE_1);
        vi1.setPostValue(VALUE_2);

        VIndex vi2 = new VIndex();
        vi2.setTimeTag(timeTag);
        vi2.setPreValue(VALUE_1);
        vi2.setPostValue(VALUE_2);

        when(indexEntryListFactory.create()).thenReturn(new ArrayList<>());
        when(indexEntryFactory.create()).thenReturn(new IndexEntry());

        List<VIndex> vIndexList = Arrays.asList(vi1, vi2);

        List<IndexEntry> viel = indexEntryMapper.mapV(vIndexList);

        assertNotNull(viel);
        assertEquals(2, viel.size());

        IndexEntry ie1 = viel.get(0);
        assertEquals(vi1.getTimeTag(), ie1.getTimeTag());
        assertEquals(VALUE_1, ie1.getPreValue(), DELTA);
        assertEquals(VALUE_2, ie1.getPostValue(), DELTA);

        IndexEntry ie2 = viel.get(1);
        assertEquals(vi2.getTimeTag(), ie2.getTimeTag());
        assertEquals(VALUE_1, ie2.getPreValue(), DELTA);
        assertEquals(VALUE_2, ie2.getPostValue(), DELTA);
    }

    @Test
    public void mapZ_calledWithNullList_throws() {
        RuntimeException re = null;

        try {
            indexEntryMapper.mapZ(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"zIndexList\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapZ_called_returnIndexEntryListOfZ() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        ZIndex zi1 = new ZIndex();
        zi1.setTimeTag(timeTag);
        zi1.setPreValue(VALUE_1);
        zi1.setPostValue(VALUE_2);

        ZIndex zi2 = new ZIndex();
        zi2.setTimeTag(timeTag);
        zi2.setPreValue(VALUE_1);
        zi2.setPostValue(VALUE_2);

        when(indexEntryListFactory.create()).thenReturn(new ArrayList<>());
        when(indexEntryFactory.create()).thenReturn(new IndexEntry());

        List<ZIndex> zIndexList = Arrays.asList(zi1, zi2);

        List<IndexEntry> ziel = indexEntryMapper.mapZ(zIndexList);

        assertNotNull(ziel);
        assertEquals(2, ziel.size());

        IndexEntry ie1 = ziel.get(0);
        assertEquals(zi1.getTimeTag(), ie1.getTimeTag());
        assertEquals(VALUE_1, ie1.getPreValue(), DELTA);
        assertEquals(VALUE_2, ie1.getPostValue(), DELTA);

        IndexEntry ie2 = ziel.get(1);
        assertEquals(zi2.getTimeTag(), ie1.getTimeTag());
        assertEquals(VALUE_1, ie2.getPreValue(), DELTA);
        assertEquals(VALUE_2, ie2.getPostValue(), DELTA);
    }

}
