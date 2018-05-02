package br.inpe.climaespacial.swd.values.dpr.value.repositories;

import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.dpr.value.dtos.DPR;
import br.inpe.climaespacial.swd.values.dpr.value.entities.DPREntity;
import br.inpe.climaespacial.swd.values.dpr.value.mappers.DPRMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultDPRRepository implements DPRRepository {

    private static final int PERIOD_SIZE = 3;

    @Inject
    private DPRMapper dprMapper;

    @Inject
    private EntityManager entityManager;

    @Inject
    private IntervalValidator intervalValidator;

    @Override
    public List<DPR> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        validate(initialDateTime, finalDateTime);

        String fullyQualifiedName = DPREntity.class.getName();
        TypedQuery<DPREntity> tq = entityManager.createQuery("SELECT NEW " + fullyQualifiedName + "(cve.timeTag, cve.dpr) "
                + "FROM CalculatedValuesEntity cve "
                + "WHERE "
                + "cve.timeTag >= :initialDateTime and "
                + "cve.timeTag < :finalDateTime ORDER BY cve.timeTag", DPREntity.class);

        tq.setParameter("initialDateTime", initialDateTime);
        tq.setParameter("finalDateTime", finalDateTime);

        List<DPREntity> dprel = tq.getResultList();

        return dprMapper.map(dprel);
    }

    private void validate(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        throwIfNull(initialDateTime, "initialDateTime");

        throwIfNull(finalDateTime, "finalDateTime");

        intervalValidator.validate(initialDateTime, finalDateTime, PERIOD_SIZE);
    }

}
