package br.inpe.climaespacial.swd.average.repositories;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultMagPlasmaCalculatedValuesNextHourRepository implements MagPlasmaCalculatedValuesNextHourRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    public ZonedDateTime getNextHour() {
        String sql = "SELECT MIN(me.timeTag) "
                + "FROM MagEntity me, PlasmaEntity pe, CalculatedValuesEntity cve "
                + "WHERE me.timeTag = pe.timeTag "
                + "AND me.timeTag = cve.timeTag";
        TypedQuery<ZonedDateTime> q = entityManager.createQuery(sql, ZonedDateTime.class);
        List<ZonedDateTime> zdtl = q.getResultList();

        return zdtl.get(0) != null ? zdtl.get(0) : null;
    }
}
