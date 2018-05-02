package br.inpe.climaespacial.swd.indexes;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultFirstHourlyAverageToCalculateIndexesReaderRepository implements FirstHourlyAverageToCalculateIndexesReaderRepository {

    private static final String QUERY = "SELECT hae.timeTag FROM HourlyAverageEntity hae ORDER BY hae.timeTag ASC";

    @Inject
    private EntityManager entityManager;

    @Override
    public ZonedDateTime getFirstHour() {
        TypedQuery<ZonedDateTime> tq = createQuery();
        return getResultFromQuery(tq);
    }

    private TypedQuery<ZonedDateTime> createQuery() {
        TypedQuery<ZonedDateTime> tq = entityManager.createQuery(QUERY, ZonedDateTime.class);
        tq.setMaxResults(1);
        return tq;
    }

    private ZonedDateTime getResultFromQuery(TypedQuery<ZonedDateTime> query) {
        final List<ZonedDateTime> zdtl = query.getResultList();
        return !zdtl.isEmpty() ? zdtl.get(0) : null;
    }
}
