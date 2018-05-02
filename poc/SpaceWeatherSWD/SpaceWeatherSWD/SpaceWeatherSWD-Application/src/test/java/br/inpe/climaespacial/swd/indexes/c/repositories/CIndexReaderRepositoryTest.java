package br.inpe.climaespacial.swd.indexes.c.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
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

import br.inpe.climaespacial.swd.commons.EmbraceMockito;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import br.inpe.climaespacial.swd.indexes.c.mappers.CIndexMapper;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultCIndexReaderRepository.class)
public class CIndexReaderRepositoryTest {

    private static final String QUERY = "SELECT MAX(cie.timeTag) FROM CIndexEntity cie";

    private static final String QUERY_LIST = "SELECT cie FROM CIndexEntity cie WHERE cie.timeTag >= :farthestFromNow AND cie.timeTag < :nearestFromNow ORDER BY cie.timeTag";

    @Produces
    @Mock
    private EntityManager entityManager;

    @Produces
    @Mock
    private CIndexMapper cIndexMapper;

    @Produces
    @Mock
    private ListFactory<CIndexEntity> cIndexEntityListFactory;

    @Produces
    @Mock
    private DateTimeHelper dateTimeHelper;

    @Inject
    private CIndexReaderRepository cIndexReaderRepository;
    
    @Test
    public void getLastCalculatedHour_called_returnsNull() {
        TypedQuery<ZonedDateTime> tq = EmbraceMockito.mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(QUERY, ZonedDateTime.class)).thenReturn(tq);

        ZonedDateTime zdt = null;
        List<ZonedDateTime> zdtl = Arrays.asList(zdt);
        when(tq.getResultList()).thenReturn(zdtl);

        ZonedDateTime lch = cIndexReaderRepository.getNextHourToBeCalculated();

        assertNull(lch);
    }

    @Test
    public void getLastCalculatedHour_called_returnsLastTimeTag() {
        TypedQuery<ZonedDateTime> tq = EmbraceMockito.mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(QUERY, ZonedDateTime.class)).thenReturn(tq);

        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        List<ZonedDateTime> zdtl = Arrays.asList(zdt);
        when(tq.getResultList()).thenReturn(zdtl);

        ZonedDateTime lch = cIndexReaderRepository.getNextHourToBeCalculated();

        assertNotNull(lch);
        assertEquals(zdt.plusHours(1), lch);
    }

    @Test
    public void listByPeriod_called_returnsListEmpty() {
        TypedQuery<CIndexEntity> tq = mockTypedQuery(CIndexEntity.class);
        when(entityManager.createQuery(QUERY_LIST, CIndexEntity.class)).thenReturn(tq);

        List<CIndexEntity> ciel = mockList(CIndexEntity.class);
        when(tq.getResultList()).thenReturn(ciel);

        List<CIndex> expectedCil = mockList(CIndex.class);
        when(cIndexMapper.map(ciel)).thenReturn(expectedCil);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");

        List<CIndex> cil = cIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(tq, times(1)).setParameter("farthestFromNow", ffn);
        verify(tq, times(1)).setParameter("nearestFromNow", nfn);
        verify(cIndexMapper, times(1)).map(ciel);
        assertNotNull(cil);
        assertSame(expectedCil, cil);
        assertEquals(0, cil.size());
    }

    @Test
    public void listByPeriod_calledWithDurationSmallerThan3Days_returnsList() {
        TypedQuery<CIndexEntity> tq = mockTypedQuery(CIndexEntity.class);
        when(entityManager.createQuery(QUERY_LIST, CIndexEntity.class)).thenReturn(tq);

        List<CIndexEntity> ciel = mockList(CIndexEntity.class);
        when(tq.getResultList()).thenReturn(ciel);

        List<CIndex> expectedCil = mockList(CIndex.class);
        when(cIndexMapper.map(ciel)).thenReturn(expectedCil);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-02T12:00:00z[UTC]");

        List<CIndex> cil = cIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(tq, times(1)).setParameter("farthestFromNow", ffn);
        verify(tq, times(1)).setParameter("nearestFromNow", nfn);
        verify(cIndexMapper, times(1)).map(ciel);
        assertNotNull(cil);
        assertSame(expectedCil, cil);
    }

    @Test
    public void listByPeriod_calledWithDurationEquals3Days_returnsList() {
        TypedQuery<CIndexEntity> tq = mockTypedQuery(CIndexEntity.class);
        when(entityManager.createQuery(QUERY_LIST, CIndexEntity.class)).thenReturn(tq);

        List<CIndexEntity> ciel = mockList(CIndexEntity.class);
        when(tq.getResultList()).thenReturn(ciel);

        List<CIndex> expectedCil = mockList(CIndex.class);
        when(cIndexMapper.map(ciel)).thenReturn(expectedCil);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-04T12:00:00z[UTC]");

        List<CIndex> cil = cIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(tq, times(1)).setParameter("farthestFromNow", ffn);
        verify(tq, times(1)).setParameter("nearestFromNow", nfn);
        verify(cIndexMapper, times(1)).map(ciel);
        assertNotNull(cil);
        assertSame(expectedCil, cil);
    }
    
    @Test
    public void listByPeriod_calledWithDurationBiggerThan3Days_returnsList() throws ParseException {

        String query = "SELECT cie1 FROM CIndexEntity cie1 WHERE cie1.timeTag >= :timeTag1 AND cie1.timeTag < :timeTag2 AND cie1.postValue = ("
                + "SELECT MAX(cie2.postValue) FROM CIndexEntity cie2 WHERE cie2.timeTag >= :timeTag1 AND cie2.timeTag < :timeTag2"
                + ") ORDER BY cie1.timeTag DESC";

        List<CIndexEntity> CIndexEntityList = new ArrayList<>();

        when(cIndexEntityListFactory.create()).thenReturn(CIndexEntityList);

        TypedQuery<CIndexEntity> tq = mockTypedQuery(CIndexEntity.class);
        when(entityManager.createQuery(query, CIndexEntity.class)).thenReturn(tq);

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

        List<CIndexEntity> auxCiel = Arrays.asList(mock(CIndexEntity.class));
        when(tq.getResultList()).thenReturn(auxCiel);

        List<CIndex> expectedCil = mockList(CIndex.class);
        when(cIndexMapper.map(CIndexEntityList)).thenReturn(expectedCil);

        ZonedDateTime ffn = uzdt1;
        ZonedDateTime nfn = uzdt4.plusDays(1);

        List<CIndex> cil = cIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(entityManager).createQuery(query, CIndexEntity.class);
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

        verify(cIndexMapper).map(CIndexEntityList);
        assertNotNull(cil);
        assertSame(expectedCil, cil);
    }

    @Test
    public void listByPeriod_calledWithDurationBiggerThan3DaysWithEmptyList_returns() throws ParseException {

        String query = "SELECT cie1 FROM CIndexEntity cie1 WHERE cie1.timeTag >= :timeTag1 AND cie1.timeTag < :timeTag2 AND cie1.postValue = ("
                + "SELECT MAX(cie2.postValue) FROM CIndexEntity cie2 WHERE cie2.timeTag >= :timeTag1 AND cie2.timeTag < :timeTag2"
                + ") ORDER BY cie1.timeTag DESC";

        List<CIndexEntity> CIndexEntityList = new ArrayList<>();

        when(cIndexEntityListFactory.create()).thenReturn(CIndexEntityList);

        TypedQuery<CIndexEntity> tq = mockTypedQuery(CIndexEntity.class);
        when(entityManager.createQuery(query, CIndexEntity.class)).thenReturn(tq);

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

        List<CIndexEntity> auxciel = mockList(CIndexEntity.class);
        when(auxciel.isEmpty()).thenReturn(Boolean.TRUE);
        when(tq.getResultList()).thenReturn(auxciel);

        List<CIndex> expectedCil = Arrays.asList();
        when(cIndexMapper.map(CIndexEntityList)).thenReturn(expectedCil);

        ZonedDateTime ffn = uzdt1;
        ZonedDateTime nfn = uzdt4.plusDays(1);

        List<CIndex> cil = cIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(entityManager).createQuery(query, CIndexEntity.class);
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

        verify(cIndexMapper).map(CIndexEntityList);
        assertNotNull(cil);
        assertSame(expectedCil, cil);
        assertEquals(0, cil.size());
        verify(auxciel, never()).get(0);
    }
}
