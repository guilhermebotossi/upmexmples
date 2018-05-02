package br.inpe.climaespacial.swd.values.ey.average.repositories;

import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.ey.average.dtos.AverageEY;
import br.inpe.climaespacial.swd.values.ey.average.entities.AverageEYEntity;
import br.inpe.climaespacial.swd.values.ey.average.mappers.AverageEYMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultAverageEYRepository implements AverageEYRepository {

    private static final int PERIOD_SIZE = 31;

    @Inject
    private AverageEYMapper averageEYMapper;

    @Inject
    private EntityManager entityManager;

    @Inject
    private IntervalValidator intervalValidator;

    @Override
    public List<AverageEY> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        validate(initialDateTime, finalDateTime);

        String fullyQualifiedName = AverageEYEntity.class.getName();
        TypedQuery<AverageEYEntity> tq = entityManager.createQuery("SELECT NEW " + fullyQualifiedName + "(hae.timeTag, hae.ey) "
                + "FROM HourlyAverageEntity hae "
                + "WHERE "
                + "hae.timeTag >= :initialDateTime and "
                + "hae.timeTag < :finalDateTime ORDER BY hae.timeTag", AverageEYEntity.class);

        tq.setParameter("initialDateTime", initialDateTime);
        tq.setParameter("finalDateTime", finalDateTime);

        List<AverageEYEntity> bel = tq.getResultList();

        return averageEYMapper.map(bel);
    }

    private void validate(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        throwIfNull(initialDateTime, "initialDateTime");

        throwIfNull(finalDateTime, "finalDateTime");

        intervalValidator.validate(initialDateTime, finalDateTime, PERIOD_SIZE);
    }

}
