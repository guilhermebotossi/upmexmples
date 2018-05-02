package br.inpe.climaespacial.swd.average.validators;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Dependent
public class DefaultNextHourValidator implements NextHourValidator {

    @Inject
    private EntityManager entityManager;

    @Override
    public boolean validate(ZonedDateTime zdt) {
        GregorianCalendar gc = GregorianCalendar.from(zdt);
        gc.add(Calendar.HOUR, 1);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        ZonedDateTime initialDateTime = gc.toZonedDateTime();

        TypedQuery<Boolean> query = entityManager.createQuery("SELECT "
                + "CASE WHEN(COUNT(*) > 0) THEN true ELSE false END "
                + "FROM MagEntity me, PlasmaEntity pe, CalculatedValuesEntity cve "
                + "WHERE me.timeTag = pe.timeTag "
                + "AND me.timeTag = cve.timeTag "
                + "AND me.timeTag >= :initialDateTime", Boolean.class);
        query.setParameter("initialDateTime", initialDateTime);

        return query.getSingleResult();
    }

}
