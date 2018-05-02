package br.inpe.climaespacial.swd.values.ey.value.repositories;

import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.ey.value.dtos.EY;
import br.inpe.climaespacial.swd.values.ey.value.entities.EYEntity;
import br.inpe.climaespacial.swd.values.ey.value.mappers.EYMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultEYRepository implements EYRepository {

    private static final int PERIOD_SIZE = 3;

    @Inject
    private EYMapper eyMapper;

    @Inject
    private EntityManager entityManager;

    @Inject
    private IntervalValidator intervalValidator;

    @Override
    public List<EY> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        validate(initialDateTime, finalDateTime);

        String fullyQualifiedName = EYEntity.class.getName();
        TypedQuery<EYEntity> tq = entityManager.createQuery("SELECT NEW " + fullyQualifiedName + "(cve.timeTag, cve.ey) "
                + "FROM CalculatedValuesEntity cve "
                + "WHERE "
                + "cve.timeTag >= :initialDateTime and "
                + "cve.timeTag < :finalDateTime ORDER BY cve.timeTag", EYEntity.class);

        tq.setParameter("initialDateTime", initialDateTime);
        tq.setParameter("finalDateTime", finalDateTime);

        List<EYEntity> eyel = tq.getResultList();

        return eyMapper.map(eyel);
    }

    private void validate(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        throwIfNull(initialDateTime, "initialDateTime");

        throwIfNull(finalDateTime, "finalDateTime");

        intervalValidator.validate(initialDateTime, finalDateTime, PERIOD_SIZE);
    }

}
