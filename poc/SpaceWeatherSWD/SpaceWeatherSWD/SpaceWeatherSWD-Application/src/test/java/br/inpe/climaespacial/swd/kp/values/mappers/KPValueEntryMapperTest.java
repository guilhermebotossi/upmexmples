package br.inpe.climaespacial.swd.kp.values.mappers;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
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
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.values.dtos.KPValueEntry;
import br.inpe.climaespacial.swd.kp.values.factories.KPValueEntryFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPValueEntryMapper.class)
public class KPValueEntryMapperTest {

    private static final long VALUE = 1;

    @Produces
    @Mock
    private ListFactory<KPValueEntry> kpForecastEntryListFactory;

    @Produces
    @Mock
    private KPValueEntryFactory kpForecastEntryFactory;

    @Inject
    private KPValueEntryMapper kpForecastEntryMapper;

    @Test
    public void mapKPValue_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            kpForecastEntryMapper.mapKPValue(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"kpValueList\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapKP_calledWithEmptyList_returnsEmptyList() {
        List<KPValue> kpl = mockList(KPValue.class);

        List<KPValueEntry> kpfel1 = mockList(KPValueEntry.class);
        when(kpForecastEntryListFactory.create()).thenReturn(kpfel1);

        List<KPValueEntry> kpfel2 = kpForecastEntryMapper.mapKPValue(kpl);

        assertNotNull(kpfel2);
        assertSame(kpfel1, kpfel2);
        verify(kpForecastEntryListFactory).create();
        verify(kpfel2, never()).add(any());
    }

    @Test
    public void mapKP_called_returnKPForecastEntryList() {
        ZonedDateTime timeTag1 = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        ZonedDateTime timeTag2 = ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]");
        ZonedDateTime timeTag3 = ZonedDateTime.parse("2017-01-01T06:00:00z[UTC]");
        ZonedDateTime timeTag4 = ZonedDateTime.parse("2017-01-01T09:00:00z[UTC]");

        KPValue kp1 = new KPValue();
        kp1.setTimeTag(timeTag1);
        kp1.setKPValue(VALUE);

        KPValue kp2 = new KPValue();
        kp2.setTimeTag(timeTag2);
        kp2.setKPValue(VALUE);

        KPValue kp3 = new KPValue();
        kp3.setTimeTag(timeTag3);
        kp3.setKPValue(VALUE);

        KPValue kp4 = new KPValue();
        kp4.setTimeTag(timeTag4);
        kp4.setKPValue(VALUE);

        when(kpForecastEntryListFactory.create()).thenReturn(new ArrayList<>());
        when(kpForecastEntryFactory.create()).thenAnswer((a) -> new KPValueEntry());

        List<KPValue> kpvl = Arrays.asList(kp1, kp2, kp3, kp4);

        List<KPValueEntry> kpfel = kpForecastEntryMapper.mapKPValue(kpvl);

        assertNotNull(kpfel);
        assertEquals(4, kpfel.size());
        
        Double value = Long.valueOf(VALUE).doubleValue();

        KPValueEntry kpe1 = kpfel.get(0);
        assertEquals(timeTag1, kpe1.getTimeTag());
        assertEquals(value, kpe1.getValue());
        assertFalse(kpe1.isForecast()); 
        assertEquals(toPresentationTimeTag(timeTag1), kpe1.getPresentationTimeTag());

        KPValueEntry kpe2 = kpfel.get(1);
        assertEquals(timeTag2, kpe2.getTimeTag());
        assertEquals(value, kpe2.getValue()); 
        assertFalse(kpe2.isForecast());
        assertEquals(toPresentationTimeTag(timeTag2), kpe2.getPresentationTimeTag());

        KPValueEntry kpe3 = kpfel.get(2);
        assertEquals(timeTag3, kpe3.getTimeTag());
        assertEquals(value, kpe3.getValue());
        assertFalse(kpe3.isForecast());
        assertEquals(toPresentationTimeTag(timeTag3), kpe3.getPresentationTimeTag());

        KPValueEntry kpe4 = kpfel.get(3);
        assertEquals(timeTag4, kpe4.getTimeTag());
        assertEquals(value, kpe4.getValue());
        assertFalse(kpe4.isForecast());
        assertEquals(toPresentationTimeTag(timeTag4), kpe4.getPresentationTimeTag());

    }

	private ZonedDateTime toPresentationTimeTag(ZonedDateTime timeTag1) {
		return timeTag1.minusMinutes(90);
	}

}
