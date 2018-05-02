package br.inpe.climaespacial.swd.kp.acquisition.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;

import br.inpe.climaespacial.swd.kp.dtos.KP;
import br.inpe.climaespacial.swd.kp.entities.KPEntity;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;
import br.inpe.climaespacial.swd.kp.mappers.KPEntityMapper;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPWriterRepository.class)
public class KPWriterRepositoryTest {
    
    private String sql = "SELECT DISTINCT(kpe) FROM KPEntity kpe LEFT JOIN FETCH kpe.kpValueList WHERE kpe.timeTag = :timeTag";

    @Mock
    @Produces
    private EntityManager entityManager;

    @Mock
    @Produces
    private KPEntityMapper kpEntityMapper;

    @Inject
    private KPWriterRepository kpWriterRepository;

    @Test
    public void save_calledWithNullList_throws() {
        RuntimeException re = null;

        try {
            kpWriterRepository.save(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"kpl\" cannot be null.", re.getMessage());
    }

    @Test
    public void save_calledWithKPNotFound_persists() {
        List<KP> kpl = mockList(KP.class);
        TypedQuery<KPEntity> tqkpe = mockTypedQuery(KPEntity.class);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T01:00:00z[UTC]");
        when(entityManager.createQuery(sql, KPEntity.class)).thenReturn(tqkpe);
        KPEntity kpe1 = mock(KPEntity.class);
        when(kpe1.getTimeTag()).thenReturn(zdt1);
        KPEntity kpe2 = mock(KPEntity.class);
        when(kpe2.getTimeTag()).thenReturn(zdt2);
        List<KPEntity> kpel = Arrays.asList(kpe1, kpe2);
        when(kpEntityMapper.map(kpl)).thenReturn(kpel);
        when(tqkpe.getResultList()).thenReturn(new ArrayList<>());
        when(tqkpe.getResultList()).thenReturn(new ArrayList<>());
        KPValueEntity kpve1 = mock(KPValueEntity.class);
        KPValueEntity kpve2 = mock(KPValueEntity.class); 
        List<KPValueEntity> kpvel = Arrays.asList(kpve1, kpve2);
        when(kpe1.getKPValueList()).thenReturn(kpvel);
        when(kpe2.getKPValueList()).thenReturn(kpvel);

        kpWriterRepository.save(kpl); 

        verify(entityManager).createQuery(sql, KPEntity.class);
        verify(kpEntityMapper).map(kpl);
        verify(tqkpe).setParameter("timeTag", zdt1);
        verify(tqkpe).setParameter("timeTag", zdt2);
        verify(tqkpe, times(2)).getResultList();
        verify(entityManager).persist(kpe1);
        verify(entityManager).persist(kpe2);
        verify(entityManager, times(2)).persist(kpve1);
        verify(entityManager, times(2)).persist(kpve2);
        verify(entityManager, times(3)).flush();
        verify(entityManager).clear();
    }

    @Test
    public void save_calledWithKPIncompletedFounded_merges() {

        List<KP> kpl = mockList(KP.class);
        TypedQuery<KPEntity> tqkpe = mockTypedQuery(KPEntity.class);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T01:00:00z[UTC]");
        when(entityManager.createQuery(sql, KPEntity.class)).thenReturn(tqkpe);
        KPEntity kpe1 = mock(KPEntity.class);
        when(kpe1.getTimeTag()).thenReturn(zdt1);
        KPEntity kpe2 = mock(KPEntity.class);
        when(kpe2.getTimeTag()).thenReturn(zdt2);
        List<KPEntity> kpel = Arrays.asList(kpe1, kpe2);
        when(kpEntityMapper.map(kpl)).thenReturn(kpel);
        KPEntity kpe1Incomplete = mock(KPEntity.class);
        KPEntity kpe2Incomplete = mock(KPEntity.class);
        when(tqkpe.getResultList()).thenReturn(Arrays.asList(kpe1Incomplete)).thenReturn(Arrays.asList(kpe2Incomplete));
        UUID expectedUUID1 = UUID.fromString("672e0316-df1f-4589-a1fe-9b8abc1fc281");
        when(kpe1Incomplete.getId()).thenReturn(expectedUUID1);
        UUID expectedUUID2 = UUID.fromString("672e0316-df1f-4589-a1fe-9b8abc1fc282");
        when(kpe2Incomplete.getId()).thenReturn(expectedUUID2);
        KPValueEntity kpve1 = mock(KPValueEntity.class);
        KPValueEntity kpve2 = mock(KPValueEntity.class);
        KPValueEntity kpve3 = mock(KPValueEntity.class);
        List<KPValueEntity> kpvel1 = Arrays.asList(kpve1, kpve2);
        List<KPValueEntity> kpvel2 = Arrays.asList(kpve1, kpve2, kpve3);
        when(kpe1.getKPValueList()).thenReturn(kpvel2);
        when(kpe2.getKPValueList()).thenReturn(kpvel2);
        when(kpe1Incomplete.getKPValueList()).thenReturn(kpvel1);
        when(kpe2Incomplete.getKPValueList()).thenReturn(kpvel1);
        
        when(kpve1.getTimeTag()).thenReturn(zdt1);
        when(kpve2.getTimeTag()).thenReturn(zdt1.plusHours(3));
        when(kpve3.getTimeTag()).thenReturn(zdt1.plusHours(6));
        
        when(kpe1Incomplete.getTimeTag()).thenReturn(zdt1);
        when(kpe1Incomplete.getTimeTag()).thenReturn(zdt1.plusHours(3));
        
        when(kpe2Incomplete.getTimeTag()).thenReturn(zdt1);
        when(kpe2Incomplete.getTimeTag()).thenReturn(zdt1.plusHours(3)); 
        
        when(kpve1.getTimeTag()).thenReturn(zdt2);
        when(kpve2.getTimeTag()).thenReturn(zdt2.plusHours(3));
        when(kpve3.getTimeTag()).thenReturn(zdt2.plusHours(6));
        

        kpWriterRepository.save(kpl);

        verify(entityManager).createQuery(sql, KPEntity.class);
        verify(kpEntityMapper).map(kpl);
        verify(tqkpe).setParameter("timeTag", zdt1);
        verify(tqkpe).setParameter("timeTag", zdt2);
        verify(tqkpe, times(2)).getResultList();
        verify(kpe1).setId(expectedUUID1);
        verify(kpe2).setId(expectedUUID2);
        verify(entityManager).merge(kpe1);
        verify(entityManager).merge(kpe2);
        verify(entityManager, times(2)).merge(kpve1);
        verify(entityManager, times(2)).merge(kpve2);
        verify(entityManager, times(2)).persist(kpve3);
        verify(entityManager, times(9)).flush();
        verify(entityManager).clear();
    }

    @Test
    public void save_calledWithKPCompletedFounded_doesNothing() {

        List<KP> kpl = mockList(KP.class);
        TypedQuery<KPEntity> tqkpe = mockTypedQuery(KPEntity.class);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T01:00:00z[UTC]");
        when(entityManager.createQuery(sql, KPEntity.class)).thenReturn(tqkpe);
        KPEntity kpe1 = mock(KPEntity.class);
        when(kpe1.getTimeTag()).thenReturn(zdt1);
        KPEntity kpe2 = mock(KPEntity.class);
        when(kpe2.getTimeTag()).thenReturn(zdt2);
        List<KPEntity> kpel = Arrays.asList(kpe1, kpe2);
        when(kpEntityMapper.map(kpl)).thenReturn(kpel);
        KPEntity kpe1Completed = mock(KPEntity.class);
        KPEntity kpe2Completed = mock(KPEntity.class);
        when(tqkpe.getResultList()).thenReturn(Arrays.asList(kpe1Completed)).thenReturn(Arrays.asList(kpe2Completed));

        kpWriterRepository.save(kpl);

        verify(entityManager).createQuery(sql, KPEntity.class);
        verify(kpEntityMapper).map(kpl);
        verify(tqkpe).setParameter("timeTag", zdt1);
        verify(tqkpe).setParameter("timeTag", zdt2);
        verify(tqkpe, times(2)).getResultList();
        verify(entityManager).merge(kpe1);
        verify(entityManager).merge(kpe2);
        verify(entityManager, times(3)).flush();
        verify(entityManager).clear();
    }

}
