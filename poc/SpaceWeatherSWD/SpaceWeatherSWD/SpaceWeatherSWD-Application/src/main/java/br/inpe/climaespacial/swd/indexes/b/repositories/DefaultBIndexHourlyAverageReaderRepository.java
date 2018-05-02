package br.inpe.climaespacial.swd.indexes.b.repositories;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import br.inpe.climaespacial.swd.indexes.FirstHourlyAverageToCalculateIndexesReaderRepository;
import br.inpe.climaespacial.swd.indexes.b.mappers.HourlyAverageMapper;
import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultBIndexHourlyAverageReaderRepository implements BIndexHourlyAverageReaderRepository {

    @Inject
    private BIndexReaderRepository bIndexReaderRepository;

    @Inject
    private FirstHourlyAverageToCalculateIndexesReaderRepository firstHourlyAverageToCalculateIndexesReaderRepository;

    @Inject
    private EntityManager entityManager;

    @Inject
    private HourlyAverageMapper hourlyAverageMapper;

    @Override
    public HourlyAverage getHourlyAverage() {
        ZonedDateTime tt = getHour();
        if (tt != null) {
            String q = "SELECT hae FROM HourlyAverageEntity hae WHERE hae.timeTag = :timeTag";
            TypedQuery<HourlyAverageEntity> haetq = entityManager.createQuery(q, HourlyAverageEntity.class);
            haetq.setParameter("timeTag", tt);
            haetq.setMaxResults(1);
            List<HourlyAverageEntity> hael = haetq.getResultList();
            return !hael.isEmpty() ? hourlyAverageMapper.map(hael.get(0)) : null;
        }

        return null;
    }

    private ZonedDateTime getHour() {
        ZonedDateTime nhtbc = bIndexReaderRepository.getNextHourToBeCalculated();
        return nhtbc == null ? firstHourlyAverageToCalculateIndexesReaderRepository.getFirstHour() : nhtbc;
    }
}
