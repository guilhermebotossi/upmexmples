package br.inpe.climaespacial.swd.commons.converters;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ZonedDateTimeAttributeConverter implements AttributeConverter<ZonedDateTime, Calendar> {

    @Override
    public Calendar convertToDatabaseColumn(ZonedDateTime zonedDateTime) {
        return zonedDateTime == null ? null : GregorianCalendar.from(zonedDateTime);
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(Calendar calendar) {
        return calendar == null ? null : ((GregorianCalendar) calendar).toZonedDateTime();
    }
}
