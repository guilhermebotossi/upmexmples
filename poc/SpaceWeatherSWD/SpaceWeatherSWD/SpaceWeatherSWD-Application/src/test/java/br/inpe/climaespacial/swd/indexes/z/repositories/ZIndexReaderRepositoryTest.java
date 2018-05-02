package br.inpe.climaespacial.swd.indexes.z.repositories;

import br.inpe.climaespacial.swd.commons.EmbraceMockito;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import br.inpe.climaespacial.swd.indexes.z.entities.ZIndexEntity;
import br.inpe.climaespacial.swd.indexes.z.mappers.ZIndexMapper;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultZIndexReaderRepository.class)
public class ZIndexReaderRepositoryTest {

    private static final String QUERY = "SELECT MAX(zie.timeTag) FROM ZIndexEntity zie";

    private static final String QUERY_LIST = "SELECT zie FROM ZIndexEntity zie WHERE zie.timeTag >= :farthestFromNow AND zie.timeTag < :nearestFromNow ORDER BY zie.timeTag";

    @Produces
    @Mock
    private EntityManager entityManager;

    @Inject
    private ZIndexReaderRepository zIndexReaderRepository;

    @Produces
    @Mock
    private ZIndexMapper zIndexMapper;

    @Produces
    @Mock
    private ListFactory<ZIndexEntity> zIndexEntityListFactory;

    @Produces
    @Mock
    private DateTimeHelper dateTimeHelper;

    @Test
    public void getLastCalculatedHour_called_returnsNull() {

        TypedQuery<ZonedDateTime> tq = EmbraceMockito.mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(QUERY, ZonedDateTime.class)).thenReturn(tq);

        ZonedDateTime zdt = null;
        List<ZonedDateTime> zdtl = Arrays.asList(zdt);
        when(tq.getResultList()).thenReturn(zdtl);

        ZonedDateTime lch = zIndexReaderRepository.getNextHourToBeCalculated();

        assertNull(lch);
    }

    @Test
    public void getLastCalculatedHour_called_returnsLastTimeTag() {

        TypedQuery<ZonedDateTime> tq = EmbraceMockito.mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(QUERY, ZonedDateTime.class)).thenReturn(tq);

        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        List<ZonedDateTime> zdtl = Arrays.asList(zdt);
        when(tq.getResultList()).thenReturn(zdtl);

        ZonedDateTime lch = zIndexReaderRepository.getNextHourToBeCalculated();

        assertNotNull(lch);
        assertEquals(zdt.plusHours(1), lch);
    }

    @Test
    public void listByPeriod_called_returnsListEmpty() {

        TypedQuery<ZIndexEntity> tq = mockTypedQuery(ZIndexEntity.class);
        when(entityManager.createQuery(QUERY_LIST, ZIndexEntity.class)).thenReturn(tq);

        List<ZIndexEntity> ziel = mockList(ZIndexEntity.class);
        when(tq.getResultList()).thenReturn(ziel);

        List<ZIndex> expectedZil = mockList(ZIndex.class);
        when(zIndexMapper.map(ziel)).thenReturn(expectedZil);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");

        List<ZIndex> zil = zIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(tq).setParameter("farthestFromNow", ffn);
        verify(tq).setParameter("nearestFromNow", nfn);
        verify(zIndexMapper).map(ziel);
        assertNotNull(zil);
        assertSame(expectedZil, zil);
        assertEquals(0, zil.size());
    }

    @Test
    public void listByPeriod_calledWithDurationSmallerThan3Days_returnsList() {

        TypedQuery<ZIndexEntity> tq = mockTypedQuery(ZIndexEntity.class);
        when(entityManager.createQuery(QUERY_LIST, ZIndexEntity.class)).thenReturn(tq);

        List<ZIndexEntity> ziel = mockList(ZIndexEntity.class);
        when(tq.getResultList()).thenReturn(ziel);

        List<ZIndex> expectedZil = mockList(ZIndex.class);
        when(zIndexMapper.map(ziel)).thenReturn(expectedZil);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-02T12:00:00z[UTC]");

        List<ZIndex> zil = zIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(tq).setParameter("farthestFromNow", ffn);
        verify(tq).setParameter("nearestFromNow", nfn);
        verify(zIndexMapper).map(ziel);
        assertNotNull(zil);
        assertSame(expectedZil, zil);
    }

    @Test
    public void listByPeriod_calledWithDurationEquals3Days_returnsList() {

        TypedQuery<ZIndexEntity> tq = mockTypedQuery(ZIndexEntity.class);
        when(entityManager.createQuery(QUERY_LIST, ZIndexEntity.class)).thenReturn(tq);

        List<ZIndexEntity> ziel = mockList(ZIndexEntity.class);
        when(tq.getResultList()).thenReturn(ziel);

        List<ZIndex> expectedZil = mockList(ZIndex.class);
        when(zIndexMapper.map(ziel)).thenReturn(expectedZil);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-04T12:00:00z[UTC]");

        List<ZIndex> zil = zIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(tq).setParameter("farthestFromNow", ffn);
        verify(tq).setParameter("nearestFromNow", nfn);
        verify(zIndexMapper).map(ziel);
        assertNotNull(zil);
        assertSame(expectedZil, zil);
    }

    @Test
    public void listByPeriod_calledWithDurationBiggerThan3Days_returnsList() throws ParseException {

        String query = "SELECT zie1 FROM ZIndexEntity zie1 WHERE zie1.timeTag >= :timeTag1 AND zie1.timeTag < :timeTag2 AND zie1.postValue = ("
                + "SELECT MAX(zie2.postValue) FROM ZIndexEntity zie2 WHERE zie2.timeTag >= :timeTag1 AND zie2.timeTag < :timeTag2"
                + ") ORDER BY zie1.timeTag DESC";

        List<ZIndexEntity> zIndexEntityList = new ArrayList<>();

        when(zIndexEntityListFactory.create()).thenReturn(zIndexEntityList);

        TypedQuery<ZIndexEntity> tq = mockTypedQuery(ZIndexEntity.class);
        when(entityManager.createQuery(query, ZIndexEntity.class)).thenReturn(tq);

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

        List<ZIndexEntity> auxZiel = mockList(ZIndexEntity.class);
        when(tq.getResultList()).thenReturn(auxZiel);

        List<ZIndex> expectedZil = mockList(ZIndex.class);
        when(zIndexMapper.map(zIndexEntityList)).thenReturn(expectedZil);

        ZonedDateTime ffn = uzdt1;
        ZonedDateTime nfn = uzdt4.plusDays(1);

        List<ZIndex> zil = zIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(entityManager).createQuery(query, ZIndexEntity.class);
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

        verify(zIndexMapper).map(zIndexEntityList);
        assertNotNull(zil);
        assertSame(expectedZil, zil);
    }

    @Test
    public void listByPeriod_calledWithDurationBiggerThan3DaysWithEmptyList_returns() throws ParseException {

        String query = "SELECT zie1 FROM ZIndexEntity zie1 WHERE zie1.timeTag >= :timeTag1 AND zie1.timeTag < :timeTag2 AND zie1.postValue = ("
                + "SELECT MAX(zie2.postValue) FROM ZIndexEntity zie2 WHERE zie2.timeTag >= :timeTag1 AND zie2.timeTag < :timeTag2"
                + ") ORDER BY zie1.timeTag DESC";

        List<ZIndexEntity> zIndexEntityList = new ArrayList<>();

        when(zIndexEntityListFactory.create()).thenReturn(zIndexEntityList);

        TypedQuery<ZIndexEntity> tq = mockTypedQuery(ZIndexEntity.class);
        when(entityManager.createQuery(query, ZIndexEntity.class)).thenReturn(tq);

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

        List<ZIndexEntity> auxZiel = mockList(ZIndexEntity.class);
        when(auxZiel.isEmpty()).thenReturn(Boolean.TRUE);
        when(tq.getResultList()).thenReturn(auxZiel);

        List<ZIndex> expectedZil = Arrays.asList();
        when(zIndexMapper.map(zIndexEntityList)).thenReturn(expectedZil);

        ZonedDateTime ffn = uzdt1;
        ZonedDateTime nfn = uzdt4.plusDays(1);

        List<ZIndex> zil = zIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(entityManager).createQuery(query, ZIndexEntity.class);
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

        verify(zIndexMapper).map(zIndexEntityList);
        assertNotNull(zil);
        assertSame(expectedZil, zil);
        assertEquals(0, zil.size());
        verify(auxZiel, never()).get(0);
    }

}
