package br.inpe.climaespacial.swd.commons.mappers;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultDateTimeMapper.class)
public class DateTimeMapperTest {

    @Inject
    private DateTimeMapper dateTimeMapper;

    @Test
    public void map_calledWithNullArgument_returnsMapperDateTime() {
        RuntimeException re = null;

        try {
            dateTimeMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"dateTime\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_calledWithValidArgument_returnsDateTime() {
        String dt = "2003-12-01 04:05:00.000";

        ZonedDateTime zdt = dateTimeMapper.map(dt);

        assertNotNull(zdt);
        assertEquals(1, zdt.getDayOfMonth());
        assertEquals(12, zdt.getMonthValue());
        assertEquals(2003, zdt.getYear());
        assertEquals(4, zdt.getHour());
        assertEquals(5, zdt.getMinute());
    }
}
