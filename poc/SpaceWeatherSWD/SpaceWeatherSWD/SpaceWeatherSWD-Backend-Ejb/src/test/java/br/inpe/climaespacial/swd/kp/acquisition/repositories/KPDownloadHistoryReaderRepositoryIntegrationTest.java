package br.inpe.climaespacial.swd.kp.acquisition.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.ZonedDateTime;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.kp.acquisition.entities.KPDownloadHistoryEntity;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class,
    HelperFactoryProducer.class,
    DefaultKPDownloadHistoryReaderRepository.class
})
public class KPDownloadHistoryReaderRepositoryIntegrationTest extends BaseIntegrationTest {
    
    @Inject
    private EntityManager entityManager;
    
    @Inject 
    private KPDownloadHistoryReaderRepository kpLastRecordRepository;  
    
    @Test
    @InRequestScope
    public void getNextDateToBeDownloaded_called_returnsNull() {
        
        ZonedDateTime last = kpLastRecordRepository.getNextDateToBeDownloaded();  
        
        assertNull(last);
    } 
    
    @Test 
    @InRequestScope  
    public void getNextDateToBeDownloaded_calledWithNoCompeletes_returnsLastComplete() {
        KPDownloadHistoryEntity kpe = new KPDownloadHistoryEntity();
        kpe.setId(UUID.randomUUID());
        kpe.setCreationDate(ZonedDateTime.now());
        kpe.setModificationDate(ZonedDateTime.now());
        ZonedDateTime timeTag1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        kpe.setPeriod(timeTag1);
        kpe.setComplete(false);

        entityManager.persist(kpe);
        
        KPDownloadHistoryEntity kpe2 = new KPDownloadHistoryEntity();
        kpe2.setId(UUID.randomUUID());
        kpe2.setCreationDate(ZonedDateTime.now());
        kpe2.setModificationDate(ZonedDateTime.now());
        ZonedDateTime timeTag2 = ZonedDateTime.parse("2015-01-01T11:00:00z[UTC]");
        kpe2.setPeriod(ZonedDateTime.parse("2015-01-01T12:00:00z[UTC]"));
        kpe2.setComplete(false);

        entityManager.persist(kpe2);
        
        
        ZonedDateTime last = kpLastRecordRepository.getNextDateToBeDownloaded(); 
        
        assertNotNull(last);
        assertEquals(timeTag2, last);
    } 
    
    @Test 
    @InRequestScope  
    public void getNextDateToBeDownloaded_called_returnsFirstComplete() {
        KPDownloadHistoryEntity kpe = new KPDownloadHistoryEntity();
        kpe.setId(UUID.randomUUID());
        kpe.setCreationDate(ZonedDateTime.now());
        kpe.setModificationDate(ZonedDateTime.now());
        ZonedDateTime timeTag1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        kpe.setPeriod(timeTag1); 
        kpe.setComplete(true);

        entityManager.persist(kpe);
        
        KPDownloadHistoryEntity kpe2 = new KPDownloadHistoryEntity();
        kpe2.setId(UUID.randomUUID());
        kpe2.setCreationDate(ZonedDateTime.now());
        kpe2.setModificationDate(ZonedDateTime.now());
        ZonedDateTime timeTag2 = ZonedDateTime.parse("2015-01-01T12:00:00z[UTC]");
        kpe2.setPeriod(timeTag2);
        kpe2.setComplete(true);

        entityManager.persist(kpe2);
        
        
        ZonedDateTime last = kpLastRecordRepository.getNextDateToBeDownloaded(); 
        
        assertNotNull(last);
        assertEquals(timeTag1, last);
    } 
    
    @Test 
    @InRequestScope  
    public void getNextDateToBeDownloaded_called_returnsComplete() {
        KPDownloadHistoryEntity kpe = new KPDownloadHistoryEntity();
        kpe.setId(UUID.randomUUID());
        kpe.setCreationDate(ZonedDateTime.now());
        kpe.setModificationDate(ZonedDateTime.now());
        ZonedDateTime timeTag1 = ZonedDateTime.parse("2017-01-01T11:00:00z[UTC]");
        kpe.setPeriod(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        kpe.setComplete(false);

        entityManager.persist(kpe);
        
        KPDownloadHistoryEntity kpe2 = new KPDownloadHistoryEntity();
        kpe2.setId(UUID.randomUUID());
        kpe2.setCreationDate(ZonedDateTime.now());
        kpe2.setModificationDate(ZonedDateTime.now());
        ZonedDateTime timeTag2 = ZonedDateTime.parse("2015-01-01T12:00:00z[UTC]");
        kpe2.setPeriod(timeTag2);
        kpe2.setComplete(true);

        entityManager.persist(kpe2);
        
        
        ZonedDateTime last = kpLastRecordRepository.getNextDateToBeDownloaded(); 
        
        assertNotNull(last);
        assertEquals(timeTag1, last); 
    } 

}
