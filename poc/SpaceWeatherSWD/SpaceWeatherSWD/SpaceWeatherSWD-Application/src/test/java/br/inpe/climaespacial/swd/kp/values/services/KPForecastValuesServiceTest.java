package br.inpe.climaespacial.swd.kp.values.services;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.values.dtos.KPForecastData;
import br.inpe.climaespacial.swd.kp.values.dtos.KPForecastEntry;
import br.inpe.climaespacial.swd.kp.values.dtos.KPValueEntry;
import br.inpe.climaespacial.swd.kp.values.dtos.BaseKPForecastEntry;
import br.inpe.climaespacial.swd.kp.values.factories.KPForecastDataFactory;
import br.inpe.climaespacial.swd.kp.values.mappers.KPForecastEntryMapper;
import br.inpe.climaespacial.swd.kp.values.mappers.KPValueEntryMapper;
import br.inpe.climaespacial.swd.kp.values.repositories.KPForecastReaderRepository;
import br.inpe.climaespacial.swd.kp.values.repositories.KPValueReaderRepository;
import static org.hamcrest.Matchers.hasSize;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPForecastValuesService.class)
public class KPForecastValuesServiceTest {
    
    @Mock
    @Produces
    private KPValueReaderRepository kpReaderRepository;
    
    @Mock
    @Produces
    private KPForecastReaderRepository kpForecastReaderRepository;
    
    @Mock
    @Produces
    private KPForecastEntryMapper kpForecastEntryMapper;
    
    @Mock
    @Produces
    private KPValueEntryMapper kpValueEntryMapper;
    
    @Mock
    @Produces
    private KPForecastDataFactory kpForecastDataFactory;
    
    @Mock
    @Produces
    private ListFactory<BaseKPForecastEntry> baseKPForecastEntryList;
    
    @Mock
    @Produces
    private DateTimeProvider dateTimeProvider;
    
    @Inject
    private KPForecastValuesService kpForecastValuesService;
    
    @Test
    public void list_called_succeeds() {
        List<KPValue> kpvl = createKPList();
        when(kpReaderRepository.getLastKPIndexes()).thenReturn(kpvl);
        
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T14:00:00z[UTC]");
        List<KPForecast> kpfl = mockList(KPForecast.class);
        when(kpfl.size()).thenReturn(1);
        when(kpForecastReaderRepository.getNextForecasts(zdt)).thenReturn(kpfl);
        
        List<KPValueEntry> kpfe1 = mockList(KPValueEntry.class);
        when(kpValueEntryMapper.mapKPValue(kpvl)).thenReturn(kpfe1);
        
        List<KPForecastEntry> kpfe2 = mockList(KPForecastEntry.class);
        when(kpForecastEntryMapper.mapKPForecast(kpfl)).thenReturn(kpfe2);
        
        List<BaseKPForecastEntry> kpfe3 = mockList(BaseKPForecastEntry.class);
        when(baseKPForecastEntryList.create()).thenReturn(kpfe3);
        
        KPForecastData kpfd1 = mock(KPForecastData.class);
        when(kpForecastDataFactory.create(kpfe3)).thenReturn(kpfd1);
        when(kpfd1.getKpForecastEntries()).thenReturn(kpfe3);
        
        ZonedDateTime now = ZonedDateTime.parse("2017-01-01T14:00:00z[UTC]");
        when(dateTimeProvider.getNow()).thenReturn(now);
        KPForecast kpf = mock(KPForecast.class);
        when(kpfl.get(0)).thenReturn(kpf);
        ZonedDateTime lptt = ZonedDateTime.parse("2017-01-02T14:00:00z[UTC]");
        when(kpf.getPredictedTimeTag()).thenReturn(lptt);
        
        KPForecastData kpfd2 = kpForecastValuesService.list();
        
        assertNotNull(kpfd2);
        assertSame(kpfd1, kpfd2);
        assertNotNull(kpfd2.getKpForecastEntries());
        assertSame(kpfe3, kpfd2.getKpForecastEntries());
        verify(kpReaderRepository).getLastKPIndexes();
        verify(kpForecastReaderRepository).getNextForecasts(zdt);
        verify(dateTimeProvider).getNow();
        verify(kpfl).isEmpty();
        verify(kpfl).size();
        verify(kpfl).get(0);
        verify(kpfl, never()).clear();
        verify(kpValueEntryMapper).mapKPValue(kpvl);
        verify(kpForecastEntryMapper).mapKPForecast(kpfl);
        verify(baseKPForecastEntryList).create();
        verify(kpfe3).addAll(kpfe1);
        verify(kpfe3).addAll(kpfe2);
        verify(kpForecastDataFactory).create(kpfe3);
    }
    
    @Test
    public void list_called_succeedsReturnsKPForecastDataEmpty() {
        List<KPValue> kpvl = mockList(KPValue.class);
        when(kpReaderRepository.getLastKPIndexes()).thenReturn(kpvl);
        when(kpvl.size()).thenReturn(0);
        
        List<BaseKPForecastEntry> kpfel = mockList(BaseKPForecastEntry.class);
        when(baseKPForecastEntryList.create()).thenReturn(kpfel);
        when(kpfel.size()).thenReturn(0);
        
        KPForecastData kpfd1 = mock(KPForecastData.class);        
        when(kpForecastDataFactory.create(kpfel)).thenReturn(kpfd1);
        
        KPForecastData kpfd2 = kpForecastValuesService.list();
        
        assertNotNull(kpfd2);
        assertNotNull(kpfd2.getKpForecastEntries());
        assertSame(kpfd1, kpfd2);
        assertThat(kpfd2.getKpForecastEntries(), hasSize(0));
        
    }
    
    @Test
    public void list_called_succeedsReturnsForecastEmpty() {
        List<KPValue> kpvl = createKPList();
        when(kpReaderRepository.getLastKPIndexes()).thenReturn(kpvl);
        
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T14:00:00z[UTC]");
        List<KPForecast> kpfl = mockList(KPForecast.class);
        when(kpfl.size()).thenReturn(1);
        when(kpForecastReaderRepository.getNextForecasts(zdt)).thenReturn(kpfl);
        
        List<KPValueEntry> kpfe1 = mockList(KPValueEntry.class);
        when(kpValueEntryMapper.mapKPValue(kpvl)).thenReturn(kpfe1);
        
        List<KPForecastEntry> kpfe2 = mockList(KPForecastEntry.class);
        when(kpForecastEntryMapper.mapKPForecast(kpfl)).thenReturn(kpfe2);
        
        List<BaseKPForecastEntry> kpfe3 = mockList(BaseKPForecastEntry.class);
        when(baseKPForecastEntryList.create()).thenReturn(kpfe3);
        
        KPForecastData kpfd1 = mock(KPForecastData.class);
        when(kpForecastDataFactory.create(kpfe3)).thenReturn(kpfd1);
        when(kpfd1.getKpForecastEntries()).thenReturn(kpfe3);
        
        ZonedDateTime now = ZonedDateTime.parse("2017-01-02T14:00:00z[UTC]");
        when(dateTimeProvider.getNow()).thenReturn(now);
        KPForecast kpf = mock(KPForecast.class);
        when(kpfl.get(0)).thenReturn(kpf);
        ZonedDateTime lptt = ZonedDateTime.parse("2017-01-01T14:00:00z[UTC]");
        when(kpf.getPredictedTimeTag()).thenReturn(lptt);
        
        KPForecastData kpfd2 = kpForecastValuesService.list();
        
        assertNotNull(kpfd2);
        assertSame(kpfd1, kpfd2);
        assertNotNull(kpfd2.getKpForecastEntries());
        assertThat(kpfd2.getKpForecastEntries(), is(not(empty())));
        verify(kpReaderRepository).getLastKPIndexes();
        verify(kpForecastReaderRepository).getNextForecasts(zdt);
        verify(dateTimeProvider).getNow();
        verify(kpfl).isEmpty();
        verify(kpfl).size();
        verify(kpfl).get(0);
        //verify(kpfl).clear();
        verify(kpValueEntryMapper).mapKPValue(kpvl);
        verify(kpForecastEntryMapper).mapKPForecast(kpfl);
        verify(baseKPForecastEntryList).create();
        verify(kpfe3).addAll(kpfe1);
        verify(kpfe3).addAll(kpfe2);
        verify(kpForecastDataFactory).create(kpfe3);
    }
    
    private List<KPValue> createKPList() {
        KPValue kp1 = new KPValue();
        kp1.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        KPValue kp2 = new KPValue();
        kp2.setTimeTag(ZonedDateTime.parse("2017-01-01T13:00:00z[UTC]"));
        KPValue kp3 = new KPValue();
        kp3.setTimeTag(ZonedDateTime.parse("2017-01-01T14:00:00z[UTC]"));
        
        List<KPValue> kpl = Arrays.asList(kp1, kp2, kp3);
        
        return kpl;
    }
    
}
