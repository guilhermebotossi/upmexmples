package br.inpe.climaespacial.swd.acquisition.entities;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(CdiRunner.class)
@AdditionalClasses(PlasmaEntity.class)
public class PlasmaEntityTest {

    private static final double DELTA = 0.001;

    private static final double VALUE = 1.0;

    @Inject
    private PlasmaEntity plasmaEntity;

    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        plasmaEntity.setTimeTag(timeTag);

        assertEquals(timeTag, plasmaEntity.getTimeTag());
    }

    @Test
    public void densityTest() {
        plasmaEntity.setDensity(VALUE);

        assertEquals(VALUE, plasmaEntity.getDensity(), DELTA);
    }

    @Test
    public void speedTest() {
        plasmaEntity.setSpeed(VALUE);
        
        assertEquals(VALUE, plasmaEntity.getSpeed(), DELTA);
    }

    @Test
    public void temperatureTest() {
        plasmaEntity.setTemperature(VALUE);
        
        assertEquals(VALUE, plasmaEntity.getTemperature(), DELTA);
    }

}
