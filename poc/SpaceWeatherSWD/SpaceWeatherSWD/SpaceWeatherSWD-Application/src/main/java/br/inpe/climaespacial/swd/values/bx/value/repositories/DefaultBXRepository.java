package br.inpe.climaespacial.swd.values.bx.value.repositories;

import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.bx.value.dtos.BX;
import br.inpe.climaespacial.swd.values.bx.value.entities.BXEntity;
import br.inpe.climaespacial.swd.values.bx.value.mappers.BXMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultBXRepository implements BXRepository {

    private static final int PERIOD_SIZE = 3;

    @Inject
    private BXMapper bxMapper;

    @Inject
    private EntityManager entityManager;

    @Inject
    private IntervalValidator intervalValidator;

    @Override
    public List<BX> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        validate(initialDateTime, finalDateTime);
        String fullyQualifiedName = BXEntity.class.getName();
        TypedQuery<BXEntity> tq = entityManager.createQuery("SELECT NEW " + fullyQualifiedName + "(me.timeTag, me.bxGsm) "
                + "FROM MagEntity me "
                + "WHERE "
                + "me.timeTag >= :initialDateTime and "
                + "me.timeTag < :finalDateTime ORDER BY me.timeTag", BXEntity.class);

        tq.setParameter("initialDateTime", initialDateTime);
        tq.setParameter("finalDateTime", finalDateTime);

        List<BXEntity> bel = tq.getResultList();

        return bxMapper.map(bel);
    }

    private void validate(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        throwIfNull(initialDateTime, "initialDateTime");

        throwIfNull(finalDateTime, "finalDateTime");

        intervalValidator.validate(initialDateTime, finalDateTime, PERIOD_SIZE);
    }

}
