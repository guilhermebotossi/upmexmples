package br.inpe.climaespacial.swd.values.repositories;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultValuesReaderRepository implements ValuesReaderRepository {
    private static final String SQL_MAG_ENTITY_SELECT = "SELECT MAX(me.timeTag) FROM MagEntity me "
            + "WHERE me.bxGsm IS NOT NULL OR "
            + "me.byGsm IS NOT NULL OR "
            + "me.bzGsm IS NOT NULL OR "
            + "me.latGsm IS NOT NULL OR "
            + "me.lonGsm IS NOT NULL OR "
            + "me.bt IS NOT NULL";
    private static final String SQL_PLASMA_ENTITY_SELECT = "SELECT MAX(pe.timeTag) FROM PlasmaEntity pe "
            + "WHERE pe.density IS NOT NULL OR "
            + "pe.speed IS NOT NULL OR "
            + "pe.temperature IS NOT NULL";
    private static final String SQL_CALCULATED_VALUES_ENTITY_SELECT = "SELECT MAX(cve.timeTag) FROM CalculatedValuesEntity cve "
            + "WHERE cve.ey IS NOT NULL OR "
            + "cve.dpr IS NOT NULL OR "
            + "cve.rmp IS NOT NULL";
    
    @Inject
    private EntityManager entityManager;

    @Override
    public ZonedDateTime lastValuesDate() {
        TypedQuery<ZonedDateTime> tq1 = entityManager.createQuery(SQL_MAG_ENTITY_SELECT, ZonedDateTime.class);
        ZonedDateTime mmdt = tq1.getSingleResult();
        
        TypedQuery<ZonedDateTime> tq2 = entityManager.createQuery(SQL_PLASMA_ENTITY_SELECT, ZonedDateTime.class);
        ZonedDateTime pmdt = tq2.getSingleResult();

        TypedQuery<ZonedDateTime> tq3 = entityManager.createQuery(SQL_CALCULATED_VALUES_ENTITY_SELECT, ZonedDateTime.class);
        ZonedDateTime cvemdt = tq3.getSingleResult();
        
        List<ZonedDateTime> zdtl = Arrays.asList(mmdt, pmdt, cvemdt);
        
        Optional<ZonedDateTime> ozdt = zdtl.stream().filter(zdt -> zdt != null).max(ZonedDateTime::compareTo);
        
        return ozdt.isPresent() ? ozdt.get(): null;
    }

}
