package br.inpe.climaespacial.swd.values.bz.value.repositories;

import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.bz.value.dtos.BZ;
import br.inpe.climaespacial.swd.values.bz.value.entities.BZEntity;
import br.inpe.climaespacial.swd.values.bz.value.mappers.BZMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultBZRepository implements BZRepository {

    private static final int PERIOD_SIZE = 3;

    @Inject
    private BZMapper bzMapper;

    @Inject
    private EntityManager entityManager;

    @Inject
    private IntervalValidator intervalValidator;

    @Override
    public List<BZ> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        validate(initialDateTime, finalDateTime);

        String fullyQualifiedName = BZEntity.class.getName();
        TypedQuery<BZEntity> tq = entityManager.createQuery("SELECT NEW " + fullyQualifiedName + "(me.timeTag, me.bzGsm) "
                + "FROM MagEntity me "
                + "WHERE "
                + "me.timeTag >= :initialDateTime and "
                + "me.timeTag < :finalDateTime ORDER BY me.timeTag", BZEntity.class);

        tq.setParameter("initialDateTime", initialDateTime);
        tq.setParameter("finalDateTime", finalDateTime);

        List<BZEntity> bel = tq.getResultList();

        return bzMapper.map(bel);
    }

    private void validate(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        throwIfNull(initialDateTime, "initialDateTime");

        throwIfNull(finalDateTime, "finalDateTime");

        intervalValidator.validate(initialDateTime, finalDateTime, PERIOD_SIZE);
    }

}
