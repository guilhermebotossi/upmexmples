package br.inpe.climaespacial.swd.kp.acquisition.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultKPDownloadHistoryReaderRepository implements KPDownloadHistoryReaderRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    public ZonedDateTime getNextDateToBeDownloaded() {
        ZonedDateTime zdt = null;
        
        return (zdt = getIfNotEmpty(getFirstIncomplete())) == null ? getIfNotEmpty(getLastComplete()) : zdt.minusHours(1);
    }

    private ZonedDateTime getIfNotEmpty(List<ZonedDateTime> zdtl2) {
        return !zdtl2.isEmpty() ? zdtl2.get(0) : null;
    }

    private List<ZonedDateTime> getFirstIncomplete() {
        String jpql = "SELECT kpdhe.period FROM KPDownloadHistoryEntity kpdhe WHERE kpdhe.complete = FALSE ORDER BY kpdhe.period ASC";
        TypedQuery<ZonedDateTime> tq = entityManager.createQuery(jpql, ZonedDateTime.class);
        tq.setMaxResults(1);
        List<ZonedDateTime> zdtl = tq.getResultList();
        return zdtl;
    }

    private List<ZonedDateTime> getLastComplete() {
        String jpql = "SELECT kpdhe.period FROM KPDownloadHistoryEntity kpdhe WHERE kpdhe.complete = TRUE ORDER BY kpdhe.period DESC";
        TypedQuery<ZonedDateTime> tq = entityManager.createQuery(jpql, ZonedDateTime.class);
        tq.setMaxResults(1);
        List<ZonedDateTime> zdtl = tq.getResultList();
        return zdtl;
    }

}
