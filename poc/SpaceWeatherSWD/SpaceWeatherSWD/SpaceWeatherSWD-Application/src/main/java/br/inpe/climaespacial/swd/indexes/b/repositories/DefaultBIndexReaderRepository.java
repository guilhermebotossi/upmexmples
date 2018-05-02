package br.inpe.climaespacial.swd.indexes.b.repositories;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.b.entities.BIndexEntity;
import br.inpe.climaespacial.swd.indexes.b.mappers.BIndexMapper;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultBIndexReaderRepository implements BIndexReaderRepository {

    private static final String QUERY = "SELECT MAX(bie.timeTag) FROM BIndexEntity bie";

    private static final String QUERY_LIST = "SELECT bie FROM BIndexEntity bie WHERE bie.timeTag >= :farthestFromNow AND bie.timeTag < :nearestFromNow ORDER BY bie.timeTag";

    @Inject
    private EntityManager entityManager;

    @Inject
    private BIndexMapper bIndexMapper;

    @Inject
    private ListFactory<BIndexEntity> bIndexEntityListFactory;

    @Inject
    private DateTimeHelper dateTimeHelper;

    @Override
    public ZonedDateTime getNextHourToBeCalculated() {
        TypedQuery<ZonedDateTime> tq = entityManager.createQuery(QUERY, ZonedDateTime.class);
        ZonedDateTime zdt = tq.getResultList().get(0);
        return zdt == null ? null : zdt.plusHours(1);
    }

    @Override
    public List<BIndex> listByPeriod(ZonedDateTime farthestFromNow, ZonedDateTime nearestFromNow) {

        Duration d = Duration.between(farthestFromNow, nearestFromNow);
        List<BIndexEntity> biel = bIndexEntityListFactory.create();
        List<BIndexEntity> auxBiel;

        if (d.toDays() > 3) {
            String query = "SELECT bie1 FROM BIndexEntity bie1 WHERE bie1.timeTag >= :timeTag1 AND bie1.timeTag < :timeTag2 AND bie1.postValue = ("
                    + "SELECT MAX(bie2.postValue) FROM BIndexEntity bie2 WHERE bie2.timeTag >= :timeTag1 AND bie2.timeTag < :timeTag2"
                    + ") ORDER BY bie1.timeTag DESC";

            TypedQuery<BIndexEntity> tq = entityManager.createQuery(query, BIndexEntity.class);
            tq.setMaxResults(1);

            while (farthestFromNow.isBefore(nearestFromNow)) {
                ZonedDateTime ttt = dateTimeHelper.truncate(farthestFromNow);
                tq.setParameter("timeTag1", ttt);
                tq.setParameter("timeTag2", ttt.plusDays(1));
                auxBiel = tq.getResultList();
                if (!auxBiel.isEmpty()) {
                    biel.add(auxBiel.get(0));
                }
                farthestFromNow = farthestFromNow.plusDays(1);
            }

        } else {
            TypedQuery<BIndexEntity> tq = entityManager.createQuery(QUERY_LIST, BIndexEntity.class);
            tq.setParameter("farthestFromNow", farthestFromNow);
            tq.setParameter("nearestFromNow", nearestFromNow);
            biel = tq.getResultList();
        }

        return bIndexMapper.map(biel);
    }

}
