package br.inpe.climaespacial.swd.kp.acquisition.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;
import br.inpe.climaespacial.swd.kp.acquisition.dtos.KPDownloadHistory;
import br.inpe.climaespacial.swd.kp.acquisition.entities.KPDownloadHistoryEntity;
import br.inpe.climaespacial.swd.kp.acquisition.mappers.KPDownloadHistoryEntityMapper;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPDownloadHistoryWriterRepository.class)
public class KPDownloadHistoryWriterRepositoryTest {

    @Mock
    @Produces
    private EntityManager entityManager;

    @Mock
    @Produces
    private KPDownloadHistoryEntityMapper kpDownloadHistoryEntityMapper;
    
    @Mock
    @Produces
    private DateTimeHelper dateTimeHelper;

    @Inject
    private KPDownloadHistoryWriterRepository kpDownloadHistoryWriterRepository;

    @Test
    public void save_calledWithNull_thows() {
        RuntimeException re = null;
        try {
            kpDownloadHistoryWriterRepository.save(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"kpDownloadHistory\" cannot be null.", re.getMessage());
    }

    @Test
    public void save_calledNonExistingPeriod_persists() {
        String jpql = "SELECT kpdhe FROM KPDownloadHistoryEntity kpdhe WHERE kpdhe.period =:period";
        KPDownloadHistory kpdh = mock(KPDownloadHistory.class);
        KPDownloadHistoryEntity kpdhe = new KPDownloadHistoryEntity();

        when(kpDownloadHistoryEntityMapper.map(kpdh)).thenReturn(kpdhe);
        TypedQuery<KPDownloadHistoryEntity> tq = mockTypedQuery(KPDownloadHistoryEntity.class);
        when(entityManager.createQuery(jpql, KPDownloadHistoryEntity.class)).thenReturn(tq);
        when(tq.setParameter("period", kpdhe.getPeriod())).thenReturn(tq);
        List<KPDownloadHistoryEntity> kpdhel = mockList(KPDownloadHistoryEntity.class);
        when(tq.getResultList()).thenReturn(kpdhel);
        when(kpdhel.isEmpty()).thenReturn(true);

        kpDownloadHistoryWriterRepository.save(kpdh);

        verify(kpDownloadHistoryEntityMapper).map(kpdh);
        verify(entityManager).createQuery(jpql, KPDownloadHistoryEntity.class);
        verify(tq).setParameter("period", kpdhe.getPeriod());
        verify(tq).getResultList();
        verify(kpdhel).isEmpty();
        verify(entityManager).merge(kpdhe);
    }

    @Test
    public void save_calledExistingPeriod_merges() {
        String jpql = "SELECT kpdhe FROM KPDownloadHistoryEntity kpdhe WHERE kpdhe.period =:period";
        KPDownloadHistory kpdh = mock(KPDownloadHistory.class);
        KPDownloadHistoryEntity kpdhe1 = mock(KPDownloadHistoryEntity.class);
        ZonedDateTime period1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(kpdhe1.getPeriod()).thenReturn(period1);

        when(kpDownloadHistoryEntityMapper.map(kpdh)).thenReturn(kpdhe1);
        TypedQuery<KPDownloadHistoryEntity> tq = mockTypedQuery(KPDownloadHistoryEntity.class);
        when(entityManager.createQuery(jpql, KPDownloadHistoryEntity.class)).thenReturn(tq);
        when(tq.setParameter("period", kpdhe1.getPeriod())).thenReturn(tq);
        List<KPDownloadHistoryEntity> kpdhel = mockList(KPDownloadHistoryEntity.class);
        when(tq.getResultList()).thenReturn(kpdhel);
        when(kpdhel.isEmpty()).thenReturn(false);
        KPDownloadHistoryEntity kpdhe2 = mock(KPDownloadHistoryEntity.class);
        when(kpdhel.get(0)).thenReturn(kpdhe2);
        UUID id = UUID.fromString("672e0316-df1f-4589-a1fe-9b8abc1fc286");
        when(kpdhe2.getId()).thenReturn(id);

        kpDownloadHistoryWriterRepository.save(kpdh);

        verify(kpDownloadHistoryEntityMapper).map(kpdh);
        verify(entityManager).createQuery(jpql, KPDownloadHistoryEntity.class);
        verify(tq).setParameter("period", kpdhe1.getPeriod());
        verify(tq).getResultList();
        verify(kpdhel).isEmpty();
        verify(kpdhel).get(0);
        verify(kpdhe2).getId();
        verify(kpdhe1).setId(id);
        verify(entityManager).merge(kpdhe1);
    }

    @Test
    public void markAsCompleted_calledWithNullDate_throws() {
        RuntimeException re = null;

        try {
            kpDownloadHistoryWriterRepository.markAsCompleted(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"period\" cannot be null.", re.getMessage());
    }

    @Test
    public void markAsCompleted_calledWithNonExistingDate_throws() {
        ZonedDateTime period = ZonedDateTime.parse("2017-01-05T12:00:00z[UTC]");
        ZonedDateTime truncatedPeriod = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        String jpql = "SELECT kpdhe FROM KPDownloadHistoryEntity kpdhe WHERE kpdhe.period =:period";
        TypedQuery<KPDownloadHistoryEntity> tq = mockTypedQuery(KPDownloadHistoryEntity.class);
        when(entityManager.createQuery(jpql, KPDownloadHistoryEntity.class)).thenReturn(tq);
        when(dateTimeHelper.truncateToDays(period)).thenReturn(truncatedPeriod);
        when(tq.setParameter("period", truncatedPeriod)).thenReturn(tq);
        List<KPDownloadHistoryEntity> kpdhel1 = mockList(KPDownloadHistoryEntity.class);
        when(tq.getResultList()).thenReturn(kpdhel1);
        when(kpdhel1.isEmpty()).thenReturn(true);
        RuntimeException re = null;
        
        try {
            kpDownloadHistoryWriterRepository.markAsCompleted(period);
        } catch(RuntimeException e) {
            re = e;
        }
        
        assertNotNull(re); 
        assertEquals("The Period(" + truncatedPeriod + ") to be marked as completed does not exist", re.getMessage());
        verify(entityManager).createQuery(jpql, KPDownloadHistoryEntity.class);
        verify(dateTimeHelper).truncateToDays(period);
        verify(tq).setParameter("period", truncatedPeriod);
        verify(tq).getResultList();
        verify(kpdhel1).isEmpty();
    }

    @Test
    public void markAsCompleted_calledWithExistingDateButCompleted_doesNothing() {
        ZonedDateTime period = ZonedDateTime.parse("2017-01-05T12:00:00z[UTC]");
        ZonedDateTime truncatedPeriod = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        String jpql = "SELECT kpdhe FROM KPDownloadHistoryEntity kpdhe WHERE kpdhe.period =:period";
        TypedQuery<KPDownloadHistoryEntity> tq = mockTypedQuery(KPDownloadHistoryEntity.class);
        when(entityManager.createQuery(jpql, KPDownloadHistoryEntity.class)).thenReturn(tq);
        when(dateTimeHelper.truncateToDays(period)).thenReturn(truncatedPeriod);
        when(tq.setParameter("period", truncatedPeriod)).thenReturn(tq);
        List<KPDownloadHistoryEntity> kpdhel1 = mockList(KPDownloadHistoryEntity.class);
        when(tq.getResultList()).thenReturn(kpdhel1);
        when(kpdhel1.isEmpty()).thenReturn(false);
        KPDownloadHistoryEntity kpdhe1 = mock(KPDownloadHistoryEntity.class);
        when(kpdhel1.get(0)).thenReturn(kpdhe1);
        when(kpdhe1.isComplete()).thenReturn(true);
        
        kpDownloadHistoryWriterRepository.markAsCompleted(period);
        
        verify(entityManager).createQuery(jpql, KPDownloadHistoryEntity.class);
        verify(dateTimeHelper).truncateToDays(period);
        verify(tq).setParameter("period", truncatedPeriod);
        verify(tq).getResultList();
        verify(kpdhel1).isEmpty();
        verify(kpdhel1).get(0); 
        verify(kpdhe1).isComplete();
        verify(entityManager, never()).merge(any()); 
    }
    
    @Test
    public void markAsCompleted_calledWithExistingDate_merges() {
        ZonedDateTime period = ZonedDateTime.parse("2017-01-05T12:00:00z[UTC]");
        ZonedDateTime truncatedPeriod = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        String jpql = "SELECT kpdhe FROM KPDownloadHistoryEntity kpdhe WHERE kpdhe.period =:period";
        TypedQuery<KPDownloadHistoryEntity> tq = mockTypedQuery(KPDownloadHistoryEntity.class);
        when(entityManager.createQuery(jpql, KPDownloadHistoryEntity.class)).thenReturn(tq);
        when(dateTimeHelper.truncateToDays(period)).thenReturn(truncatedPeriod);
        when(tq.setParameter("period", truncatedPeriod)).thenReturn(tq);
        List<KPDownloadHistoryEntity> kpdhel1 = mockList(KPDownloadHistoryEntity.class);
        when(tq.getResultList()).thenReturn(kpdhel1);
        when(kpdhel1.isEmpty()).thenReturn(false);
        KPDownloadHistoryEntity kpdhe1 = mock(KPDownloadHistoryEntity.class);
        when(kpdhel1.get(0)).thenReturn(kpdhe1);
        when(kpdhe1.isComplete()).thenReturn(false);
        
        kpDownloadHistoryWriterRepository.markAsCompleted(period);
        
        verify(entityManager).createQuery(jpql, KPDownloadHistoryEntity.class);
        verify(dateTimeHelper).truncateToDays(period);
        verify(tq).setParameter("period", truncatedPeriod);
        verify(tq).getResultList();
        verify(kpdhel1).isEmpty();
        verify(kpdhel1).get(0); 
        verify(kpdhe1).isComplete();
        verify(kpdhe1).setComplete(true);
        verify(entityManager).merge(kpdhe1);  
    }
}
