package br.inpe.climaespacial.swd.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.time.ZonedDateTime;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

@RunWith(CdiRunner.class)
@AdditionalClasses(ZonedDateTimeJsonSerializer.class)
public class ZonedDateTimeJsonSerializerTest {
    
    @Mock
    @Produces
    private JsonGenerator jsonGenerator;
    
    @Mock
    @Produces
    private SerializerProvider serializerProvider;
    
    @Inject
    private ZonedDateTimeJsonSerializer zonedDateTimeJsonSerializer;
    
    @Test
    public void serialize_called_throws() throws IOException {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        RuntimeException re = null;
        doThrow(IOException.class).when(jsonGenerator).writeStartObject();
        
        try {
            zonedDateTimeJsonSerializer.serialize(zonedDateTime, jsonGenerator, serializerProvider);
        } catch(RuntimeException e) {
            re = e;
        }
         
        assertNotNull(re);
        assertEquals("ZonedDateTime serialization error.", re.getMessage());
    }
    
    @Test
    public void serialize_called_succeeds() throws IOException {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        
        zonedDateTimeJsonSerializer.serialize(zonedDateTime, jsonGenerator, serializerProvider);
        
        verify(jsonGenerator).writeStartObject();
        verify(jsonGenerator).writeNumberField("year", zonedDateTime.getYear());
        verify(jsonGenerator).writeNumberField("monthValue", zonedDateTime.getMonthValue());
        verify(jsonGenerator).writeNumberField("dayOfMonth", zonedDateTime.getDayOfMonth());
        verify(jsonGenerator).writeNumberField("hour", zonedDateTime.getHour());
        verify(jsonGenerator).writeNumberField("minute", zonedDateTime.getMinute());
        verify(jsonGenerator).writeNumberField("second", zonedDateTime.getSecond());
        verify(jsonGenerator).writeEndObject();
    }
}
