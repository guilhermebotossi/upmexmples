package br.inpe.climaespacial.swd.indexes.v.repositories;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.commons.utils.CollectionUtils;
import br.inpe.climaespacial.swd.indexes.FirstHourlyAverageToCalculateIndexesReaderRepository;
import br.inpe.climaespacial.swd.indexes.b.mappers.HourlyAverageMapper;
import br.inpe.climaespacial.swd.indexes.v.helpers.HourlyAverageDataFillerHelper;
import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultVIndexHourlyAverageReaderRepository.class)
public class VIndexHourlyAverageReaderRepositoryTest {

    private final String SELECT = "SELECT hae FROM HourlyAverageEntity hae WHERE hae.timeTag <= :timeTag ORDER BY hae.timeTag DESC";

    private static final int targetSize = 26;

    @Produces
    @Mock
    private VIndexReaderRepository vIndexReaderRepository;

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

    @Mock
    @Produces
    private HourlyAverageDataFillerHelper houlyAverageDataFillerHelper;
    
    @Mock
    @Produces
    private CollectionUtils collectionUtils;

    @Inject
    private VIndexHourlyAverageReaderRepository vIndexHourlyAverageReaderRepository; 

    @Test
    public void getHourlyAverages_called_returnsHourlyAverageFromLastCalculatedVIndex() {
        ZonedDateTime fdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime idt = fdt.minusHours(targetSize -1 );
        when(vIndexReaderRepository.getNextHourToBeCalculated()).thenReturn(fdt);
        TypedQuery<HourlyAverageEntity> haetq = mockTypedQuery(HourlyAverageEntity.class);
        when(entityManager.createQuery(SELECT, HourlyAverageEntity.class)).thenReturn(haetq);
        HourlyAverageEntity hae = mock(HourlyAverageEntity.class);
        when(hae.getTimeTag()).thenReturn(fdt);
        List<HourlyAverageEntity> hael = mockList(HourlyAverageEntity.class);
        when(hael.isEmpty()).thenReturn(false);
        when(hael.get(0)).thenReturn(hae);
        when(haetq.getResultList()).thenReturn(hael);
        List<HourlyAverage> hal1 = mockList(HourlyAverage.class);
        List<HourlyAverage> hal2 = mockList(HourlyAverage.class);
        when(hourlyAverageMapper.mapAll(hael)).thenReturn(hal1);
        when(houlyAverageDataFillerHelper.fillByHours(idt, fdt, hal1)).thenReturn(hal2);

        List<HourlyAverage> hal3 = vIndexHourlyAverageReaderRepository.getHourlyAverages();

        assertNotNull(hal3);
        assertNotSame(hal1, hal3);
        verify(vIndexReaderRepository).getNextHourToBeCalculated();
        verify(entityManager).createQuery(SELECT, HourlyAverageEntity.class);
        verify(haetq).setParameter("timeTag", fdt);
        verify(haetq).setMaxResults(targetSize);
        verify(haetq).getResultList();
        verify(collectionUtils).reverse(hael);
        verify(hourlyAverageMapper).mapAll(hael);
        verify(houlyAverageDataFillerHelper).fillByHours(idt, fdt, hal1);
    }

    @Test
    public void getHourlyAverages_called_returnsHourlyAverageFromFirstHourlyAverageToCalculateIndexesReaderRepository() {
        when(vIndexReaderRepository.getNextHourToBeCalculated()).thenReturn(null);
        ZonedDateTime fh = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime idt = fh.minusHours(targetSize - 1);
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
        List<HourlyAverage> hal2 = mockList(HourlyAverage.class);
        when(hourlyAverageMapper.mapAll(hael)).thenReturn(hal1);
        when(houlyAverageDataFillerHelper.fillByHours(idt, fh, hal1)).thenReturn(hal2);

        List<HourlyAverage> hal3 = vIndexHourlyAverageReaderRepository.getHourlyAverages();

        assertNotNull(hal3);
        assertNotSame(hal1, hal3);
        verify(vIndexReaderRepository).getNextHourToBeCalculated();
        verify(firstHourlyAverageToCalculateIndexesReaderRepository).getFirstHour();
        verify(entityManager).createQuery(SELECT, HourlyAverageEntity.class);
        verify(haetq).setParameter("timeTag", fh);
        verify(haetq).setMaxResults(targetSize);
        verify(haetq).getResultList();
        verify(collectionUtils).reverse(hael);
        verify(hourlyAverageMapper).mapAll(hael);
        verify(houlyAverageDataFillerHelper).fillByHours(idt, fh, hal1);
    }

    @Test
    public void getHourlyAverages_calledWithNoDataToCalculate_returnsEmptyList() {
        when(vIndexReaderRepository.getNextHourToBeCalculated()).thenReturn(null);
        when(firstHourlyAverageToCalculateIndexesReaderRepository.getFirstHour()).thenReturn(null);
        List<HourlyAverage> hal1 = mockList(HourlyAverage.class);
        when(hourlyAverageListFactory.create()).thenReturn(hal1);

        List<HourlyAverage> hal2 = vIndexHourlyAverageReaderRepository.getHourlyAverages();

        assertNotNull(hal2);
        assertSame(hal1, hal2);
        verify(vIndexReaderRepository).getNextHourToBeCalculated();
        verify(firstHourlyAverageToCalculateIndexesReaderRepository).getFirstHour();
        verify(hourlyAverageListFactory).create();
    }

    @Test
    public void getHourlyAverages_calledWithNoHourlyAverageForCurrentIndexToBeCalculated_returnsEmptyList() {
        ZonedDateTime nhtbc = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(vIndexReaderRepository.getNextHourToBeCalculated()).thenReturn(nhtbc);
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

        List<HourlyAverage> hal2 = vIndexHourlyAverageReaderRepository.getHourlyAverages();

        assertNotNull(hal2);
        assertSame(hal1, hal2);
        verify(vIndexReaderRepository).getNextHourToBeCalculated();
        verify(entityManager).createQuery(SELECT, HourlyAverageEntity.class);
        verify(haetq).setParameter("timeTag", nhtbc);
        verify(haetq).setMaxResults(targetSize);
        verify(haetq).getResultList();
        verify(hourlyAverageListFactory).create();
    }

}

