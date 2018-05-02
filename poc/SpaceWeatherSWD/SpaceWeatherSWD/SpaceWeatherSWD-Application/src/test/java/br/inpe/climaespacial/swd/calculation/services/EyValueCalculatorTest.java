package br.inpe.climaespacial.swd.calculation.services;

import br.inpe.climaespacial.swd.calculation.TestHelper;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasma;
import org.jglue.cdiunit.AdditionalClasses;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
@AdditionalClasses(DefaultEyValueCalculator.class)
public class EyValueCalculatorTest {

    private EyValueCalculator eyValueCalculator;

    @DataPoints
    public static String[][] pairs = TestHelper.getPairs();

    @Before
    public void beforeTest() {
        eyValueCalculator = new DefaultEyValueCalculator();
    }

    @Test
    public void calculate_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            eyValueCalculator.calculate(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"magPlasma\" cannot be null.", re.getMessage());
    }

    @Test
    public void calculate_withSpeedNull_returnsNull() {
        MagPlasma mp = new MagPlasma();
        mp.setSpeed(null);
        mp.setBzGsm(1D);

        Double calculate = eyValueCalculator.calculate(mp);

        assertNull(calculate);
    }

    @Test
    public void calculate_withBzGsmNull_returnsNull() {
        MagPlasma mp = new MagPlasma();
        mp.setSpeed(2D);
        mp.setBzGsm(null);

        Double calculate = eyValueCalculator.calculate(mp);

        assertNull(calculate);
    }

    @Theory
    public void calculate_calledWithValidArgument_succeeds(String[] data) {
        MagPlasma mp = new MagPlasma();
        mp.setSpeed(toDouble(data[7]));
        mp.setBzGsm(toDouble(data[4]));

        Double ey = eyValueCalculator.calculate(mp);
        
        doAssert(toDouble(data[8]), ey, 0.00);
    }
    
    private void doAssert(Double expected, Double calculated, double delta) {
        if(calculated == null) {
            assertNull(expected);
            assertNull(calculated);
        } else {
            assertEquals(expected, calculated, delta);
        }
        
    }

    private Double toDouble(String value) {
        return value == null ? null : Double.valueOf(value);
    }
}
