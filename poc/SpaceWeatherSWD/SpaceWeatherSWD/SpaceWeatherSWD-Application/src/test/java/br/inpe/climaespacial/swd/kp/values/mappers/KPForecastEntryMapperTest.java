package br.inpe.climaespacial.swd.kp.values.mappers;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.values.dtos.KPForecastEntry;
import br.inpe.climaespacial.swd.kp.values.factories.KPForecastEntryFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPForecastEntryMapper.class)
public class KPForecastEntryMapperTest {

    @Produces
    @Mock
    private ListFactory<KPForecastEntry> kpForecastEntryListFactory;

    @Produces
    @Mock
    private KPForecastEntryFactory kpForecastEntryFactory;

    @Inject
    private KPForecastEntryMapper kpForecastEntryMapper;


    @Test
    public void mapKPForecast_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            kpForecastEntryMapper.mapKPForecast(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"kpForecastList\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapKPForecast_calledWithEmptyList_returnsEmptyList() {
        List<KPForecast> kpfl = mockList(KPForecast.class);

        List<KPForecastEntry> kpfel1 = mockList(KPForecastEntry.class);
        when(kpForecastEntryListFactory.create()).thenReturn(kpfel1);

        List<KPForecastEntry> kpfel2 = kpForecastEntryMapper.mapKPForecast(kpfl);

        assertNotNull(kpfel2);
        assertSame(kpfel1, kpfel2);
        verify(kpForecastEntryListFactory).create();
        verify(kpfel2, never()).add(any());
    }

    @Test
    public void mapKPForecast_called_returnKPForecastEntryList() {
        ZonedDateTime timeTag1 = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        ZonedDateTime timeTag2 = ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]");
        ZonedDateTime timeTag3 = ZonedDateTime.parse("2017-01-01T06:00:00z[UTC]");

        KPForecast kpf1 = new KPForecast();
        kpf1.setPredictedTimeTag(timeTag2);
        kpf1.setIndexesTimeTag(timeTag1);
        Double probability1 = 1.0;
        Double probability2 = 2.0;
        Double probability3 = 3.0;
        Double inferiorLimit1 = 4.0;
        Double inferiorLimit2 = 5.0;
        Double inferiorLimit3 = 6.0;
        Double upperLimit1 = 7.0;
        Double upperLimit2 = 8.0;
        Double upperLimit3 = 9.0;
        kpf1.setProbability1(probability1);
        kpf1.setProbability2(probability2);
        kpf1.setProbability3(probability3);
        kpf1.setInferiorLimit1(inferiorLimit1);
        kpf1.setInferiorLimit2(inferiorLimit2);
        kpf1.setInferiorLimit3(inferiorLimit3);
        kpf1.setUpperLimit1(upperLimit1);
        kpf1.setUpperLimit2(upperLimit2);
        kpf1.setUpperLimit3(upperLimit3);

        KPForecast kpf2 = new KPForecast();
        kpf2.setPredictedTimeTag(timeTag3);
        kpf2.setIndexesTimeTag(timeTag2);
        kpf2.setProbability1(probability1);
        kpf2.setProbability2(probability2);
        kpf2.setProbability3(probability3);
        kpf2.setInferiorLimit1(inferiorLimit1);
        kpf2.setInferiorLimit2(inferiorLimit2);
        kpf2.setInferiorLimit3(inferiorLimit3);
        kpf2.setUpperLimit1(upperLimit1);
        kpf2.setUpperLimit2(upperLimit2);
        kpf2.setUpperLimit3(upperLimit3);

        when(kpForecastEntryListFactory.create()).thenReturn(new ArrayList<>());
        when(kpForecastEntryFactory.create()).thenAnswer((a) -> new KPForecastEntry());

        List<KPForecast> kpfl = Arrays.asList(kpf1, kpf2);

        List<KPForecastEntry> kpfel = kpForecastEntryMapper.mapKPForecast(kpfl);

        assertNotNull(kpfel);
        assertEquals(2, kpfel.size());

        KPForecastEntry kpe1 = kpfel.get(0);
        assertEquals(kpf1.getPredictedTimeTag(), kpe1.getTimeTag());
        assertEquals(toPresentationTimeTag(timeTag2), kpe1.getPresentationTimeTag());
        assertTrue(kpe1.isForecast());
        assertEquals(probability1, kpe1.getProbability1());
        assertEquals(probability2, kpe1.getProbability2());
        assertEquals(probability3, kpe1.getProbability3());
        
        assertEquals(inferiorLimit1, kpe1.getInferiorLimit1());
        assertEquals(inferiorLimit2, kpe1.getInferiorLimit2());
        assertEquals(inferiorLimit3, kpe1.getInferiorLimit3());
        
        assertEquals(upperLimit1, kpe1.getUpperLimit1());
        assertEquals(upperLimit2, kpe1.getUpperLimit2());
        assertEquals(upperLimit3, kpe1.getUpperLimit3());
 
        KPForecastEntry kpe2 = kpfel.get(1);
        assertEquals(kpf2.getPredictedTimeTag(), kpe2.getTimeTag());
        assertEquals(toPresentationTimeTag(timeTag3), kpe2.getPresentationTimeTag());
        assertTrue(kpe2.isForecast());
        assertEquals(probability1, kpe2.getProbability1());
        assertEquals(probability2, kpe2.getProbability2());
        assertEquals(probability3, kpe2.getProbability3());
        
        assertEquals(inferiorLimit1, kpe2.getInferiorLimit1());
        assertEquals(inferiorLimit2, kpe2.getInferiorLimit2());
        assertEquals(inferiorLimit3, kpe2.getInferiorLimit3());
        
        assertEquals(upperLimit1, kpe2.getUpperLimit1());
        assertEquals(upperLimit2, kpe2.getUpperLimit2());
        assertEquals(upperLimit3, kpe2.getUpperLimit3());
    }

	private ZonedDateTime toPresentationTimeTag(ZonedDateTime timeTag1) {
		return timeTag1.minusMinutes(90);
	}

}
