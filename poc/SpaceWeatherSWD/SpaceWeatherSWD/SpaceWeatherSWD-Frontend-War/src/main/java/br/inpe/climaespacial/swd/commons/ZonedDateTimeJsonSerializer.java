package br.inpe.climaespacial.swd.commons;

import java.io.IOException;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class ZonedDateTimeJsonSerializer extends StdSerializer<ZonedDateTime> {

    private static final long serialVersionUID = 1L;

    protected ZonedDateTimeJsonSerializer() {
        super(ZonedDateTime.class);
    }

    @Override
    public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jgen, SerializerProvider provider){
        try {
            jgen.writeStartObject();
            jgen.writeNumberField("year", zonedDateTime.getYear());
            jgen.writeNumberField("monthValue", zonedDateTime.getMonthValue());
            jgen.writeNumberField("dayOfMonth", zonedDateTime.getDayOfMonth());
            jgen.writeNumberField("hour", zonedDateTime.getHour());
            jgen.writeNumberField("minute", zonedDateTime.getMinute());
            jgen.writeNumberField("second", zonedDateTime.getSecond());
            jgen.writeEndObject();
        } catch (IOException e) {
            throw new RuntimeException("ZonedDateTime serialization error.");
        }
    }

}
