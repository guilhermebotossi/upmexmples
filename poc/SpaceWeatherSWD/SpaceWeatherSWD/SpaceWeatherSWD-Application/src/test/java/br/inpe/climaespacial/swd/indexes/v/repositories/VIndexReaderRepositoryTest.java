package br.inpe.climaespacial.swd.indexes.v.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
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
import br.inpe.climaespacial.swd.commons.utils.CollectionUtils;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.entities.VIndexEntity;
import br.inpe.climaespacial.swd.indexes.v.helpers.VIndexDataFillerHelper;
import br.inpe.climaespacial.swd.indexes.v.mappers.VIndexMapper;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultVIndexReaderRepository.class)
public class VIndexReaderRepositoryTest {

    private static final String QUERY = "SELECT MAX(vie.timeTag) FROM VIndexEntity vie";

    private static final String QUERY_LIST = "SELECT vie FROM VIndexEntity vie WHERE vie.timeTag >= :farthestFromNow AND vie.timeTag < :nearestFromNow ORDER BY vie.timeTag";

    @Produces
    @Mock
    private EntityManager entityManager;

    @Produces
    @Mock
    private VIndexMapper vIndexMapper;

    @Produces
    @Mock
    private ListFactory<VIndexEntity> vIndexEntityListFactory;

    @Produces
    @Mock
    private DateTimeHelper dateTimeHelper;

    @Mock
    @Produces
    private VIndexDataFillerHelper vIndexDataFillerHelper;
    
    @Mock
    @Produces
    private CollectionUtils collectionUtils; 

    @Inject
    private VIndexReaderRepository vIndexReaderRepository;

    @Test
    public void getLastCalculatedHour_called_returnsNull() {

        TypedQuery<ZonedDateTime> tq = EmbraceMockito.mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(QUERY, ZonedDateTime.class)).thenReturn(tq);

        ZonedDateTime zdt = null;
        List<ZonedDateTime> zdtl = Arrays.asList(zdt);
        when(tq.getResultList()).thenReturn(zdtl);

        ZonedDateTime lch = vIndexReaderRepository.getNextHourToBeCalculated();

        assertNull(lch);
    }

    @Test
    public void getLastCalculatedHour_called_returnsLastTimeTag() {

        TypedQuery<ZonedDateTime> tq = EmbraceMockito.mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(QUERY, ZonedDateTime.class)).thenReturn(tq);

        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        List<ZonedDateTime> zdtl = Arrays.asList(zdt);
        when(tq.getResultList()).thenReturn(zdtl);

        ZonedDateTime lch = vIndexReaderRepository.getNextHourToBeCalculated();

        assertNotNull(lch);
        assertEquals(zdt.plusHours(1), lch);
    }

    @Test
    public void listByPeriod_called_returnsListEmpty() {

        TypedQuery<VIndexEntity> tq = mockTypedQuery(VIndexEntity.class);
        when(entityManager.createQuery(QUERY_LIST, VIndexEntity.class)).thenReturn(tq);

        List<VIndexEntity> viel = mockList(VIndexEntity.class);
        when(tq.getResultList()).thenReturn(viel);

        List<VIndex> expectedVil = mockList(VIndex.class);
        when(vIndexMapper.map(viel)).thenReturn(expectedVil);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]");

        List<VIndex> vil = vIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(tq).setParameter("farthestFromNow", ffn);
        verify(tq).setParameter("nearestFromNow", nfn);
        verify(vIndexMapper).map(viel);
        assertNotNull(vil);
        assertSame(expectedVil, vil);
        assertEquals(0, vil.size());
    }

    @Test
    public void listByPeriod_calledWithDurationSmallerThan3Days_returnsList() {

        TypedQuery<VIndexEntity> tq = mockTypedQuery(VIndexEntity.class);
        when(entityManager.createQuery(QUERY_LIST, VIndexEntity.class)).thenReturn(tq);

        List<VIndexEntity> viel = mockList(VIndexEntity.class);
        when(tq.getResultList()).thenReturn(viel);

        List<VIndex> expectedVil = mockList(VIndex.class);
        when(vIndexMapper.map(viel)).thenReturn(expectedVil);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-02T12:00:00z[UTC]");

        List<VIndex> vil = vIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(tq).setParameter("farthestFromNow", ffn);
        verify(tq).setParameter("nearestFromNow", nfn);
        verify(vIndexMapper).map(viel);
        assertNotNull(vil);
        assertSame(expectedVil, vil);
    }
    
    @Test
    public void listByPeriod_calledWithDurationEquals3Days_returnsList() {

        TypedQuery<VIndexEntity> tq = mockTypedQuery(VIndexEntity.class);
        when(entityManager.createQuery(QUERY_LIST, VIndexEntity.class)).thenReturn(tq);

        List<VIndexEntity> viel = mockList(VIndexEntity.class);
        when(tq.getResultList()).thenReturn(viel);

        List<VIndex> expectedVil = mockList(VIndex.class);
        when(vIndexMapper.map(viel)).thenReturn(expectedVil);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-04T12:00:00z[UTC]");

        List<VIndex> vil = vIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(tq).setParameter("farthestFromNow", ffn);
        verify(tq).setParameter("nearestFromNow", nfn);
        verify(vIndexMapper).map(viel);
        assertNotNull(vil);
        assertSame(expectedVil, vil);
    }

    @Test
    public void listByPeriod_calledWithDurationBiggerThan3Days_returnsList() throws ParseException {

        String query = "SELECT vie1 FROM VIndexEntity vie1 WHERE vie1.timeTag >= :timeTag1 AND vie1.timeTag < :timeTag2 AND vie1.postValue = ("
                + "SELECT MAX(vie2.postValue) FROM VIndexEntity vie2 WHERE vie2.timeTag >= :timeTag1 AND vie2.timeTag < :timeTag2"
                + ") ORDER BY vie1.timeTag DESC";

        List<VIndexEntity> vIndexEntityList = new ArrayList<>();

        when(vIndexEntityListFactory.create()).thenReturn(vIndexEntityList);

        TypedQuery<VIndexEntity> tq = mockTypedQuery(VIndexEntity.class);
        when(entityManager.createQuery(query, VIndexEntity.class)).thenReturn(tq);

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

        List<VIndexEntity> auxViel = mockList(VIndexEntity.class);
        when(tq.getResultList()).thenReturn(auxViel);

        List<VIndex> expectedVil = mockList(VIndex.class);
        when(vIndexMapper.map(vIndexEntityList)).thenReturn(expectedVil);

        ZonedDateTime ffn = uzdt1;
        ZonedDateTime nfn = uzdt4.plusDays(1);

        List<VIndex> vil = vIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(entityManager).createQuery(query, VIndexEntity.class);
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

        verify(vIndexMapper).map(vIndexEntityList);
        assertNotNull(vil);
        assertSame(expectedVil, vil);
    }

    @Test
    public void listByPeriod_calledWithDurationBiggerThan3DaysWithEmptyList_returns() throws ParseException {

        String query = "SELECT vie1 FROM VIndexEntity vie1 WHERE vie1.timeTag >= :timeTag1 AND vie1.timeTag < :timeTag2 AND vie1.postValue = ("
                + "SELECT MAX(vie2.postValue) FROM VIndexEntity vie2 WHERE vie2.timeTag >= :timeTag1 AND vie2.timeTag < :timeTag2"
                + ") ORDER BY vie1.timeTag DESC";

        List<VIndexEntity> vIndexEntityList = new ArrayList<>();

        when(vIndexEntityListFactory.create()).thenReturn(vIndexEntityList);

        TypedQuery<VIndexEntity> tq = mockTypedQuery(VIndexEntity.class);
        when(entityManager.createQuery(query, VIndexEntity.class)).thenReturn(tq);

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

        List<VIndexEntity> auxViel = mockList(VIndexEntity.class);
        when(auxViel.isEmpty()).thenReturn(Boolean.TRUE);
        when(tq.getResultList()).thenReturn(auxViel);

        List<VIndex> expectedVil = Arrays.asList();
        when(vIndexMapper.map(vIndexEntityList)).thenReturn(expectedVil);

        ZonedDateTime ffn = uzdt1;
        ZonedDateTime nfn = uzdt4.plusDays(1);

        List<VIndex> vil = vIndexReaderRepository.listByPeriod(ffn, nfn);

        verify(entityManager).createQuery(query, VIndexEntity.class);
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

        verify(vIndexMapper).map(vIndexEntityList);
        assertNotNull(vil);
        assertSame(expectedVil, vil);
        assertEquals(0, vil.size());
        verify(auxViel, never()).get(0);
    }

    @Test
    public void getLastVIndexesFromDateTime_called_succeeds() {
        String sql = "SELECT ve FROM VIndexEntity ve WHERE ve.timeTag < :timeTag ORDER BY ve.timeTag DESC";
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T11:00:00z[UTC]");
        int targetSize = 24;

        TypedQuery<VIndexEntity> tq = mockTypedQuery(VIndexEntity.class);
        when(entityManager.createQuery(sql, VIndexEntity.class)).thenReturn(tq);
        when(tq.setMaxResults(targetSize)).thenReturn(tq);
        when(tq.setParameter("timeTag", zdt1)).thenReturn(tq);
        List<VIndexEntity> viel1 = mockList(VIndexEntity.class);
        when(tq.getResultList()).thenReturn(viel1);
        List<VIndex> vil1 = mockList(VIndex.class);
        when(vIndexMapper.map(viel1)).thenReturn(vil1);
        List<VIndex> vil2 = mockList(VIndex.class);
        ZonedDateTime zdt3 = zdt2.minusHours(targetSize - 1); 
        
        
        when(vIndexDataFillerHelper.fillByHoursAnyway(zdt3, zdt2, vil1)).thenReturn(vil2);
        when(vil2.isEmpty()).thenReturn(false);
        when(vil2.size()).thenReturn(targetSize);

        List<VIndex> vil3 = vIndexReaderRepository.getLastVIndexesFromDateTime(zdt1); 

        assertNotNull(vil3);
        assertThat(vil3, is(not(empty())));
        assertThat(vil3, hasSize(targetSize));
        verify(entityManager).createQuery(sql, VIndexEntity.class);
        verify(tq).setParameter("timeTag", zdt1);
        verify(tq).setMaxResults(targetSize);
        verify(tq).getResultList();
        verify(collectionUtils).reverse(viel1);
        verify(vIndexMapper).map(viel1);
        verify(vIndexDataFillerHelper).fillByHoursAnyway(zdt3, zdt2, vil1);
    }

}
