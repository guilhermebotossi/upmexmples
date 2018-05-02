package br.inpe.climaespacial.swd.average.repositories;

import br.inpe.climaespacial.swd.average.providers.HourlyAverageNextHourRepository;
import java.time.ZonedDateTime;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultHourlyAverageNextHourRepository implements HourlyAverageNextHourRepository {

    @Inject
    private EntityManager entityManager;

    @Override
    public ZonedDateTime getNextHour() {
        String sql = "SELECT MAX(hae.timeTag) from HourlyAverageEntity hae";
        TypedQuery<ZonedDateTime> q = entityManager.createQuery(sql, ZonedDateTime.class);
        List<ZonedDateTime> zdtl = q.getResultList();

        return addHour(zdtl.get(0));
    }

    private ZonedDateTime addHour(ZonedDateTime zonedDateTime) {
        ZonedDateTime nextHour = null;
        if (zonedDateTime != null) {
            nextHour = zonedDateTime.plusHours(1);
        }
        return nextHour;
    }

}
