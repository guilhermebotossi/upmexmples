package br.inpe.climaespacial.swd.commons;

import java.time.ZonedDateTime;
import javax.ws.rs.ext.ParamConverter;

public interface ZonedDateTimeParameterConverter extends ParamConverter<ZonedDateTime> {
    
    @Override
    ZonedDateTime fromString(String string);
    
    @Override
    String toString(ZonedDateTime dateTime);
    
}
