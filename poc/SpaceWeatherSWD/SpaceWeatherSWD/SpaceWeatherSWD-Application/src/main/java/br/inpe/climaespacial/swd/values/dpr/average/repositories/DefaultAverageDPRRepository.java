package br.inpe.climaespacial.swd.values.dpr.average.repositories;

import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.dpr.average.dtos.AverageDPR;
import br.inpe.climaespacial.swd.values.dpr.average.entities.AverageDPREntity;
import br.inpe.climaespacial.swd.values.dpr.average.mappers.AverageDPRMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultAverageDPRRepository implements AverageDPRRepository {

    private static final int PERIOD_SIZE = 31;

    @Inject
    private AverageDPRMapper averageDPRMapper;

    @Inject
    private EntityManager entityManager;

    @Inject
    private IntervalValidator intervalValidator;

    @Override
    public List<AverageDPR> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        validate(initialDateTime, finalDateTime);

        String fullyQualifiedName = AverageDPREntity.class.getName();
        TypedQuery<AverageDPREntity> tq = entityManager.createQuery("SELECT NEW " + fullyQualifiedName + "(hae.timeTag, hae.dpr) "
                + "FROM HourlyAverageEntity hae "
                + "WHERE "
                + "hae.timeTag >= :initialDateTime and "
                + "hae.timeTag < :finalDateTime ORDER BY hae.timeTag", AverageDPREntity.class);

        tq.setParameter("initialDateTime", initialDateTime);
        tq.setParameter("finalDateTime", finalDateTime);

        List<AverageDPREntity> bel = tq.getResultList();

        return averageDPRMapper.map(bel);
    }

    private void validate(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        throwIfNull(initialDateTime, "initialDateTime");

        throwIfNull(finalDateTime, "finalDateTime");

        intervalValidator.validate(initialDateTime, finalDateTime, PERIOD_SIZE);
    }

}
