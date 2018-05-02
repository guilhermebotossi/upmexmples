package br.inpe.climaespacial.swd.commons.helpers;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.commons.DefaultTimeZoneConfigurator;
import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultDateTimeHelper.class)
public class DateTimeHelperTest {

    @BeforeClass
    public static void beforeClass() {
        new DefaultTimeZoneConfigurator().init();
    }

    @Inject
    private DateTimeHelper dateTimeHelper;

    @Test
    public void truncate_calledWithNull_returnsNull() {
        ZonedDateTime zdt = dateTimeHelper.truncate(null);

        assertNull(zdt);
    }

    @Test
    public void truncate_called_returnsDateWithTruncatedTime() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T02:10:15Z[UTC]");
        ZonedDateTime ezdt = ZonedDateTime.parse("2017-01-01T00:00:00Z[UTC]");

        ZonedDateTime zdt2 = dateTimeHelper.truncate(zdt1);

        assertNotNull(zdt2);
        assertEquals(ezdt, zdt2);
    }

    @Test
    public void setMaxDayTime_calledWithNull_throws() {

        RuntimeException re = null;

        try {
            dateTimeHelper.setMaxDayTime(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"zonedDateTime\" cannot be null.", re.getMessage());
    }

    @Test
    public void setMaxDayTime_called_returnsZdtWithMaxDayTime() {

        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T01:30:00.0z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-02T01:30:00.0z[UTC]");

        ZonedDateTime maxZdt1 = dateTimeHelper.setMaxDayTime(zdt1);

        assertNotNull(maxZdt1);
        assertEquals(zdt2, maxZdt1);
    }

    @Test
    public void truncateToMinute_called_succeeds() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T01:38:05.100z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T01:00:00.0z[UTC]");

        ZonedDateTime zdt3 = dateTimeHelper.truncateToMinute(zdt1);

        assertNotNull(zdt3);
        assertEquals(zdt2, zdt3);
    }
    
    @Test
    public void truncateToDays_called_succeeds() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-31T01:38:05.100z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T00:00:00.0z[UTC]");

        ZonedDateTime zdt3 = dateTimeHelper.truncateToDays(zdt1);

        assertNotNull(zdt3);
        assertEquals(zdt2, zdt3);
    }

}
