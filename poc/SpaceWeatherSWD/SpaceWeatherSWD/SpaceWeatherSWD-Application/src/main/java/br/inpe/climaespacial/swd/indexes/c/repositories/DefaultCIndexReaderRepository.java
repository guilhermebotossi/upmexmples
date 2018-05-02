package br.inpe.climaespacial.swd.indexes.c.repositories;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import br.inpe.climaespacial.swd.indexes.c.mappers.CIndexMapper;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultCIndexReaderRepository implements CIndexReaderRepository {

    private static final String QUERY = "SELECT MAX(cie.timeTag) FROM CIndexEntity cie";

    private static final String QUERY_LIST = "SELECT cie FROM CIndexEntity cie WHERE cie.timeTag >= :farthestFromNow AND cie.timeTag < :nearestFromNow ORDER BY cie.timeTag";

    @Inject
    private EntityManager entityManager;

    @Inject
    private CIndexMapper cIndexMapper;

    @Inject
    private ListFactory<CIndexEntity> cIndexEntityListFactory;

    @Inject
    private DateTimeHelper dateTimeHelper;

    @Override
    public ZonedDateTime getNextHourToBeCalculated() {
        TypedQuery<ZonedDateTime> tq = entityManager.createQuery(QUERY, ZonedDateTime.class);
        ZonedDateTime zdt = tq.getResultList().get(0);
        return zdt == null ? null : zdt.plusHours(1);
    }

    @Override
    public List<CIndex> listByPeriod(ZonedDateTime farthestFromNow, ZonedDateTime nearestFromNow) {

        Duration d = Duration.between(farthestFromNow, nearestFromNow);
        List<CIndexEntity> ciel = cIndexEntityListFactory.create();
        List<CIndexEntity> auxCiel;

        if (d.toDays() > 3) {
            String query = "SELECT cie1 FROM CIndexEntity cie1 WHERE cie1.timeTag >= :timeTag1 AND cie1.timeTag < :timeTag2 AND cie1.postValue = ("
                    + "SELECT MAX(cie2.postValue) FROM CIndexEntity cie2 WHERE cie2.timeTag >= :timeTag1 AND cie2.timeTag < :timeTag2"
                    + ") ORDER BY cie1.timeTag DESC";

            TypedQuery<CIndexEntity> tq = entityManager.createQuery(query, CIndexEntity.class);
            tq.setMaxResults(1);

            while (farthestFromNow.isBefore(nearestFromNow)) {
                ZonedDateTime ttt = dateTimeHelper.truncate(farthestFromNow);
                tq.setParameter("timeTag1", ttt);
                tq.setParameter("timeTag2", ttt.plusDays(1));
                auxCiel = tq.getResultList();
                if (!auxCiel.isEmpty()) {
                    ciel.add(auxCiel.get(0));
                }
                farthestFromNow = farthestFromNow.plusDays(1);
            }
        } else {
            TypedQuery<CIndexEntity> tq = entityManager.createQuery(QUERY_LIST, CIndexEntity.class);
            tq.setParameter("farthestFromNow", farthestFromNow);
            tq.setParameter("nearestFromNow", nearestFromNow);
            ciel = tq.getResultList();
        }

        return cIndexMapper.map(ciel);
    }
    
}
