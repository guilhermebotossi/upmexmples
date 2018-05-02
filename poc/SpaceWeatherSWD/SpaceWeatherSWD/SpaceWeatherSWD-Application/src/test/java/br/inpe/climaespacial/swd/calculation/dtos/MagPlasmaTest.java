package br.inpe.climaespacial.swd.calculation.dtos;

import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(MagPlasma.class)
public class MagPlasmaTest {

    private static final double DELTA = 0.001;

    private static final double VALUE = 1.0;

    @Inject
    private MagPlasma magPlasma;

    @Test
    public void bzGsmTest() {
        magPlasma.setBzGsm(VALUE);

        assertEquals(VALUE, magPlasma.getBzGsm(), DELTA);
    }

    @Test
    public void speedTest() {
        magPlasma.setSpeed(VALUE);

        assertEquals(VALUE, magPlasma.getSpeed(), DELTA);
    }

    @Test
    public void densityTest() {
        magPlasma.setDensity(VALUE);

        assertEquals(VALUE, magPlasma.getDensity(), DELTA);
    }

    @Test
    public void toString_called_returnsStringRepresentation() {
        magPlasma.setBzGsm(VALUE);
        magPlasma.setSpeed(VALUE + 1);
        magPlasma.setDensity(VALUE + 2);

        String r = magPlasma.toString();

        assertEquals("MagPlasma [bzGsm=1.0, speed=2.0, density=3.0]", r);
    }

}
