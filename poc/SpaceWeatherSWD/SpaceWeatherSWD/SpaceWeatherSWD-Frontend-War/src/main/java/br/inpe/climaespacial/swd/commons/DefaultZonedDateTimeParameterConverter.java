package br.inpe.climaespacial.swd.commons;

import static br.inpe.climaespacial.swd.commons.helpers.ThrowHelper.throwIfNullOrEmpty;

import java.time.ZonedDateTime;
import javax.enterprise.context.Dependent;

@Dependent
public class DefaultZonedDateTimeParameterConverter implements ZonedDateTimeParameterConverter {

    @Override
    public ZonedDateTime fromString(String string) {

        throwIfNullOrEmpty(string, "string");

        StringBuilder sb = new StringBuilder();
        
        sb.append(string).append("T00:00:00z[UTC]");

        ZonedDateTime dateTime = ZonedDateTime.parse(sb.toString());

        return dateTime;
    }

    @Override
    public String toString(ZonedDateTime dateTime) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
