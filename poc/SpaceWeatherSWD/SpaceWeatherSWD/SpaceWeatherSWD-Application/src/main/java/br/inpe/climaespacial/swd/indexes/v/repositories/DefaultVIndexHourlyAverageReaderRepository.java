package br.inpe.climaespacial.swd.indexes.v.repositories;

import java.time.ZonedDateTime;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.commons.utils.CollectionUtils;
import br.inpe.climaespacial.swd.indexes.FirstHourlyAverageToCalculateIndexesReaderRepository;
import br.inpe.climaespacial.swd.indexes.b.mappers.HourlyAverageMapper;
import br.inpe.climaespacial.swd.indexes.v.helpers.HourlyAverageDataFillerHelper;

@Dependent
public class DefaultVIndexHourlyAverageReaderRepository implements VIndexHourlyAverageReaderRepository {

    private static final int maxSize = 26;

    @Inject
    private VIndexReaderRepository vIndexReaderRepository;

    @Inject
    private FirstHourlyAverageToCalculateIndexesReaderRepository firstHourlyAverageToCalculateIndexesReaderRepository;

    @Inject
    private EntityManager entityManager;

    @Inject
    private HourlyAverageMapper hourlyAverageMapper;

    @Inject
    private ListFactory<HourlyAverage> hourlyAverageListFactory;

    @Inject
    private HourlyAverageDataFillerHelper houlyAverageDataFillerHelper;

    @Inject
    private CollectionUtils collectionUtils;

    @Override
    public List<HourlyAverage> getHourlyAverages() {
        ZonedDateTime tt = getHour();
        if (tt != null) {
            String q = "SELECT hae FROM HourlyAverageEntity hae WHERE hae.timeTag <= :timeTag ORDER BY hae.timeTag DESC";
            TypedQuery<HourlyAverageEntity> haetq = entityManager.createQuery(q, HourlyAverageEntity.class);
            haetq.setParameter("timeTag", tt);
            haetq.setMaxResults(maxSize);
            List<HourlyAverageEntity> hael = haetq.getResultList();
            if (!hael.isEmpty() && hael.get(0).getTimeTag().equals(tt)) {
                collectionUtils.reverse(hael);
                List<HourlyAverage> hal = hourlyAverageMapper.mapAll(hael);
                ZonedDateTime idt = tt.minusHours(maxSize - 1L);
                List<HourlyAverage> fhal = houlyAverageDataFillerHelper.fillByHours(idt, tt, hal);
                return fhal;
            }
        }

        return hourlyAverageListFactory.create();
    }

    private ZonedDateTime getHour() {
        ZonedDateTime nhtbc = vIndexReaderRepository.getNextHourToBeCalculated();
        return nhtbc == null ? firstHourlyAverageToCalculateIndexesReaderRepository.getFirstHour() : nhtbc;
    }

}
