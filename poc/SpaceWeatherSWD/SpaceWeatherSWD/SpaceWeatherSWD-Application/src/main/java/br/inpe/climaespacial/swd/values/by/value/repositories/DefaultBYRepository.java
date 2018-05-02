package br.inpe.climaespacial.swd.values.by.value.repositories;

import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.by.value.dtos.BY;
import br.inpe.climaespacial.swd.values.by.value.entities.BYEntity;
import br.inpe.climaespacial.swd.values.by.value.mappers.BYMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultBYRepository implements BYRepository {

    private static final int PERIOD_SIZE = 3;

    @Inject
    private BYMapper byMapper;

    @Inject
    private EntityManager entityManager;

    @Inject
    private IntervalValidator intervalValidator;

    @Override
    public List<BY> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        validate(initialDateTime, finalDateTime);

        String fullyQualifiedName = BYEntity.class.getName();
        TypedQuery<BYEntity> tq = entityManager.createQuery("SELECT NEW " + fullyQualifiedName + "(me.timeTag, me.byGsm) "
                + "FROM MagEntity me "
                + "WHERE "
                + "me.timeTag >= :initialDateTime and "
                + "me.timeTag < :finalDateTime ORDER BY me.timeTag", BYEntity.class);

        tq.setParameter("initialDateTime", initialDateTime);
        tq.setParameter("finalDateTime", finalDateTime);

        List<BYEntity> bel = tq.getResultList();

        return byMapper.map(bel);
    }

    private void validate(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        throwIfNull(initialDateTime, "initialDateTime");

        throwIfNull(finalDateTime, "finalDateTime");

        intervalValidator.validate(initialDateTime, finalDateTime, PERIOD_SIZE);
    }

}
