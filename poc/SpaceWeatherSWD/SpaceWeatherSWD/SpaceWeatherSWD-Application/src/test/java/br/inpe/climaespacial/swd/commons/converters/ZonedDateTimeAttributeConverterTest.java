package br.inpe.climaespacial.swd.commons.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.inpe.climaespacial.swd.commons.DefaultTimeZoneConfigurator;

public class ZonedDateTimeAttributeConverterTest {

    private ZonedDateTimeAttributeConverter zonedDateTimeAttributeConverter;

    @BeforeClass
    public static void beforeClass() {
        new DefaultTimeZoneConfigurator().init();
    }

    @Before
    public void beforeTest() {
        zonedDateTimeAttributeConverter = new ZonedDateTimeAttributeConverter();
    }

    @Test
    public void convertToDatabaseColumn_calledWithNull_returnNull() {
        Calendar c = zonedDateTimeAttributeConverter.convertToDatabaseColumn(null);

        assertNull(c);
    }

    @Test
    public void convertToDatabaseColumn_calledWithValidValue_returnCalendar() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Calendar expected = new GregorianCalendar(2017, 0, 01, 12, 0, 0);
        expected.setTimeZone(TimeZone.getTimeZone("UTC"));

        Calendar c = zonedDateTimeAttributeConverter.convertToDatabaseColumn(zdt);

        assertNotNull(c);
        assertEquals(expected.getTime().toString(), c.getTime().toString());
    }

    @Test
    public void convertToEntityAttribute_calledWithNull_returnsNull() {
        ZonedDateTime zdt = zonedDateTimeAttributeConverter.convertToEntityAttribute(null);

        assertNull(zdt);
    }

    @Test
    public void convertToEntityAttribute_calledWithNull_returnsZonedDateTime() {
        Calendar c = new GregorianCalendar(2017, 0, 01, 12, 0, 0);
        ZonedDateTime actual = zonedDateTimeAttributeConverter.convertToEntityAttribute(c);

        ZonedDateTime expected = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        assertNotNull(actual);
        assertEquals(expected, actual);

    }

}
