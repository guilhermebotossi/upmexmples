package br.inpe.climaespacial.swd.indexes.c.repositories;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import br.inpe.climaespacial.swd.indexes.FirstHourlyAverageToCalculateIndexesReaderRepository;
import br.inpe.climaespacial.swd.indexes.b.mappers.HourlyAverageMapper;
import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultCIndexHourlyAverageReaderRepository.class)
public class CIndexHourlyAverageReaderRepositoryTest {

    private final String SELECT = "SELECT hae FROM HourlyAverageEntity hae WHERE hae.timeTag = :timeTag";

    @Produces
    @Mock
    private CIndexReaderRepository cIndexReaderRepository;

    @Produces
    @Mock
    private FirstHourlyAverageToCalculateIndexesReaderRepository firstHourlyAverageToCalculateIndexesReaderRepository;
	
    @Produces
    @Mock
    private EntityManager entityManager;

    @Produces
    @Mock
    private HourlyAverageMapper hourlyAverageMapper;

    @Inject
    private CIndexHourlyAverageReaderRepository cIndexHourlyAverageReaderRepository;

    @Test
    public void getHourlyAverage_called_returnsHourlyAverageFromLastCalculatedCIndex() {
        ZonedDateTime nhtbc = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(cIndexReaderRepository.getNextHourToBeCalculated()).thenReturn(nhtbc);
        TypedQuery<HourlyAverageEntity> haetq = mockTypedQuery(HourlyAverageEntity.class);
        when(entityManager.createQuery(SELECT, HourlyAverageEntity.class)).thenReturn(haetq);
        HourlyAverageEntity hae = mock(HourlyAverageEntity.class);
        List<HourlyAverageEntity> hael = mockList(HourlyAverageEntity.class);
        when(hael.isEmpty()).thenReturn(false);
        when(hael.get(0)).thenReturn(hae);
        when(haetq.getResultList()).thenReturn(hael);
        HourlyAverage ha1 = mock(HourlyAverage.class);
        when(hourlyAverageMapper.map(hae)).thenReturn(ha1);

        HourlyAverage ha2 = cIndexHourlyAverageReaderRepository.getHourlyAverage();

        assertNotNull(ha2);
        assertSame(ha1, ha2);
        verify(cIndexReaderRepository).getNextHourToBeCalculated();
        verify(entityManager).createQuery(SELECT, HourlyAverageEntity.class);
        verify(haetq).setParameter("timeTag", nhtbc);
        verify(haetq).setMaxResults(1);
        verify(haetq).getResultList();
        verify(hael).isEmpty();
        verify(hael).get(0);
        verify(hourlyAverageMapper).map(hae);
    }

    @Test
    public void getHourlyAverage_called_returnsHourlyAverageFromFirstHourlyAverageToCalculateIndexesReaderRepository() {
        when(cIndexReaderRepository.getNextHourToBeCalculated()).thenReturn(null);
        ZonedDateTime fh = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(firstHourlyAverageToCalculateIndexesReaderRepository.getFirstHour()).thenReturn(fh);
        TypedQuery<HourlyAverageEntity> haetq = mockTypedQuery(HourlyAverageEntity.class);
        when(entityManager.createQuery(SELECT, HourlyAverageEntity.class)).thenReturn(haetq);
        HourlyAverageEntity hae = mock(HourlyAverageEntity.class);
        List<HourlyAverageEntity> hael = mockList(HourlyAverageEntity.class);
        when(hael.isEmpty()).thenReturn(false);
        when(hael.get(0)).thenReturn(hae);
        when(haetq.getResultList()).thenReturn(hael);
        HourlyAverage ha1 = mock(HourlyAverage.class);
        when(hourlyAverageMapper.map(hae)).thenReturn(ha1);

        HourlyAverage ha2 = cIndexHourlyAverageReaderRepository.getHourlyAverage();

        assertNotNull(ha2);
        assertSame(ha1, ha2);
        verify(cIndexReaderRepository).getNextHourToBeCalculated();
        verify(firstHourlyAverageToCalculateIndexesReaderRepository).getFirstHour();
        verify(entityManager).createQuery(SELECT, HourlyAverageEntity.class);
        verify(haetq).setParameter("timeTag", fh);
        verify(haetq).setMaxResults(1);
        verify(haetq).getResultList();
        verify(hael).isEmpty();
        verify(hael).get(0);
        verify(hourlyAverageMapper).map(hae);
    }

    @Test
    public void getHourlyAverage_calledWithNoDataToCalculate_returnsNull() {
        when(cIndexReaderRepository.getNextHourToBeCalculated()).thenReturn(null);
        when(firstHourlyAverageToCalculateIndexesReaderRepository.getFirstHour()).thenReturn(null);

        HourlyAverage ha2 = cIndexHourlyAverageReaderRepository.getHourlyAverage();

        assertNull(ha2);
        verify(cIndexReaderRepository).getNextHourToBeCalculated();
        verify(firstHourlyAverageToCalculateIndexesReaderRepository).getFirstHour();
    }
}
