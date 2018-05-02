package br.inpe.climaespacial.swd.values.rmp.value.repositories;

import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.rmp.value.dtos.RMP;
import br.inpe.climaespacial.swd.values.rmp.value.entities.RMPEntity;
import br.inpe.climaespacial.swd.values.rmp.value.mappers.RMPMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultRMPRepository implements RMPRepository {

    private static final int PERIOD_SIZE = 3;

    @Inject
    private RMPMapper rmpMapper;

    @Inject
    private EntityManager entityManager;

    @Inject
    private IntervalValidator intervalValidator;

    @Override
    public List<RMP> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        validate(initialDateTime, finalDateTime);

        String fullyQualifiedName = RMPEntity.class.getName();
        TypedQuery<RMPEntity> tq = entityManager.createQuery("SELECT NEW " + fullyQualifiedName + "(cve.timeTag, cve.rmp) "
                + "FROM CalculatedValuesEntity cve "
                + "WHERE "
                + "cve.timeTag >= :initialDateTime and "
                + "cve.timeTag < :finalDateTime ORDER BY cve.timeTag", RMPEntity.class);

        tq.setParameter("initialDateTime", initialDateTime);
        tq.setParameter("finalDateTime", finalDateTime);

        List<RMPEntity> bel = tq.getResultList();

        return rmpMapper.map(bel);
    }

    private void validate(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        throwIfNull(initialDateTime, "initialDateTime");

        throwIfNull(finalDateTime, "finalDateTime");

        intervalValidator.validate(initialDateTime, finalDateTime, PERIOD_SIZE);
    }

}
