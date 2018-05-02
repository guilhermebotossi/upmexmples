package br.inpe.climaespacial.swd.average.repositories;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import br.inpe.climaespacial.swd.average.mappers.HourlyAverageEntityMapper;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

@Dependent
public class DefaultHourlyAverageWriterRepository implements HourlyAverageWriterRepository {

    @Inject
    private HourlyAverageEntityMapper hourlyAverageEntityMapper;

    @Inject
    private EntityManager entityManager;

    @Override
    @Transactional(value = TxType.REQUIRES_NEW)
    public void save(HourlyAverage hourlyAverage) {

        throwIfNull(hourlyAverage, "hourlyAverage");

        HourlyAverageEntity hae = hourlyAverageEntityMapper.map(hourlyAverage);

        entityManager.persist(hae);

        entityManager.flush();
    }

}
