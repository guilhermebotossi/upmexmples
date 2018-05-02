package br.inpe.climaespacial.swd.commons;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultZonedDateTimeParameterConverter.class)
public class ZonedDatetimeParameterConverterTest {

    @Inject
    private ZonedDateTimeParameterConverter zonedDateTimeParameterConverter;

    @Test
    public void fromString_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            zonedDateTimeParameterConverter.fromString(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"string\" cannot be null or empty.", re.getMessage());
    }

    @Test
    public void fromString_calledWithEmptyArgument_throws() {
        RuntimeException re = null;

        try {
            zonedDateTimeParameterConverter.fromString("");
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"string\" cannot be null or empty.", re.getMessage());
    }

    @Test
    public void fromString_called_returnsZonedDateTime() {
        ZonedDateTime dateTime1 = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        String string = "2017-01-01";

        ZonedDateTime dateTime2 = zonedDateTimeParameterConverter.fromString(string);

        assertNotNull(string);
        assertEquals(dateTime1, dateTime2);
    }

}
