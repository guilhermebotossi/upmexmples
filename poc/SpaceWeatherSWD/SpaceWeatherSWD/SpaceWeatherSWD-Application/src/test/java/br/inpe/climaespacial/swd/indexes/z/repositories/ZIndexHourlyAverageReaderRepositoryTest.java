package br.inpe.climaespacial.swd.indexes.z.repositories;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.commons.utils.CollectionUtils;
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
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultZIndexHourlyAverageReaderRepository.class)
public class ZIndexHourlyAverageReaderRepositoryTest {

    private final String SELECT = "SELECT hae FROM HourlyAverageEntity hae WHERE hae.timeTag <= :timeTag ORDER BY hae.timeTag DESC";

    private final int maxSize = 3;

    @Produces
    @Mock
    private ZIndexReaderRepository zIndexReaderRepository;

    @Produces
    @Mock
    private FirstHourlyAverageToCalculateIndexesReaderRepository firstHourlyAverageToCalculateIndexesReaderRepository;

    @Produces
    @Mock
    private EntityManager entityManager;

    @Produces
    @Mock
    private HourlyAverageMapper hourlyAverageMapper;

    @Produces
    @Mock
    private ListFactory<HourlyAverage> hourlyAverageListFactory;
    
    @Produces
    @Mock
    private CollectionUtils collectionUtils;

    @Inject
    private ZIndexHourlyAverageReaderRepository zIndexHourlyAverageReaderRepository;

    @Test
    public void getHourlyAverage_called_returnsHourlyAverageFromLastCalculatedZIndex() {
        ZonedDateTime nhtbc = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(zIndexReaderRepository.getNextHourToBeCalculated()).thenReturn(nhtbc);
        TypedQuery<HourlyAverageEntity> haetq = mockTypedQuery(HourlyAverageEntity.class);
        when(entityManager.createQuery(SELECT, HourlyAverageEntity.class)).thenReturn(haetq);
        HourlyAverageEntity hae = mock(HourlyAverageEntity.class);
        when(hae.getTimeTag()).thenReturn(nhtbc);
        List<HourlyAverageEntity> hael = mockList(HourlyAverageEntity.class);
        when(hael.isEmpty()).thenReturn(false);
        when(hael.get(0)).thenReturn(hae);
        when(haetq.getResultList()).thenReturn(hael);
        List<HourlyAverage> hal1 = mockList(HourlyAverage.class);
        when(hourlyAverageMapper.mapAll(hael)).thenReturn(hal1);

        List<HourlyAverage> hal2 = zIndexHourlyAverageReaderRepository.getHourlyAverages();

        assertNotNull(hal2);
        assertSame(hal1, hal2);
        verify(zIndexReaderRepository).getNextHourToBeCalculated();
        verify(entityManager).createQuery(SELECT, HourlyAverageEntity.class);
        verify(haetq).setParameter("timeTag", nhtbc);
        verify(haetq).setMaxResults(maxSize);
        verify(haetq).getResultList();
        verify(collectionUtils).reverse(hael);
        verify(hourlyAverageMapper).mapAll(hael); 
    }

    @Test
    public void getHourlyAverage_called_returnsHourlyAverageFromFirstHourlyAverageToCalculateIndexesReaderRepository() {
        when(zIndexReaderRepository.getNextHourToBeCalculated()).thenReturn(null);
        ZonedDateTime fh = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(firstHourlyAverageToCalculateIndexesReaderRepository.getFirstHour()).thenReturn(fh);
        TypedQuery<HourlyAverageEntity> haetq = mockTypedQuery(HourlyAverageEntity.class);
        when(entityManager.createQuery(SELECT, HourlyAverageEntity.class)).thenReturn(haetq);
        HourlyAverageEntity hae = mock(HourlyAverageEntity.class);
        when(hae.getTimeTag()).thenReturn(fh);
        List<HourlyAverageEntity> hael = mockList(HourlyAverageEntity.class);
        when(hael.isEmpty()).thenReturn(false);
        when(hael.get(0)).thenReturn(hae);
        when(haetq.getResultList()).thenReturn(hael);
        List<HourlyAverage> hal1 = mockList(HourlyAverage.class);
        when(hourlyAverageMapper.mapAll(hael)).thenReturn(hal1);

        List<HourlyAverage> hal2 = zIndexHourlyAverageReaderRepository.getHourlyAverages();

        assertNotNull(hal2);
        assertSame(hal1, hal2);
        verify(zIndexReaderRepository).getNextHourToBeCalculated();
        verify(firstHourlyAverageToCalculateIndexesReaderRepository).getFirstHour();
        verify(entityManager).createQuery(SELECT, HourlyAverageEntity.class);
        verify(haetq).setParameter("timeTag", fh);
        verify(haetq).setMaxResults(maxSize);
        verify(haetq).getResultList();
        verify(collectionUtils).reverse(hael);
        verify(hourlyAverageMapper).mapAll(hael);
    }

    @Test
    public void getHourlyAverage_calledWithNoDataToCalculate_returnsEmptyList() {
        when(zIndexReaderRepository.getNextHourToBeCalculated()).thenReturn(null);
        when(firstHourlyAverageToCalculateIndexesReaderRepository.getFirstHour()).thenReturn(null);
        List<HourlyAverage> hal1 = mockList(HourlyAverage.class);
        when(hourlyAverageListFactory.create()).thenReturn(hal1);

        List<HourlyAverage> hal2 = zIndexHourlyAverageReaderRepository.getHourlyAverages();

        assertNotNull(hal2);
        assertSame(hal1, hal2);
        verify(zIndexReaderRepository).getNextHourToBeCalculated();
        verify(firstHourlyAverageToCalculateIndexesReaderRepository).getFirstHour();
        verify(hourlyAverageListFactory).create();
    }

    @Test
    public void getHourlyAverages_calledWithNoHourlyAverageForCurrentIndexToBeCalculated_returnsEmptyList() {
        ZonedDateTime nhtbc = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(zIndexReaderRepository.getNextHourToBeCalculated()).thenReturn(nhtbc);
        TypedQuery<HourlyAverageEntity> haetq = mockTypedQuery(HourlyAverageEntity.class);
        when(entityManager.createQuery(SELECT, HourlyAverageEntity.class)).thenReturn(haetq);
        HourlyAverageEntity hae = mock(HourlyAverageEntity.class);
        when(hae.getTimeTag()).thenReturn(nhtbc.minusHours(1));
        List<HourlyAverageEntity> hael = mockList(HourlyAverageEntity.class);
        when(hael.isEmpty()).thenReturn(false);
        when(hael.get(0)).thenReturn(hae);
        when(haetq.getResultList()).thenReturn(hael);
        List<HourlyAverage> hal1 = mockList(HourlyAverage.class);
        when(hourlyAverageListFactory.create()).thenReturn(hal1);

        List<HourlyAverage> hal2 = zIndexHourlyAverageReaderRepository.getHourlyAverages();

        assertNotNull(hal2);
        assertSame(hal1, hal2);
        verify(zIndexReaderRepository).getNextHourToBeCalculated();
        verify(entityManager).createQuery(SELECT, HourlyAverageEntity.class);
        verify(haetq).setParameter("timeTag", nhtbc);
        verify(haetq).setMaxResults(maxSize);
        verify(haetq).getResultList();
        verify(hourlyAverageListFactory).create();
    }

}
