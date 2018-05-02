package br.inpe.climaespacial.swd.values.speed.average.repositories;

import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.speed.average.dtos.AverageSpeed;
import br.inpe.climaespacial.swd.values.speed.average.entities.AverageSpeedEntity;
import br.inpe.climaespacial.swd.values.speed.average.mappers.AverageSpeedMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultAverageSpeedRepository implements AverageSpeedRepository {

    private static final int PERIOD_SIZE = 31;

    @Inject
    private AverageSpeedMapper AverageSpeedMapper;

    @Inject
    private EntityManager entityManager;

    @Inject
    private IntervalValidator intervalValidator;

    @Override
    public List<AverageSpeed> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        validate(initialDateTime, finalDateTime);

        String fullyQualifiedName = AverageSpeedEntity.class.getName();
        TypedQuery<AverageSpeedEntity> tq = entityManager.createQuery("SELECT NEW " + fullyQualifiedName + "(hae.timeTag, hae.speed) "
                + "FROM HourlyAverageEntity hae "
                + "WHERE "
                + "hae.timeTag >= :initialDateTime and "
                + "hae.timeTag < :finalDateTime ORDER BY hae.timeTag", AverageSpeedEntity.class);

        tq.setParameter("initialDateTime", initialDateTime);
        tq.setParameter("finalDateTime", finalDateTime);

        List<AverageSpeedEntity> bel = tq.getResultList();

        return AverageSpeedMapper.map(bel);
    }

    private void validate(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        throwIfNull(initialDateTime, "initialDateTime");

        throwIfNull(finalDateTime, "finalDateTime");

        intervalValidator.validate(initialDateTime, finalDateTime, PERIOD_SIZE);
    }

}
