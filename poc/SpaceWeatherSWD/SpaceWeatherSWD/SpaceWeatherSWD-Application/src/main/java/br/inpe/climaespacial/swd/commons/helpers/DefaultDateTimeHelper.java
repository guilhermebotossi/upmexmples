package br.inpe.climaespacial.swd.commons.helpers;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.enterprise.context.Dependent;

@Dependent
public class DefaultDateTimeHelper implements DateTimeHelper {

    @Override
    public ZonedDateTime setMaxDayTime(ZonedDateTime zonedDateTime) {

        throwIfNull(zonedDateTime, "zonedDateTime");

        return zonedDateTime.plusDays(1);
    }

    @Override
    public ZonedDateTime truncate(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }

        GregorianCalendar gc = doTruncateHour(zonedDateTime);

        return gc.toZonedDateTime();
    }

    @Override
    public ZonedDateTime truncateToMinute(ZonedDateTime zonedDateTime) {
        GregorianCalendar gc = doTruncateToMinute(zonedDateTime);
        return gc.toZonedDateTime();
    }

    private GregorianCalendar doTruncateHour(ZonedDateTime zonedDateTime) {
        GregorianCalendar gc = doTruncateToMinute(zonedDateTime);
        gc.set(Calendar.HOUR_OF_DAY, 0);
        return gc;
    }

    private GregorianCalendar doTruncateToMinute(ZonedDateTime zonedDateTime) {
        GregorianCalendar gc = GregorianCalendar.from(zonedDateTime);
        gc.set(Calendar.MINUTE, 0);
        gc.set(Calendar.SECOND, 0);
        gc.set(Calendar.MILLISECOND, 0);

        return gc;
    }

    @Override
    public ZonedDateTime truncateToDays(ZonedDateTime zonedDateTime) {
        GregorianCalendar gc = doTruncateHour(zonedDateTime);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        return gc.toZonedDateTime();
    }

}
