package br.inpe.climaespacial.swd.indexes.v.repositories;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;
import br.inpe.climaespacial.swd.commons.utils.CollectionUtils;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.entities.VIndexEntity;
import br.inpe.climaespacial.swd.indexes.v.helpers.VIndexDataFillerHelper;
import br.inpe.climaespacial.swd.indexes.v.mappers.VIndexMapper;

@Dependent
public class DefaultVIndexReaderRepository implements VIndexReaderRepository {

    private static final String QUERY = "SELECT MAX(vie.timeTag) FROM VIndexEntity vie";

    private static final String QUERY_LIST = "SELECT vie FROM VIndexEntity vie WHERE vie.timeTag >= :farthestFromNow AND vie.timeTag < :nearestFromNow ORDER BY vie.timeTag";

    @Inject
    private EntityManager entityManager;

    @Inject
    private VIndexMapper vIndexMapper;

    @Inject
    private ListFactory<VIndexEntity> vIndexEntityListFactory;

    @Inject
    private DateTimeHelper dateTimeHelper;

    @Inject
    private VIndexDataFillerHelper vIndexDataFillerHelper;
    
    @Inject
    private CollectionUtils collectionUtils;

    @Override
    public ZonedDateTime getNextHourToBeCalculated() {
        TypedQuery<ZonedDateTime> tq = entityManager.createQuery(QUERY, ZonedDateTime.class);
        ZonedDateTime zdt = tq.getResultList().get(0);
        return zdt == null ? null : zdt.plusHours(1);
    }

    @Override
    public List<VIndex> listByPeriod(ZonedDateTime farthestFromNow, ZonedDateTime nearestFromNow) {

        Duration d = Duration.between(farthestFromNow, nearestFromNow);
        List<VIndexEntity> viel = vIndexEntityListFactory.create();
        List<VIndexEntity> auxViel;

        if (d.toDays() > 3) {
            String query = "SELECT vie1 FROM VIndexEntity vie1 WHERE vie1.timeTag >= :timeTag1 AND vie1.timeTag < :timeTag2 AND vie1.postValue = ("
                    + "SELECT MAX(vie2.postValue) FROM VIndexEntity vie2 WHERE vie2.timeTag >= :timeTag1 AND vie2.timeTag < :timeTag2"
                    + ") ORDER BY vie1.timeTag DESC";

            TypedQuery<VIndexEntity> tq = entityManager.createQuery(query, VIndexEntity.class);
            tq.setMaxResults(1);

            while (farthestFromNow.isBefore(nearestFromNow)) {
                ZonedDateTime ttt = dateTimeHelper.truncate(farthestFromNow);
                tq.setParameter("timeTag1", ttt);
                tq.setParameter("timeTag2", ttt.plusDays(1));
                auxViel = tq.getResultList();
                if (!auxViel.isEmpty()) {
                    viel.add(auxViel.get(0));
                }
                farthestFromNow = farthestFromNow.plusDays(1);
            }
        } else {
            TypedQuery<VIndexEntity> tq = entityManager.createQuery(QUERY_LIST, VIndexEntity.class);
            tq.setParameter("farthestFromNow", farthestFromNow);
            tq.setParameter("nearestFromNow", nearestFromNow);
            viel = tq.getResultList();
        }

        return vIndexMapper.map(viel);
    }

    @Override
    public List<VIndex> getLastVIndexesFromDateTime(ZonedDateTime timeTag) {
        String sql = "SELECT ve FROM VIndexEntity ve WHERE ve.timeTag < :timeTag ORDER BY ve.timeTag DESC";
        int targetSize = 24;

        TypedQuery<VIndexEntity> tq = entityManager.createQuery(sql, VIndexEntity.class);
        tq.setParameter("timeTag", timeTag);
        tq.setMaxResults(targetSize);
        List<VIndexEntity> viel = tq.getResultList();
        collectionUtils.reverse(viel);

        List<VIndex> vil = vIndexMapper.map(viel);
        
        ZonedDateTime fdt = timeTag.minusHours(1);
        ZonedDateTime idt = fdt.minusHours(targetSize - 1);
        
        List<VIndex> fvil = vIndexDataFillerHelper.fillByHoursAnyway(idt, fdt, vil);

        return fvil;
    }

}
