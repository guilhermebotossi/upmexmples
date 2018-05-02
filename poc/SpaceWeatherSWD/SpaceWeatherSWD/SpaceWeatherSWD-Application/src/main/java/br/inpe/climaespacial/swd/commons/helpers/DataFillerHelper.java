package br.inpe.climaespacial.swd.commons.helpers;

import java.time.ZonedDateTime;
import java.util.List;

public interface DataFillerHelper<T extends TimeTagable> {

    List<T> fillByHours(ZonedDateTime initialDate, ZonedDateTime finalDate, List<T> listToBeFilled);
    List<T> fillByMinutes(ZonedDateTime initialDate, ZonedDateTime finalDate, List<T> listToBeFilled);
    List<T> fillByHoursAnyway(ZonedDateTime initialDate, ZonedDateTime finalDate, List<T> listToBeFilled);
    List<T> fillByMinutesAnyway(ZonedDateTime initialDate, ZonedDateTime finalDate, List<T> listToBeFilled);

}
