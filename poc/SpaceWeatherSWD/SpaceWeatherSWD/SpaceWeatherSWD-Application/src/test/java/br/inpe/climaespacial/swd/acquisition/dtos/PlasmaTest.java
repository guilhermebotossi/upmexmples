package br.inpe.climaespacial.swd.acquisition.dtos;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(CdiRunner.class)
@AdditionalClasses(Plasma.class)
public class PlasmaTest {

    private static final double DELTA = 0.001;

    private static final double VALUE = 1.0;

    @Inject
    private Plasma plasma;

    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        plasma.setTimeTag(timeTag);

        assertEquals(timeTag, plasma.getTimeTag());
    }

    @Test
    public void densityTest() {
        plasma.setDensity(VALUE);

        assertEquals(VALUE, plasma.getDensity(), DELTA);
    }

    @Test
    public void speedTest() {
        plasma.setSpeed(VALUE);
        
        assertEquals(VALUE, plasma.getSpeed(), DELTA);
    }

    @Test
    public void temperatureTest() {
        plasma.setTemperature(VALUE);
        
        assertEquals(VALUE, plasma.getTemperature(), DELTA);
    }

    @Test
    public void toString_called_returnsStringRepresentation() {
        plasma.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        plasma.setDensity(VALUE);
        plasma.setSpeed(VALUE + 1);
        plasma.setTemperature(VALUE + 2);

        String r = plasma.toString();

        assertEquals("Plasma [timeTag=2017-01-01T12:00Z[UTC], density=1.0, speed=2.0, "
                + "temperature=3.0]", r);
    }

}
