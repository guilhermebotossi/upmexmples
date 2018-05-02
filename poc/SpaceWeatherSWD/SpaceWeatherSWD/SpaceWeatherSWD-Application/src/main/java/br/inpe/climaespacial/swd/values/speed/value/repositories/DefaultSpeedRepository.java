package br.inpe.climaespacial.swd.values.speed.value.repositories;

import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.speed.value.dtos.Speed;
import br.inpe.climaespacial.swd.values.speed.value.entities.SpeedEntity;
import br.inpe.climaespacial.swd.values.speed.value.mappers.SpeedMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultSpeedRepository implements SpeedRepository {

    private static final int PERIOD_SIZE = 3;

    @Inject
    private SpeedMapper speedMapper;

    @Inject
    private EntityManager entityManager;

    @Inject
    private IntervalValidator intervalValidator;

    @Override
    public List<Speed> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        validate(initialDateTime, finalDateTime);

        String fullyQualifiedName = SpeedEntity.class.getName();
        TypedQuery<SpeedEntity> tq = entityManager.createQuery("SELECT NEW " + fullyQualifiedName + "(pe.timeTag, pe.speed) "
                + "FROM PlasmaEntity pe "
                + "WHERE "
                + "pe.timeTag >= :initialDateTime and "
                + "pe.timeTag < :finalDateTime ORDER BY pe.timeTag", SpeedEntity.class);

        tq.setParameter("initialDateTime", initialDateTime);
        tq.setParameter("finalDateTime", finalDateTime);

        List<SpeedEntity> bel = tq.getResultList();

        return speedMapper.map(bel);
    }

    private void validate(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        throwIfNull(initialDateTime, "initialDateTime");

        throwIfNull(finalDateTime, "finalDateTime");

        intervalValidator.validate(initialDateTime, finalDateTime, PERIOD_SIZE);
    }

}
