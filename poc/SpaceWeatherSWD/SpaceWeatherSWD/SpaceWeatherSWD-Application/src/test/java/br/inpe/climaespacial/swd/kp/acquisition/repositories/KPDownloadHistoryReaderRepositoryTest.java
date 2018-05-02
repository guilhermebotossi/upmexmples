package br.inpe.climaespacial.swd.kp.acquisition.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
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

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPDownloadHistoryReaderRepository.class)
public class KPDownloadHistoryReaderRepositoryTest {
    String jpql1 = "SELECT kpdhe.period FROM KPDownloadHistoryEntity kpdhe WHERE kpdhe.complete = FALSE ORDER BY kpdhe.period ASC";
    String jpql2 = "SELECT kpdhe.period FROM KPDownloadHistoryEntity kpdhe WHERE kpdhe.complete = TRUE ORDER BY kpdhe.period DESC";
    
    
    @Mock
    @Produces
    private EntityManager entityManager;
    
    @Inject 
    private KPDownloadHistoryReaderRepository kpDownloadHistoryReaderRepository; 
    
    @Test
    public void getNextDateToBeDownloaded_called_returnsNull() {
        TypedQuery<ZonedDateTime> tq1 = mockTypedQuery(ZonedDateTime.class);  
        when(entityManager.createQuery(jpql1, ZonedDateTime.class)).thenReturn(tq1);
        List<ZonedDateTime> zdtl1 = mockList(ZonedDateTime.class);
        when(tq1.setMaxResults(1)).thenReturn(tq1);
        when(tq1.getResultList()).thenReturn(zdtl1);
        when(zdtl1.isEmpty()).thenReturn(true);
        
        TypedQuery<ZonedDateTime> tq2 = mockTypedQuery(ZonedDateTime.class);  
        when(entityManager.createQuery(jpql2, ZonedDateTime.class)).thenReturn(tq2);
        when(tq2.setMaxResults(1)).thenReturn(tq2);
        List<ZonedDateTime> zdtl2 = mockList(ZonedDateTime.class);
        when(tq2.getResultList()).thenReturn(zdtl2);
        when(zdtl2.isEmpty()).thenReturn(true);
        
        ZonedDateTime mzdt  = kpDownloadHistoryReaderRepository.getNextDateToBeDownloaded(); 
         
        assertNull(mzdt);
        verify(entityManager).createQuery(jpql1, ZonedDateTime.class);
        verify(tq1).setMaxResults(1);
        verify(tq1).getResultList(); 
        verify(zdtl1).isEmpty();
        verify(entityManager).createQuery(jpql2, ZonedDateTime.class);
        verify(tq2).setMaxResults(1);
        verify(tq2).getResultList(); 
        verify(zdtl2).isEmpty();
    }
    
    
    @Test
    public void getNextDateToBeDownloaded_called_returnsDateTime() {
        
        TypedQuery<ZonedDateTime> tq1 = mockTypedQuery(ZonedDateTime.class);  
        when(entityManager.createQuery(jpql1, ZonedDateTime.class)).thenReturn(tq1);
        List<ZonedDateTime> zdtl1 = mockList(ZonedDateTime.class);
        when(tq1.setMaxResults(1)).thenReturn(tq1);
        when(tq1.getResultList()).thenReturn(zdtl1);
        when(zdtl1.isEmpty()).thenReturn(true);
        
        TypedQuery<ZonedDateTime> tq2 = mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(jpql2, ZonedDateTime.class)).thenReturn(tq2);
        List<ZonedDateTime> zdtl2 = mockList(ZonedDateTime.class);
        when(tq2.setMaxResults(1)).thenReturn(tq2);
        when(tq2.getResultList()).thenReturn(zdtl2);
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(zdtl2.isEmpty()).thenReturn(false);
        when(zdtl2.get(0)).thenReturn(timeTag);
        
        ZonedDateTime mzdt  = kpDownloadHistoryReaderRepository.getNextDateToBeDownloaded();
        
        assertNotNull(mzdt);
        assertSame(timeTag, mzdt);
        verify(entityManager).createQuery(jpql1, ZonedDateTime.class);
        verify(tq1).setMaxResults(1);
        verify(tq1).getResultList(); 
        verify(zdtl1).isEmpty();
        verify(entityManager).createQuery(jpql2, ZonedDateTime.class);
        verify(tq2).setMaxResults(1);
        verify(tq2).getResultList();
        verify(zdtl2).isEmpty();
        verify(zdtl2).get(0);
    } 
    
    
    @Test
    public void getNextDateToBeDownloaded_called_returnsDateTime2() {
        TypedQuery<ZonedDateTime> tq = mockTypedQuery(ZonedDateTime.class);
        when(entityManager.createQuery(jpql1, ZonedDateTime.class)).thenReturn(tq);
        List<ZonedDateTime> zdtl = mockList(ZonedDateTime.class);
        when(tq.setMaxResults(1)).thenReturn(tq);
        when(tq.getResultList()).thenReturn(zdtl);
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime timeTag2 = ZonedDateTime.parse("2017-01-01T11:00:00z[UTC]");
        when(zdtl.isEmpty()).thenReturn(false);
        when(zdtl.get(0)).thenReturn(timeTag);
        
        ZonedDateTime mzdt  = kpDownloadHistoryReaderRepository.getNextDateToBeDownloaded();
        
        assertNotNull(mzdt);
        assertEquals(timeTag2, mzdt);
        verify(entityManager).createQuery(jpql1, ZonedDateTime.class);
        verify(tq).setMaxResults(1);
        verify(tq).getResultList();
        verify(zdtl).isEmpty();
        verify(zdtl).get(0);
    }

}
