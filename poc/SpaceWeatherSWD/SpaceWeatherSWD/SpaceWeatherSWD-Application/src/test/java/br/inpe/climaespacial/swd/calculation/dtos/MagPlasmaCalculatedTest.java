package br.inpe.climaespacial.swd.calculation.dtos;

import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(MagPlasmaCalculated.class)
public class MagPlasmaCalculatedTest {

    private static final double DELTA = 0.001;

    private static final double VALUE = 1.0;

    @Inject
    private MagPlasmaCalculated magPlasmaCalculated;

    @Test
    public void bxGsmTest() {
        magPlasmaCalculated.setBxGsm(VALUE);

        assertEquals(VALUE, magPlasmaCalculated.getBxGsm(), DELTA);
    }

    @Test
    public void byGsmTest() {
        magPlasmaCalculated.setByGsm(VALUE);

        assertEquals(VALUE, magPlasmaCalculated.getByGsm(), DELTA);
    }

    @Test
    public void bzGsmTest() {
        magPlasmaCalculated.setBzGsm(VALUE);

        assertEquals(VALUE, magPlasmaCalculated.getBzGsm(), DELTA);
    }

    @Test
    public void btTest() {
        magPlasmaCalculated.setBt(VALUE);

        assertEquals(VALUE, magPlasmaCalculated.getBt(), DELTA);
    }

    @Test
    public void densityTest() {
        magPlasmaCalculated.setDensity(VALUE);

        assertEquals(VALUE, magPlasmaCalculated.getDensity(), DELTA);
    }

    @Test
    public void speedTest() {
        magPlasmaCalculated.setSpeed(VALUE);

        assertEquals(VALUE, magPlasmaCalculated.getSpeed(), DELTA);
    }

    @Test
    public void temperatureTest() {
        magPlasmaCalculated.setTemperature(VALUE);

        assertEquals(VALUE, magPlasmaCalculated.getTemperature(), DELTA);
    }

    @Test
    public void eyTest() {
        magPlasmaCalculated.setEy(VALUE);

        assertEquals(VALUE, magPlasmaCalculated.getEy(), DELTA);
    }

    @Test
    public void dprTest() {
        magPlasmaCalculated.setDpr(VALUE);

        assertEquals(VALUE, magPlasmaCalculated.getDpr(), DELTA);
    }

    @Test
    public void rmpTest() {
        magPlasmaCalculated.setRmp(VALUE);

        assertEquals(VALUE, magPlasmaCalculated.getRmp(), DELTA);
    }

    @Test
    public void toString_called_returnsStringRepresentation() {
        magPlasmaCalculated.setBxGsm(VALUE);
        magPlasmaCalculated.setByGsm(VALUE + 1);
        magPlasmaCalculated.setBzGsm(VALUE + 2);
        magPlasmaCalculated.setBt(VALUE + 3);
        magPlasmaCalculated.setDensity(VALUE + 4);
        magPlasmaCalculated.setSpeed(VALUE + 5);
        magPlasmaCalculated.setTemperature(VALUE + 6);
        magPlasmaCalculated.setEy(VALUE + 7);
        magPlasmaCalculated.setDpr(VALUE + 8);
        magPlasmaCalculated.setRmp(VALUE + 9);

        String r = magPlasmaCalculated.toString();

        assertEquals("MagPlasmaCalculated [bxGsm=1.0, byGsm=2.0, "
				+ "bzGsm=3.0, bt=4.0, density=5.0, speed=6.0, "
                                + "temperature=7.0, ey=8.0, dpr=9.0, rmp=10.0]", r);
    }

}
