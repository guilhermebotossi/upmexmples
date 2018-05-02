package br.inpe.climaespacial.swd.indexes.z.repositories;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import br.inpe.climaespacial.swd.indexes.z.entities.ZIndexEntity;
import br.inpe.climaespacial.swd.indexes.z.mappers.ZIndexMapper;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultZIndexReaderRepository implements ZIndexReaderRepository {

    private static final String QUERY = "SELECT MAX(zie.timeTag) FROM ZIndexEntity zie";

    private static final String QUERY_LIST = "SELECT zie FROM ZIndexEntity zie WHERE zie.timeTag >= :farthestFromNow AND zie.timeTag < :nearestFromNow ORDER BY zie.timeTag";

    @Inject
    private EntityManager entityManager;

    @Inject
    private ZIndexMapper zIndexMapper;

    @Inject
    private ListFactory<ZIndexEntity> zIndexEntityListFactory;

    @Inject
    private DateTimeHelper dateTimeHelper;

    @Override
    public ZonedDateTime getNextHourToBeCalculated() {
        TypedQuery<ZonedDateTime> tq = entityManager.createQuery(QUERY, ZonedDateTime.class);
        ZonedDateTime zdt = tq.getResultList().get(0);
        return zdt == null ? null : zdt.plusHours(1);
    }

    @Override
    public List<ZIndex> listByPeriod(ZonedDateTime farthestFromNow, ZonedDateTime nearestFromNow) {

        Duration d = Duration.between(farthestFromNow, nearestFromNow);
        List<ZIndexEntity> ziel = zIndexEntityListFactory.create();
        List<ZIndexEntity> auxZiel;

        if (d.toDays() > 3) {
            String query = "SELECT zie1 FROM ZIndexEntity zie1 WHERE zie1.timeTag >= :timeTag1 AND zie1.timeTag < :timeTag2 AND zie1.postValue = ("
                    + "SELECT MAX(zie2.postValue) FROM ZIndexEntity zie2 WHERE zie2.timeTag >= :timeTag1 AND zie2.timeTag < :timeTag2"
                    + ") ORDER BY zie1.timeTag DESC";

            TypedQuery<ZIndexEntity> tq = entityManager.createQuery(query, ZIndexEntity.class);
            tq.setMaxResults(1);

            while (farthestFromNow.isBefore(nearestFromNow)) {
                ZonedDateTime ttt = dateTimeHelper.truncate(farthestFromNow);
                tq.setParameter("timeTag1", ttt);
                tq.setParameter("timeTag2", ttt.plusDays(1));
                auxZiel = tq.getResultList();
                if (!auxZiel.isEmpty()) {
                    ziel.add(auxZiel.get(0));
                }
                farthestFromNow = farthestFromNow.plusDays(1);
            }
        } else {
            TypedQuery<ZIndexEntity> tq = entityManager.createQuery(QUERY_LIST, ZIndexEntity.class);
            tq.setParameter("farthestFromNow", farthestFromNow);
            tq.setParameter("nearestFromNow", nearestFromNow);
            ziel = tq.getResultList();
        }

        return zIndexMapper.map(ziel);
    }

}
