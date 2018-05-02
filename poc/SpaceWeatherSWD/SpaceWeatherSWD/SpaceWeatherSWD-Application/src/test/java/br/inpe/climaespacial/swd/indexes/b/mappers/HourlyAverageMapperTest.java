package br.inpe.climaespacial.swd.indexes.b.mappers;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.indexes.b.factories.HourlyAverageFactory;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    DefaultHourlyAverageMapper.class
})
public class HourlyAverageMapperTest {

    @Produces
    @Mock
    private HourlyAverageFactory hourlyAverageFactory;

    @Produces
    @Mock
    private ListFactory<HourlyAverage> hourlyAverageListFactory;

    @Inject
    private HourlyAverageMapper hourlyAverageMapper;

    @Test
    public void map_calledWithNull_returnsNull() {
        HourlyAverage ha = hourlyAverageMapper.map(null);

        assertNull(ha);
    }

    @Test
    public void map_calledWithEntity_returnsMappedDto() {
        HourlyAverage ha1 = mock(HourlyAverage.class);
        when(hourlyAverageFactory.create()).thenReturn(ha1);
        HourlyAverageEntity hae = mock(HourlyAverageEntity.class);
        final ZonedDateTime tt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(hae.getTimeTag()).thenReturn(tt);
        double v = 1.0;
        when(hae.getBxGsm()).thenReturn(v);
        when(hae.getByGsm()).thenReturn(v);
        when(hae.getBzGsm()).thenReturn(v);
        when(hae.getBt()).thenReturn(v);
        when(hae.getDensity()).thenReturn(v);
        when(hae.getTemperature()).thenReturn(v);
        when(hae.getSpeed()).thenReturn(v);
        when(hae.getEy()).thenReturn(v);
        when(hae.getDpr()).thenReturn(v);
        when(hae.getRmp()).thenReturn(v);

        HourlyAverage ha = hourlyAverageMapper.map(hae);

        assertNotNull(ha);
        verify(ha).setTimeTag(tt);
        verify(ha).setBxGsm(v);
        verify(ha).setByGsm(v);
        verify(ha).setBzGsm(v);
        verify(ha).setBt(v);
        verify(ha).setDensity(v);
        verify(ha).setTemperature(v);
        verify(ha).setSpeed(v);
        verify(ha).setEy(v);
        verify(ha).setDpr(v);
        verify(ha).setRmp(v);
        verify(hourlyAverageFactory).create();
    }

    @Test
    public void mapAll_calledWithEntityList_returnsMappedDtoList() {
        when(hourlyAverageListFactory.create()).thenReturn(new ArrayList<>());
        HourlyAverage ha1 = mock(HourlyAverage.class);
        when(hourlyAverageFactory.create()).thenReturn(ha1);
        HourlyAverageEntity hae = mock(HourlyAverageEntity.class);
        List<HourlyAverageEntity> hael = Arrays.asList(hae);
        final ZonedDateTime tt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(hae.getTimeTag()).thenReturn(tt);
        double v = 1.0;
        when(hae.getBxGsm()).thenReturn(v);
        when(hae.getByGsm()).thenReturn(v);
        when(hae.getBzGsm()).thenReturn(v);
        when(hae.getBt()).thenReturn(v);
        when(hae.getDensity()).thenReturn(v);
        when(hae.getTemperature()).thenReturn(v);
        when(hae.getSpeed()).thenReturn(v);
        when(hae.getEy()).thenReturn(v);
        when(hae.getDpr()).thenReturn(v);
        when(hae.getRmp()).thenReturn(v);

        List<HourlyAverage> hal = hourlyAverageMapper.mapAll(hael);
        HourlyAverage ha = hal.get(0);

        assertNotNull(hal);
        verify(ha).setTimeTag(tt);
        verify(ha).setBxGsm(v);
        verify(ha).setByGsm(v);
        verify(ha).setBzGsm(v);
        verify(ha).setBt(v);
        verify(ha).setDensity(v);
        verify(ha).setTemperature(v);
        verify(ha).setSpeed(v);
        verify(ha).setEy(v);
        verify(ha).setDpr(v);
        verify(ha).setRmp(v);
        verify(hourlyAverageFactory).create();
        verify(hourlyAverageListFactory).create();
    }
}
