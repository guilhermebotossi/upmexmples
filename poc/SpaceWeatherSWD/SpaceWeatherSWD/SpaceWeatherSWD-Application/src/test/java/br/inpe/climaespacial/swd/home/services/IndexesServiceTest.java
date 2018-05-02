package br.inpe.climaespacial.swd.home.services;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;

import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;
import br.inpe.climaespacial.swd.home.dtos.IndexData;
import br.inpe.climaespacial.swd.home.dtos.IndexEntry;
import br.inpe.climaespacial.swd.home.factories.IndexDataFactory;
import br.inpe.climaespacial.swd.home.mappers.IndexEntryMapper;
import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.indexes.IndexesReaderRepository;
import br.inpe.climaespacial.swd.indexes.RangeDate;
import br.inpe.climaespacial.swd.indexes.RangeDateFactory;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.b.repositories.BIndexReaderRepository;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.repositories.CIndexReaderRepository;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.repositories.VIndexReaderRepository;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import br.inpe.climaespacial.swd.indexes.z.repositories.ZIndexReaderRepository;
import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultIndexesService.class)
public class IndexesServiceTest {

    private static final int DAYS_TO_SUBTRACT = 6;
    private static final int PERIOD_SIZE = 31;

    @Produces
    @Mock
    private IntervalValidator intervalValidator;

    @Produces
    @Mock
    private BIndexReaderRepository bIndexReaderRepository;

    @Produces
    @Mock
    private CIndexReaderRepository cIndexReaderRepository;

    @Produces
    @Mock
    private VIndexReaderRepository vIndexReaderRepository;

    @Produces
    @Mock
    private ZIndexReaderRepository zIndexReaderRepository;

    @Produces
    @Mock
    private IndexEntryMapper indexEntryMapper;

    @Produces
    @Mock
    private IndexDataFactory indexDataFactory;

    @Produces
    @Mock
    private RangeDateFactory rangeDateFactory;

    @Produces
    @Mock
    private IndexesReaderRepository indexesReaderRepository;

    @Produces
    @Mock
    private DateTimeHelper dateTimeHelper;

    @Inject
    private IndexesService indexesService;

    @Test
    public void getRangeDate_called_returnsRangeDate() {
        ZonedDateTime lid = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        ZonedDateTime tlid = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime fid = tlid.minusDays(DAYS_TO_SUBTRACT) ;
         
        when(indexesReaderRepository.lastIndexesDate()).thenReturn(lid);
        when(dateTimeHelper.truncate(lid)).thenReturn(tlid);
        RangeDate rd1 = mock(RangeDate.class);
        when(rangeDateFactory.create(fid, tlid)).thenReturn(rd1);

        RangeDate rd2 = indexesService.getRangeDate();

        assertNotNull(rd2);
        assertSame(rd1, rd2);
        verify(indexesReaderRepository).lastIndexesDate();
        verify(dateTimeHelper).truncate(lid);
        verify(rangeDateFactory).create(fid, tlid);
    }
    
    @Test
    public void getRangeDate_called_returnsRangeDateWithNullValues() {
        ZonedDateTime lid = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        ZonedDateTime tlid = null;
        ZonedDateTime fid = null;
        
        when(indexesReaderRepository.lastIndexesDate()).thenReturn(lid);
        when(dateTimeHelper.truncate(lid)).thenReturn(tlid);
        RangeDate rd1 = mock(RangeDate.class);
        when(rangeDateFactory.create(fid, tlid)).thenReturn(rd1);

        RangeDate rd2 = indexesService.getRangeDate();

        assertNotNull(rd2);
        assertSame(rd1, rd2);
        verify(indexesReaderRepository).lastIndexesDate();
        verify(dateTimeHelper).truncate(lid);
        verify(rangeDateFactory).create(fid, tlid);
    }

    @Test
    public void getIndexData_called_returnsIndexDataFilledWithIndexEntrys() {

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");

        ZonedDateTime nfnParsed = nfn.plusDays(1);
        when(dateTimeHelper.setMaxDayTime(nfn)).thenReturn(nfnParsed);
        
        List<BIndex> bil = mockList(BIndex.class);
        when(bIndexReaderRepository.listByPeriod(ffn, nfnParsed)).thenReturn(bil);
        List<IndexEntry> bIndexEntryList = mockList(IndexEntry.class);
        when(indexEntryMapper.mapB(bil)).thenReturn(bIndexEntryList);

        List<CIndex> cil = mockList(CIndex.class);
        when(cIndexReaderRepository.listByPeriod(ffn, nfnParsed)).thenReturn(cil);
        List<IndexEntry> cIndexEntryList = mockList(IndexEntry.class);
        when(indexEntryMapper.mapC(cil)).thenReturn(cIndexEntryList);

        List<VIndex> vil = mockList(VIndex.class);
        when(vIndexReaderRepository.listByPeriod(ffn, nfnParsed)).thenReturn(vil);
        List<IndexEntry> vIndexEntryList = mockList(IndexEntry.class);
        when(indexEntryMapper.mapV(vil)).thenReturn(vIndexEntryList);

        List<ZIndex> zil = mockList(ZIndex.class);
        when(zIndexReaderRepository.listByPeriod(ffn, nfnParsed)).thenReturn(zil);
        List<IndexEntry> zIndexEntryList = mockList(IndexEntry.class);
        when(indexEntryMapper.mapZ(zil)).thenReturn(zIndexEntryList);

        IndexData expectedIa = mock(IndexData.class);
        when(indexDataFactory.create(bIndexEntryList, cIndexEntryList, vIndexEntryList, zIndexEntryList)).thenReturn(expectedIa);

        IndexData ia = indexesService.getIndexData(ffn, nfn);

        verify(intervalValidator, times(1)).validate(ffn, nfn, PERIOD_SIZE);
        
        verify(dateTimeHelper, times(1)).setMaxDayTime(nfn);

        verify(bIndexReaderRepository, times(1)).listByPeriod(ffn, nfnParsed);
        verify(indexEntryMapper, times(1)).mapB(bil);

        verify(cIndexReaderRepository, times(1)).listByPeriod(ffn, nfnParsed);
        verify(indexEntryMapper, times(1)).mapC(cil);

        verify(vIndexReaderRepository, times(1)).listByPeriod(ffn, nfnParsed);
        verify(indexEntryMapper, times(1)).mapV(vil);

        verify(zIndexReaderRepository, times(1)).listByPeriod(ffn, nfnParsed);
        verify(indexEntryMapper, times(1)).mapZ(zil);

        verify(indexDataFactory, times(1)).create(bIndexEntryList, cIndexEntryList, vIndexEntryList, zIndexEntryList);
        assertNotNull(ia);
        assertSame(expectedIa, ia);
    }

}
