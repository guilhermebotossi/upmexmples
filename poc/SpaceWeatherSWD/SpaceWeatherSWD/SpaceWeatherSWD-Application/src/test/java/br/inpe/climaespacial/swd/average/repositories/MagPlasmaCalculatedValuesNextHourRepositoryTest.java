package br.inpe.climaespacial.swd.average.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultMagPlasmaCalculatedValuesNextHourRepository.class})
public class MagPlasmaCalculatedValuesNextHourRepositoryTest {

    private String SQL_SELECT = "SELECT MIN(me.timeTag) "
            + "FROM MagEntity me, PlasmaEntity pe, CalculatedValuesEntity cve "
            + "WHERE me.timeTag = pe.timeTag "
            + "AND me.timeTag = cve.timeTag";

    @Mock
    @Produces
    private EntityManager entityManager;

    @Inject
    private MagPlasmaCalculatedValuesNextHourRepository magPlasmaCalculatedValuesNextHourRepository;

    @Test
    public void getNextHour_called_returnDateTime() {
        TypedQuery<ZonedDateTime> tq = mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(SQL_SELECT, ZonedDateTime.class)).thenReturn(tq);
        List<ZonedDateTime> zdtl = mockList(ZonedDateTime.class);
        when(tq.getResultList()).thenReturn(zdtl);
        when(zdtl.isEmpty()).thenReturn(false);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(zdtl.get(0)).thenReturn(zdt1);

        ZonedDateTime nextHour = magPlasmaCalculatedValuesNextHourRepository.getNextHour();

        assertNotNull(nextHour);
        assertEquals(zdt2, nextHour);
        verify(entityManager).createQuery(SQL_SELECT, ZonedDateTime.class);
        verify(tq).getResultList();
        verify(zdtl, times(2)).get(0);
    }

    @Test
    public void getNextHour_called_returnNull() {
        TypedQuery<ZonedDateTime> tq = mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(SQL_SELECT, ZonedDateTime.class)).thenReturn(tq);
        List<ZonedDateTime> zdtl = mockList(ZonedDateTime.class);
        when(tq.getResultList()).thenReturn(zdtl);
        when(zdtl.isEmpty()).thenReturn(true);

        ZonedDateTime nextHour = magPlasmaCalculatedValuesNextHourRepository.getNextHour();

        assertNull(nextHour);
        verify(entityManager).createQuery(SQL_SELECT, ZonedDateTime.class);
        verify(tq).getResultList();
        verify(zdtl).get(0);
    }
}
