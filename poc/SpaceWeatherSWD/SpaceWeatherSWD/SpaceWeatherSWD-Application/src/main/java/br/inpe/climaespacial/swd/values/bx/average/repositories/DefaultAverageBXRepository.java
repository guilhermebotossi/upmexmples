package br.inpe.climaespacial.swd.values.bx.average.repositories;

import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.bx.average.dtos.AverageBX;
import br.inpe.climaespacial.swd.values.bx.average.entities.AverageBXEntity;
import br.inpe.climaespacial.swd.values.bx.average.mappers.AverageBXMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultAverageBXRepository implements AverageBXRepository {

    private static final int PERIOD_SIZE = 31;

    @Inject
    private AverageBXMapper averageBXMapper;

    @Inject
    private EntityManager entityManager;

    @Inject
    private IntervalValidator intervalValidator;

    @Override
    public List<AverageBX> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        validate(initialDateTime, finalDateTime);
        String fullyQualifiedName = AverageBXEntity.class.getName();
        TypedQuery<AverageBXEntity> tq = entityManager.createQuery("SELECT NEW " + fullyQualifiedName + "(hae.timeTag, hae.bxGsm) "
                + "FROM HourlyAverageEntity hae "
                + "WHERE "
                + "hae.timeTag >= :initialDateTime and "
                + "hae.timeTag < :finalDateTime ORDER BY hae.timeTag", AverageBXEntity.class);

        tq.setParameter("initialDateTime", initialDateTime);
        tq.setParameter("finalDateTime", finalDateTime);

        List<AverageBXEntity> bel = tq.getResultList();

        return averageBXMapper.map(bel);
    }

    private void validate(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        throwIfNull(initialDateTime, "initialDateTime");

        throwIfNull(finalDateTime, "finalDateTime");

        intervalValidator.validate(initialDateTime, finalDateTime, PERIOD_SIZE);
    }

}
