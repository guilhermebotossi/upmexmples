package br.inpe.climaespacial.swd.commons.mappers;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNull;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import javax.enterprise.context.Dependent;

@Dependent
public class DefaultDateTimeMapper implements DateTimeMapper {

    @Override
    public ZonedDateTime map(String dateTime) {

        throwIfNull(dateTime, "dateTime");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS").withZone(ZoneId.of("UTC"));

        return ZonedDateTime.parse(dateTime, dtf);
    }

}
