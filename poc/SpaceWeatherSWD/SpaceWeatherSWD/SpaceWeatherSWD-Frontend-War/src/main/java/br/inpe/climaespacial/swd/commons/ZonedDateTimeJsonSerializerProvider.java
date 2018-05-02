package br.inpe.climaespacial.swd.commons;

import java.time.ZonedDateTime;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Provider
public class ZonedDateTimeJsonSerializerProvider implements ContextResolver<ObjectMapper> {

    private final ObjectMapper objectMapper;

    public ZonedDateTimeJsonSerializerProvider() {
        objectMapper = new ObjectMapper();
        SimpleModule s = new SimpleModule();
        s.addSerializer(ZonedDateTime.class, new ZonedDateTimeJsonSerializer());
        objectMapper.registerModule(s);
    };

    @Override
    public ObjectMapper getContext(Class<?> type) {
        return objectMapper;
    }
}