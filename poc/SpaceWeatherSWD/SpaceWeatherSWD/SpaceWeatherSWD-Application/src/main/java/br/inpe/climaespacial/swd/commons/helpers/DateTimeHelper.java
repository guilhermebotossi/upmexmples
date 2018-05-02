package br.inpe.climaespacial.swd.commons.helpers;

import java.time.ZonedDateTime;

public interface DateTimeHelper {

    ZonedDateTime setMaxDayTime(ZonedDateTime zonedDateTime);

    ZonedDateTime truncate(ZonedDateTime zonedDateTime);

    ZonedDateTime truncateToMinute(ZonedDateTime zonedDateTime);

    ZonedDateTime truncateToDays(ZonedDateTime zonedDateTime);

}
