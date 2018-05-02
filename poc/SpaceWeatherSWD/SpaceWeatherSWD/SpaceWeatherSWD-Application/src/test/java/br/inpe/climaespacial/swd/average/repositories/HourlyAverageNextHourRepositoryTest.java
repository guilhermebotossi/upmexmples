package br.inpe.climaespacial.swd.average.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.average.providers.HourlyAverageNextHourRepository;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultHourlyAverageNextHourRepository.class)
public class HourlyAverageNextHourRepositoryTest {

    private static final String SQL_SELECT = "SELECT MAX(hae.timeTag) from HourlyAverageEntity hae";

    @Mock
    @Produces
    private EntityManager entityManager;

    @Inject
    private HourlyAverageNextHourRepository hourlyAverageNextHourRepository;

    @Test
    public void getNextHour_called_returnsDate() {
        TypedQuery<ZonedDateTime> tq = mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(SQL_SELECT, ZonedDateTime.class)).thenReturn(tq);
        List<ZonedDateTime> zdtl = mockList(ZonedDateTime.class);
        when(tq.getResultList()).thenReturn(zdtl);
        when(zdtl.isEmpty()).thenReturn(false);
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T13:00:00z[UTC]");
        when(zdtl.get(0)).thenReturn(zdt);

        ZonedDateTime nextHour = hourlyAverageNextHourRepository.getNextHour();

        assertNotNull(nextHour);
        assertEquals(zdt2, nextHour);
        verify(entityManager).createQuery(SQL_SELECT, ZonedDateTime.class);
        verify(tq).getResultList();
        verify(zdtl, times(1)).get(0);
    }

    @Test
    public void getNextHour_called_returnsNull() {

        TypedQuery<ZonedDateTime> tq = mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(SQL_SELECT, ZonedDateTime.class)).thenReturn(tq);
        List<ZonedDateTime> zdtl = mockList(ZonedDateTime.class);
        when(tq.getResultList()).thenReturn(zdtl);
        when(zdtl.isEmpty()).thenReturn(true);

        ZonedDateTime nextHour = hourlyAverageNextHourRepository.getNextHour();

        assertNull(nextHour);
        verify(entityManager).createQuery(SQL_SELECT, ZonedDateTime.class);
        verify(tq).getResultList();
        verify(zdtl, times(1)).get(0);
    }
}
