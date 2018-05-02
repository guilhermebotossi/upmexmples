package br.inpe.climaespacial.swd.indexes;

import java.time.ZonedDateTime;
import javax.inject.Inject;

import org.jglue.cdiunit.CdiRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

@RunWith(CdiRunner.class)
public class RangeDateTest {
    
    @Inject
    private RangeDate rangeData;
    
    @Test
    public void minDateTest() {
        ZonedDateTime minDate = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        rangeData.setMinDate(minDate);

        assertEquals(minDate, rangeData.getMinDate());
    }

    @Test
    public void maxDate() {
        ZonedDateTime maxDate = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        rangeData.setMaxDate(maxDate);

        assertEquals(maxDate, rangeData.getMaxDate());
    }
    
    
}
