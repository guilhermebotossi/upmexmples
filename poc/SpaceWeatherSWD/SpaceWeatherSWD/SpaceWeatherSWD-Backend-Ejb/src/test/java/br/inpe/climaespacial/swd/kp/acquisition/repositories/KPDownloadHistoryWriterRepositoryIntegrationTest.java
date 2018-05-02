package br.inpe.climaespacial.swd.kp.acquisition.repositories;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.acquisition.providers.DefaultDateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.helpers.DefaultDateTimeHelper;
import br.inpe.climaespacial.swd.commons.providers.DefaultUUIDProvider;
import br.inpe.climaespacial.swd.kp.acquisition.dtos.KPDownloadHistory;
import br.inpe.climaespacial.swd.kp.acquisition.entities.KPDownloadHistoryEntity;
import br.inpe.climaespacial.swd.kp.acquisition.factories.DefaultKPDownloadHistoryEntityFactory;
import br.inpe.climaespacial.swd.kp.acquisition.mappers.DefaultKPDownloadHistoryEntityMapper;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;


@RunWith(CdiRunner.class)
@AdditionalClasses({
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class,
    HelperFactoryProducer.class,
    DefaultDateTimeProvider.class,
    DefaultUUIDProvider.class,
    DefaultKPDownloadHistoryWriterRepository.class,
    DefaultKPDownloadHistoryEntityMapper.class,
    DefaultKPDownloadHistoryEntityFactory.class,
    DefaultDateTimeHelper.class
})
public class KPDownloadHistoryWriterRepositoryIntegrationTest extends BaseIntegrationTest {
    
    @Inject
    private EntityManager entityManager;
    
    @Inject
    private KPDownloadHistoryWriterRepository kpDownloadHistoryWriterRepository;
    
    @Test
    @InRequestScope
    public void save_calledExisting_merges(){
        ZonedDateTime period = ZonedDateTime.parse("2017-05-01T00:00:00z[UTC]");
        KPDownloadHistoryEntity kpdhe1 = createKPDownloadHistoryEntity(period);
        entityManager.persist(kpdhe1);
        
        KPDownloadHistory kpdh = createKPDownloadHistory(ZonedDateTime.parse("2017-05-06T00:00:00z[UTC]"));
        
        kpDownloadHistoryWriterRepository.save(kpdh); 
        
        String jpql = "SELECT kpdhe FROM KPDownloadHistoryEntity kpdhe";
        TypedQuery<KPDownloadHistoryEntity> tq = entityManager.createQuery(jpql, KPDownloadHistoryEntity.class);
        List<KPDownloadHistoryEntity> kpdhel = tq.getResultList();
        
        assertNotNull(kpdhel);
        assertThat(kpdhel, hasSize(1));
        KPDownloadHistoryEntity kpdhe2 = kpdhel.get(0);
        assertNotNull(kpdhe2);
        assertEquals(period, kpdhe2.getPeriod());
        assertTrue(kpdhe2.isComplete());
    }
    
    @Test
    @InRequestScope
    public void save_calledNonExisting_persists(){
        KPDownloadHistory kpdh = createKPDownloadHistory(ZonedDateTime.parse("2017-05-06T00:00:00z[UTC]"));
        
        kpDownloadHistoryWriterRepository.save(kpdh); 
        
        String jpql = "SELECT kpdhe FROM KPDownloadHistoryEntity kpdhe";
        TypedQuery<KPDownloadHistoryEntity> tq = entityManager.createQuery(jpql, KPDownloadHistoryEntity.class);
        List<KPDownloadHistoryEntity> kpdhel = tq.getResultList();
        
        assertNotNull(kpdhel);
        assertThat(kpdhel, hasSize(1));
        KPDownloadHistoryEntity kpdhe2 = kpdhel.get(0);
        assertNotNull(kpdhe2);
        ZonedDateTime period = ZonedDateTime.parse("2017-05-01T00:00:00z[UTC]");
        assertEquals(period, kpdhe2.getPeriod());
        assertTrue(kpdhe2.isComplete());
    }

    @Test
    @InRequestScope
    public void markAsCompleted_called_succeeds() {
        ZonedDateTime period = ZonedDateTime.parse("2017-05-01T00:00:00z[UTC]");
        KPDownloadHistoryEntity kpdhe1 = createKPDownloadHistoryEntity(period);
        entityManager.persist(kpdhe1);
        
        kpDownloadHistoryWriterRepository.markAsCompleted(ZonedDateTime.parse("2017-05-05T13:50:00z[UTC]"));
        
        String jpql = "SELECT kpdhe FROM KPDownloadHistoryEntity kpdhe";
        TypedQuery<KPDownloadHistoryEntity> tq = entityManager.createQuery(jpql, KPDownloadHistoryEntity.class);
        List<KPDownloadHistoryEntity> kpdhel = tq.getResultList();
        
        assertNotNull(kpdhel);
        assertThat(kpdhel, hasSize(1));
        KPDownloadHistoryEntity kpdhe2 = kpdhel.get(0);
        assertNotNull(kpdhe2);
        assertEquals(period, kpdhe2.getPeriod());
        assertTrue(kpdhe2.isComplete());
    }

    private KPDownloadHistoryEntity createKPDownloadHistoryEntity(ZonedDateTime period) {
        KPDownloadHistoryEntity kpdhe = new KPDownloadHistoryEntity();
        kpdhe.setId(UUID.randomUUID());
        kpdhe.setCreationDate(ZonedDateTime.now());
        kpdhe.setModificationDate(ZonedDateTime.now());
        kpdhe.setPeriod(period);
        kpdhe.setComplete(false);
        
        return kpdhe;
    }

    private KPDownloadHistory createKPDownloadHistory(ZonedDateTime period) {
        KPDownloadHistory kpdh = new KPDownloadHistory();
        kpdh.setPeriod(period);
        kpdh.setComplete(true);
        
        return kpdh;
    }
}
