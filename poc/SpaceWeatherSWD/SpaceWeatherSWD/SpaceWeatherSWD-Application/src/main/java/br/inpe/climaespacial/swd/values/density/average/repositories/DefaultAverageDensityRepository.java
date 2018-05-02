package br.inpe.climaespacial.swd.values.density.average.repositories;

import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.density.average.dtos.AverageDensity;
import br.inpe.climaespacial.swd.values.density.average.entities.AverageDensityEntity;
import br.inpe.climaespacial.swd.values.density.average.mappers.AverageDensityMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultAverageDensityRepository implements AverageDensityRepository {

    private static final int PERIOD_SIZE = 31;

    @Inject
    private AverageDensityMapper averageDensityMapper;

    @Inject
    private EntityManager entityManager;

    @Inject
    private IntervalValidator intervalValidator;

    @Override
    public List<AverageDensity> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        validate(initialDateTime, finalDateTime);

        String fullyQualifiedName = AverageDensityEntity.class.getName();
        TypedQuery<AverageDensityEntity> tq = entityManager.createQuery("SELECT NEW " + fullyQualifiedName + "(hae.timeTag, hae.density) "
                + "FROM HourlyAverageEntity hae "
                + "WHERE "
                + "hae.timeTag >= :initialDateTime and "
                + "hae.timeTag < :finalDateTime ORDER BY hae.timeTag", AverageDensityEntity.class);

        tq.setParameter("initialDateTime", initialDateTime);
        tq.setParameter("finalDateTime", finalDateTime);

        List<AverageDensityEntity> bel = tq.getResultList();

        return averageDensityMapper.map(bel);
    }

    private void validate(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        throwIfNull(initialDateTime, "initialDateTime");

        throwIfNull(finalDateTime, "finalDateTime");

        intervalValidator.validate(initialDateTime, finalDateTime, PERIOD_SIZE);
    }

}
