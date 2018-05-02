package br.inpe.climaespacial.swd.indexes.b.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.b.entities.BIndexEntity;
import br.inpe.climaespacial.swd.indexes.b.mappers.BIndexMapper;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultBIndexReaderRepository.class)
public class BIndexReaderRepositoryTest {

    private static final String QUERY = "SELECT MAX(bie.timeTag) FROM BIndexEntity bie";

    private static final String QUERY_LIST = "SELECT bie FROM BIndexEntity bie WHERE bie.timeTag >= :farthestFromNow AND bie.timeTag < :nearestFromNow ORDER BY bie.timeTag";

    @Produces
    @Mock
    private EntityManager entityManager;

    @Produces
    @Mock
    private BIndexMapper bIndexMapper;

    @Produces
    @Mock
    private ListFactory<BIndexEntity> bIndexEntityListFactory;

    @Produces
    @Mock
    private DateTimeHelper dateTimeHelper;

    @Inject
    private BIndexReaderRepository bIndexReaderRepository;

    @Test
    public void getLastCalculatedHour_called_returnsNull() {

        TypedQuery<ZonedDateTime> tq = mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(QUERY, ZonedDateTime.class)).thenReturn(tq);

        ZonedDateTime zdt = null;
        List<ZonedDateTime> zdtl = Arrays.asList(zdt);
        when(tq.getResultList()).thenReturn(zdtl);

        ZonedDateTime nhtbc = bIndexReaderRepository.getNextHourToBeCalculated();

        assertNull(nhtbc);
    }

    @Test
    public void getLastCalculatedHour_called_returnsLastTimeTag() {

        TypedQuery<ZonedDateTime> tq = mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(QUERY, ZonedDateTime.class)).thenReturn(tq);

        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        List<ZonedDateTime> zdtl = Arrays.asList(zdt);
        when(tq.getResultList()).thenReturn(zdtl);

        ZonedDateTime nhtbc = bIndexReaderRepository.getNextHourToBeCalculated();

        assertNotNull(nhtbc);
        assertEquals(zdt.plusHours(1), nhtbc);
    }

    @Test
    public void listByPeriod_called_returnsListEmpty() {

        TypedQuery<BIndexEntity> tq = mockTypedQuery(BIndexEntity.class);
        when(entityManager.createQuery(QUERY_LIST, BIndexEntity.class)).thenReturn(tq);

        List<BIndexEntity> biel = mockList(BIndexEntity.class);
        when(tq.getResultList()).thenReturn(biel);

        List<BIndex> expectedBil = mockList(BIndex.class);
        when(bIndexMapper.map(biel)).thenReturn(expectedBil);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");

        List<BIndex> bil = bIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(tq, times(1)).setParameter("farthestFromNow", ffn);
        verify(tq, times(1)).setParameter("nearestFromNow", nfn);
        verify(bIndexMapper, times(1)).map(biel);
        assertNotNull(bil);
        assertSame(expectedBil, bil);
        assertEquals(0, bil.size());
    }

    @Test
    public void listByPeriod_calledWithDurationSmallerThan3Days_returnsList() {

        TypedQuery<BIndexEntity> tq = mockTypedQuery(BIndexEntity.class);
        when(entityManager.createQuery(QUERY_LIST, BIndexEntity.class)).thenReturn(tq);

        List<BIndexEntity> biel = mockList(BIndexEntity.class);
        when(tq.getResultList()).thenReturn(biel);

        List<BIndex> expectedBil = mockList(BIndex.class);
        when(bIndexMapper.map(biel)).thenReturn(expectedBil);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-02T12:00:00z[UTC]");

        List<BIndex> bil = bIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(tq, times(1)).setParameter("farthestFromNow", ffn);
        verify(tq, times(1)).setParameter("nearestFromNow", nfn);
        verify(bIndexMapper, times(1)).map(biel);
        assertNotNull(bil);
        assertSame(expectedBil, bil);
    }
    
    @Test
    public void listByPeriod_calledWithDurationEquals3Days_returnsList() {

        TypedQuery<BIndexEntity> tq = mockTypedQuery(BIndexEntity.class);
        when(entityManager.createQuery(QUERY_LIST, BIndexEntity.class)).thenReturn(tq);

        List<BIndexEntity> biel = mockList(BIndexEntity.class);
        when(tq.getResultList()).thenReturn(biel);

        List<BIndex> expectedBil = mockList(BIndex.class);
        when(bIndexMapper.map(biel)).thenReturn(expectedBil);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-04T12:00:00z[UTC]");

        List<BIndex> bil = bIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(tq, times(1)).setParameter("farthestFromNow", ffn);
        verify(tq, times(1)).setParameter("nearestFromNow", nfn);
        verify(bIndexMapper, times(1)).map(biel);
        assertNotNull(bil);
        assertSame(expectedBil, bil);
    }

    @Test
    public void listByPeriod_calledWithDurationBiggerThan3Days_returnsList() throws ParseException {

        String query = "SELECT bie1 FROM BIndexEntity bie1 WHERE bie1.timeTag >= :timeTag1 AND bie1.timeTag < :timeTag2 AND bie1.postValue = ("
                + "SELECT MAX(bie2.postValue) FROM BIndexEntity bie2 WHERE bie2.timeTag >= :timeTag1 AND bie2.timeTag < :timeTag2"
                + ") ORDER BY bie1.timeTag DESC";

        List<BIndexEntity> BIndexEntityList = new ArrayList<>();

        when(bIndexEntityListFactory.create()).thenReturn(BIndexEntityList);

        TypedQuery<BIndexEntity> tq = mockTypedQuery(BIndexEntity.class);
        when(entityManager.createQuery(query, BIndexEntity.class)).thenReturn(tq);

        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]"); 
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-02T00:00:00z[UTC]");
        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-03T00:00:00z[UTC]");
        ZonedDateTime zdt4 = ZonedDateTime.parse("2017-01-04T00:00:00z[UTC]");

        ZonedDateTime uzdt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime uzdt2 = ZonedDateTime.parse("2017-01-02T12:00:00z[UTC]");
        ZonedDateTime uzdt3 = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");
        ZonedDateTime uzdt4 = ZonedDateTime.parse("2017-01-04T12:00:00z[UTC]");

        when(dateTimeHelper.truncate(uzdt1)).thenReturn(zdt1);
        when(dateTimeHelper.truncate(uzdt2)).thenReturn(zdt2);
        when(dateTimeHelper.truncate(uzdt3)).thenReturn(zdt3);
        when(dateTimeHelper.truncate(uzdt4)).thenReturn(zdt4);

        List<BIndexEntity> auxBiel = mockList(BIndexEntity.class);
        when(tq.getResultList()).thenReturn(auxBiel);

        List<BIndex> expectedBil = mockList(BIndex.class);
        when(bIndexMapper.map(BIndexEntityList)).thenReturn(expectedBil);

        ZonedDateTime ffn = uzdt1;
        ZonedDateTime nfn = uzdt4.plusDays(1);

        List<BIndex> bil = bIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(entityManager).createQuery(query, BIndexEntity.class);
        verify(tq).setMaxResults(1);

        verify(dateTimeHelper).truncate(uzdt1);
        verify(dateTimeHelper).truncate(uzdt2);
        verify(dateTimeHelper).truncate(uzdt3);
        verify(dateTimeHelper).truncate(uzdt4);

        verify(tq).setParameter("timeTag1", zdt1);
        verify(tq).setParameter("timeTag2", zdt1.plusDays(1));

        verify(tq).setParameter("timeTag1", zdt2);
        verify(tq).setParameter("timeTag2", zdt2.plusDays(1));

        verify(tq).setParameter("timeTag1", zdt3);
        verify(tq).setParameter("timeTag2", zdt3.plusDays(1));
        
        verify(tq).setParameter("timeTag1", zdt4);
        verify(tq).setParameter("timeTag2", zdt4.plusDays(1));

        verify(tq, times(4)).getResultList();

        verify(bIndexMapper).map(BIndexEntityList);
        assertNotNull(bil);
        assertSame(expectedBil, bil);
    }
 
    @Test
    public void listByPeriod_calledWithDurationBiggerThan3DaysWithEmptyList_returns() throws ParseException {

        String query = "SELECT bie1 FROM BIndexEntity bie1 WHERE bie1.timeTag >= :timeTag1 AND bie1.timeTag < :timeTag2 AND bie1.postValue = ("
                + "SELECT MAX(bie2.postValue) FROM BIndexEntity bie2 WHERE bie2.timeTag >= :timeTag1 AND bie2.timeTag < :timeTag2"
                + ") ORDER BY bie1.timeTag DESC";

        List<BIndexEntity> BIndexEntityList = new ArrayList<>();

        when(bIndexEntityListFactory.create()).thenReturn(BIndexEntityList);

        TypedQuery<BIndexEntity> tq = mockTypedQuery(BIndexEntity.class);
        when(entityManager.createQuery(query, BIndexEntity.class)).thenReturn(tq);

        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-02T00:00:00z[UTC]");
        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-03T00:00:00z[UTC]");
        ZonedDateTime zdt4 = ZonedDateTime.parse("2017-01-04T00:00:00z[UTC]");

        ZonedDateTime uzdt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime uzdt2 = ZonedDateTime.parse("2017-01-02T12:00:00z[UTC]");
        ZonedDateTime uzdt3 = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");
        ZonedDateTime uzdt4 = ZonedDateTime.parse("2017-01-04T12:00:00z[UTC]");

        when(dateTimeHelper.truncate(uzdt1)).thenReturn(zdt1);
        when(dateTimeHelper.truncate(uzdt2)).thenReturn(zdt2);
        when(dateTimeHelper.truncate(uzdt3)).thenReturn(zdt3);
        when(dateTimeHelper.truncate(uzdt4)).thenReturn(zdt4);

        List<BIndexEntity> auxbiel = mockList(BIndexEntity.class);
        when(auxbiel.isEmpty()).thenReturn(Boolean.TRUE);
        when(tq.getResultList()).thenReturn(auxbiel);

        List<BIndex> expectedBil = Arrays.asList();
        when(bIndexMapper.map(BIndexEntityList)).thenReturn(expectedBil);

        ZonedDateTime ffn = uzdt1;
        ZonedDateTime nfn = uzdt4.plusDays(1);

        List<BIndex> bil = bIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(entityManager).createQuery(query, BIndexEntity.class);
        verify(tq).setMaxResults(1);

        verify(dateTimeHelper).truncate(uzdt1);
        verify(dateTimeHelper).truncate(uzdt2);
        verify(dateTimeHelper).truncate(uzdt3);
        verify(dateTimeHelper).truncate(uzdt4);

        verify(tq).setParameter("timeTag1", zdt1);
        verify(tq).setParameter("timeTag2", zdt1.plusDays(1));

        verify(tq).setParameter("timeTag1", zdt2);
        verify(tq).setParameter("timeTag2", zdt2.plusDays(1));

        verify(tq).setParameter("timeTag1", zdt3);
        verify(tq).setParameter("timeTag2", zdt3.plusDays(1));
        
        verify(tq).setParameter("timeTag1", zdt4);
        verify(tq).setParameter("timeTag2", zdt4.plusDays(1));

        verify(tq, times(4)).getResultList();

        verify(bIndexMapper).map(BIndexEntityList);
        assertNotNull(bil);
        assertSame(expectedBil, bil);
        assertEquals(0, bil.size());
        verify(auxbiel, never()).get(0);
    }

}
