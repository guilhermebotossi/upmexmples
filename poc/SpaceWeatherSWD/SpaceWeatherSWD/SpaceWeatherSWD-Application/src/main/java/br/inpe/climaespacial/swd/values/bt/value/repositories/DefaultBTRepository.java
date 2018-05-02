package br.inpe.climaespacial.swd.values.bt.value.repositories;

import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.bt.value.dtos.BT;
import br.inpe.climaespacial.swd.values.bt.value.entities.BTEntity;
import br.inpe.climaespacial.swd.values.bt.value.mappers.BTMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultBTRepository implements BTRepository {

    private static final int PERIOD_SIZE = 3;

    @Inject
    private BTMapper btMapper;

    @Inject
    private EntityManager entityManager;

    @Inject
    private IntervalValidator intervalValidator;

    @Override
    public List<BT> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        validate(initialDateTime, finalDateTime);
        String fullyQualifiedName = BTEntity.class.getName();

        TypedQuery<BTEntity> tq = entityManager.createQuery("SELECT NEW " + fullyQualifiedName + "(me.timeTag, me.bt) "
                + "FROM MagEntity me "
                + "WHERE "
                + "me.timeTag >= :initialDateTime and "
                + "me.timeTag < :finalDateTime ORDER BY me.timeTag", BTEntity.class);

        tq.setParameter("initialDateTime", initialDateTime);
        tq.setParameter("finalDateTime", finalDateTime);

        List<BTEntity> bel = tq.getResultList();

        return btMapper.map(bel);
    }

    private void validate(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        throwIfNull(initialDateTime, "initialDateTime");
        throwIfNull(finalDateTime, "finalDateTime");

        intervalValidator.validate(initialDateTime, finalDateTime, PERIOD_SIZE);
    }

}
