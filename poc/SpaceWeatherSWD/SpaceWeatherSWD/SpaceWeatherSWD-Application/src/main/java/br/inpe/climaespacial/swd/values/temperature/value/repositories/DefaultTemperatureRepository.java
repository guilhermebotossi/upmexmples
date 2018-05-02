package br.inpe.climaespacial.swd.values.temperature.value.repositories;

import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.values.temperature.value.dtos.Temperature;
import br.inpe.climaespacial.swd.values.temperature.value.entities.TemperatureEntity;
import br.inpe.climaespacial.swd.values.temperature.value.mappers.TemperatureMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultTemperatureRepository implements TemperatureRepository {

    private static final int PERIOD_SIZE = 3;

    @Inject
    private TemperatureMapper temperatureMapper;

    @Inject
    private EntityManager entityManager;

    @Inject
    private IntervalValidator intervalValidator;

    @Override
    public List<Temperature> list(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        validate(initialDateTime, finalDateTime);

        String fullyQualifiedName = TemperatureEntity.class.getName();
        TypedQuery<TemperatureEntity> tq = entityManager.createQuery("SELECT NEW " + fullyQualifiedName + "(pe.timeTag, pe.temperature) "
                + "FROM PlasmaEntity pe "
                + "WHERE "
                + "pe.timeTag >= :initialDateTime and "
                + "pe.timeTag < :finalDateTime ORDER BY pe.timeTag", TemperatureEntity.class);

        tq.setParameter("initialDateTime", initialDateTime);
        tq.setParameter("finalDateTime", finalDateTime);

        List<TemperatureEntity> bel = tq.getResultList();

        return temperatureMapper.map(bel);
    }

    private void validate(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        throwIfNull(initialDateTime, "initialDateTime");

        throwIfNull(finalDateTime, "finalDateTime");

        intervalValidator.validate(initialDateTime, finalDateTime, PERIOD_SIZE);
    }

}
