package br.inpe.climaespacial.swd.acquisition.entities;

import java.time.ZonedDateTime;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(MagEntity.class)
public class MagEntityTest {

    private static final double DELTA = 0.001;

    private static final double VALUE = 1.0;

    @Inject
    private MagEntity magEntity;

    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        magEntity.setTimeTag(timeTag);

        assertEquals(timeTag, magEntity.getTimeTag());
    }

    @Test
    public void bxGsmTest() {
        magEntity.setBxGsm(VALUE);

        assertEquals(VALUE, magEntity.getBxGsm(), DELTA);
    }

    @Test
    public void byGsmTest() {
        magEntity.setByGsm(VALUE);

        assertEquals(VALUE, magEntity.getByGsm(), DELTA);
    }

    @Test
    public void bzGsmTest() {
        magEntity.setBzGsm(VALUE);

        assertEquals(VALUE, magEntity.getBzGsm(), DELTA);
    }

    @Test
    public void latGsmTest() {
        magEntity.setLatGsm(VALUE);

        assertEquals(VALUE, magEntity.getLatGsm(), DELTA);
    }

    @Test
    public void lonGsmTest() {
        magEntity.setLonGsm(VALUE);

        assertEquals(VALUE, magEntity.getLonGsm(), DELTA);
    }

    @Test
    public void btTest() {
        magEntity.setBt(VALUE);

        assertEquals(VALUE, magEntity.getBt(), DELTA);
    }

}
