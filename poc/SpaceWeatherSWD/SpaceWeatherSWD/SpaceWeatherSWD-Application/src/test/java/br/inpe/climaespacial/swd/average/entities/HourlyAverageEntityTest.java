package br.inpe.climaespacial.swd.average.entities;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(HourlyAverageEntity.class)
public class HourlyAverageEntityTest {

    @Inject
    private HourlyAverageEntity hourlyAverageEntity;

    private static final double DELTA = 0.001;

    private static final double VALUE = 1.0;

    
    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag  = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        
        hourlyAverageEntity.setTimeTag(timeTag);
        
        assertSame(timeTag, hourlyAverageEntity.getTimeTag());
    }
    
    @Test
    public void bxGsmTest() {
        hourlyAverageEntity.setBxGsm(VALUE);

        assertEquals(VALUE, hourlyAverageEntity.getBxGsm(), DELTA);
    }

    @Test
    public void byGsmTest() {
        hourlyAverageEntity.setByGsm(VALUE);

        assertEquals(VALUE, hourlyAverageEntity.getByGsm(), DELTA);
    }

    @Test
    public void bzGsmTest() {
        hourlyAverageEntity.setBzGsm(VALUE);

        assertEquals(VALUE, hourlyAverageEntity.getBzGsm(), DELTA);
    }

    @Test
    public void btTest() {
        hourlyAverageEntity.setBt(VALUE);

        assertEquals(VALUE, hourlyAverageEntity.getBt(), DELTA);
    }

    @Test
    public void densityTest() {
        hourlyAverageEntity.setDensity(VALUE);

        assertEquals(VALUE, hourlyAverageEntity.getDensity(), DELTA);
    }

    @Test
    public void speedTest() {
        hourlyAverageEntity.setSpeed(VALUE);

        assertEquals(VALUE, hourlyAverageEntity.getSpeed(), DELTA);
    }

    @Test
    public void temperatureTest() {
        hourlyAverageEntity.setTemperature(VALUE);

        assertEquals(VALUE, hourlyAverageEntity.getTemperature(), DELTA);
    }

    @Test
    public void eyTest() {
        hourlyAverageEntity.setEy(VALUE);

        assertEquals(VALUE, hourlyAverageEntity.getEy(), DELTA);
    }

    @Test
    public void dprTest() {
        hourlyAverageEntity.setDpr(VALUE);

        assertEquals(VALUE, hourlyAverageEntity.getDpr(), DELTA);
    }

    @Test
    public void rmpTest() {
        hourlyAverageEntity.setRmp(VALUE);

        assertEquals(VALUE, hourlyAverageEntity.getRmp(), DELTA);
    }

}
