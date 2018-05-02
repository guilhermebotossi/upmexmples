package br.inpe.climaespacial.swd.kp.acquisition.repositories;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;
import br.inpe.climaespacial.swd.kp.acquisition.dtos.KPDownloadHistory;
import br.inpe.climaespacial.swd.kp.acquisition.entities.KPDownloadHistoryEntity;
import br.inpe.climaespacial.swd.kp.acquisition.mappers.KPDownloadHistoryEntityMapper;

@Dependent
public class DefaultKPDownloadHistoryWriterRepository implements KPDownloadHistoryWriterRepository {
    
    private String jpql = "SELECT kpdhe FROM KPDownloadHistoryEntity kpdhe WHERE kpdhe.period =:period";
    
    @Inject
    private KPDownloadHistoryEntityMapper kpDownloadHistoryEntityMapper;
    
    @Inject
    private EntityManager entityManager;
    
    @Inject
    private DateTimeHelper dateTimeHelper;

    @Override
    @Transactional(value = TxType.REQUIRES_NEW)
    public void save(KPDownloadHistory kpDownloadHistory) {
        throwIfNull(kpDownloadHistory, "kpDownloadHistory");
        
        KPDownloadHistoryEntity kpdhe1 = kpDownloadHistoryEntityMapper.map(kpDownloadHistory);
        
        TypedQuery<KPDownloadHistoryEntity> tq = entityManager.createQuery(jpql, KPDownloadHistoryEntity.class);
        tq.setParameter("period", kpdhe1.getPeriod());
        List<KPDownloadHistoryEntity> kpdhel = tq.getResultList();
        
        if(!kpdhel.isEmpty()) {
            KPDownloadHistoryEntity kpdhe2 = kpdhel.get(0);
            kpdhe1.setId(kpdhe2.getId());
        }
        entityManager.merge(kpdhe1);
    }

    @Override
    @Transactional(value = TxType.REQUIRES_NEW)
    public void markAsCompleted(ZonedDateTime period) {
        throwIfNull(period, "period");
        
        ZonedDateTime truncatedPeriod = dateTimeHelper.truncateToDays(period);
        
        TypedQuery<KPDownloadHistoryEntity> tq = entityManager.createQuery(jpql, KPDownloadHistoryEntity.class);
        tq.setParameter("period", truncatedPeriod);
        List<KPDownloadHistoryEntity> kpdhel = tq.getResultList();
        
        if(kpdhel.isEmpty()) {
            throw new RuntimeException("The Period(" + truncatedPeriod + ") to be marked as completed does not exist");
        }
        
        KPDownloadHistoryEntity kpdhe = kpdhel.get(0);
        
        if(!kpdhe.isComplete()) {
            kpdhe.setComplete(true);
            entityManager.merge(kpdhe);
        }
    }

}
