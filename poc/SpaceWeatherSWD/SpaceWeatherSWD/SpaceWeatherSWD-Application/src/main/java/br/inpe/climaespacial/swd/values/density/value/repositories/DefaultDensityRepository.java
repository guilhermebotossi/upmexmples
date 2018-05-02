package br.inpe.climaespacial.swd.values.density.value.repositories;

import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.density.value.dtos.Density;
import br.inpe.climaespacial.swd.values.density.value.entities.DensityEntity;
import br.inpe.climaespacial.swd.values.density.value.mappers.DensityMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultDensityRepository implements DensityRepository {

    private static final int PERIOD_SIZE = 3;

    @Inject
    private DensityMapper densityMapper;

    @Inject
    private EntityManager entityManager;

    @Inject
    private IntervalValidator intervalValidator;

    @Override
    public List<Density> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        validate(initialDateTime, finalDateTime);

        String fullyQualifiedName = DensityEntity.class.getName();
        TypedQuery<DensityEntity> tq = entityManager.createQuery("SELECT NEW " + fullyQualifiedName + "(pe.timeTag, pe.density) "
                + "FROM PlasmaEntity pe "
                + "WHERE "
                + "pe.timeTag >= :initialDateTime and "
                + "pe.timeTag < :finalDateTime ORDER BY pe.timeTag", DensityEntity.class);

        tq.setParameter("initialDateTime", initialDateTime);
        tq.setParameter("finalDateTime", finalDateTime);

        List<DensityEntity> del = tq.getResultList();

        return densityMapper.map(del);
    }

    private void validate(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        throwIfNull(initialDateTime, "initialDateTime");

        throwIfNull(finalDateTime, "finalDateTime");

        intervalValidator.validate(initialDateTime, finalDateTime, PERIOD_SIZE);
    }

}
