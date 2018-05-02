package br.inpe.climaespacial.swd.average.dtos;

import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(HourlyAverage.class)
public class HourlyAverageTest {

    @Inject
    HourlyAverage hourlyAverage;

    private static final double DELTA = 0.001;

    private static final double VALUE = 1.0;

    @Test
    public void bxGsmTest() {
        hourlyAverage.setBxGsm(VALUE);

        assertEquals(VALUE, hourlyAverage.getBxGsm(), DELTA);
    }

    @Test
    public void byGsmTest() {
        hourlyAverage.setByGsm(VALUE);

        assertEquals(VALUE, hourlyAverage.getByGsm(), DELTA);
    }

    @Test
    public void bzGsmTest() {
        hourlyAverage.setBzGsm(VALUE);

        assertEquals(VALUE, hourlyAverage.getBzGsm(), DELTA);
    }

    @Test
    public void btTest() {
        hourlyAverage.setBt(VALUE);

        assertEquals(VALUE, hourlyAverage.getBt(), DELTA);
    }

    @Test
    public void densityTest() {
        hourlyAverage.setDensity(VALUE);

        assertEquals(VALUE, hourlyAverage.getDensity(), DELTA);
    }

    @Test
    public void speedTest() {
        hourlyAverage.setSpeed(VALUE);

        assertEquals(VALUE, hourlyAverage.getSpeed(), DELTA);
    }

    @Test
    public void temperatureTest() {
        hourlyAverage.setTemperature(VALUE);

        assertEquals(VALUE, hourlyAverage.getTemperature(), DELTA);
    }

    @Test
    public void eyTest() {
        hourlyAverage.setEy(VALUE);

        assertEquals(VALUE, hourlyAverage.getEy(), DELTA);
    }

    @Test
    public void dprTest() {
        hourlyAverage.setDpr(VALUE);

        assertEquals(VALUE, hourlyAverage.getDpr(), DELTA);
    }

    @Test
    public void rmpTest() {
        hourlyAverage.setRmp(VALUE);

        assertEquals(VALUE, hourlyAverage.getRmp(), DELTA);
    }

    @Test
    public void toString_called_returnsStringRepresentation() {
    	
    	hourlyAverage.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        hourlyAverage.setBxGsm(VALUE);
        hourlyAverage.setByGsm(VALUE);
        hourlyAverage.setBzGsm(VALUE);
        hourlyAverage.setBt(VALUE);
        hourlyAverage.setDensity(VALUE);
        hourlyAverage.setSpeed(VALUE);
        hourlyAverage.setTemperature(VALUE);
        hourlyAverage.setEy(VALUE);
        hourlyAverage.setDpr(VALUE);
        hourlyAverage.setRmp(VALUE);
        
        String actualString = hourlyAverage.toString();
        
        String expectedString = "HourlyAverage [timeTag=2017-01-01T12:00Z[UTC]bxGsm=1.0, byGsm=1.0,"
        		+ " bzGsm=1.0, bt=1.0, density=1.0, speed=1.0, temperature=1.0, ey=1.0, dpr=1.0, rmp=1.0]";
                
        assertEquals(expectedString, actualString);
        
    }

}
