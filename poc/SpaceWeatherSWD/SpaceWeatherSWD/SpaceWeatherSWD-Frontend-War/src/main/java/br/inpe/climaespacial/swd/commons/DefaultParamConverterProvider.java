package br.inpe.climaespacial.swd.commons;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultParamConverterProvider implements ParamConverterProvider {

    @Override
    @SuppressWarnings("unchecked")
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.equals(ZonedDateTime.class)) {
            return (ParamConverter<T>) new DefaultZonedDateTimeParameterConverter();
        }
        return null;
    }

}
