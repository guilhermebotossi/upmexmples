package br.inpe.climaespacial.swd.calculation.services;

import br.inpe.climaespacial.swd.calculation.TestHelper;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasma;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class DprValueCalculatorTest {

    @DataPoints
    public static final String[][] data = TestHelper.getPairs();

    private DprValueCalculator dprValueCalculator;
    
    @Before
    public void beforeTest() {
        dprValueCalculator = new DefaultDprValueCalculator();
    }

    @Test
    public void calculate_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            dprValueCalculator.calculate(null);
            fail("");
        } catch (RuntimeException e) {
            re = e;
            assertNotNull(re);
            assertEquals("Argument \"magPlasma\" cannot be null.", re.getMessage());
        }

        
    }

    @Test
    public void calculate_calledWithSpeedNull_returnsNull() {
        MagPlasma mp = new MagPlasma();
        mp.setSpeed(null);
        mp.setDensity(3D);

        Double calculated = dprValueCalculator.calculate(mp);

        assertNull(calculated);
    }

    @Test
    public void calculate_calledWithDensityNull_returnsNull() {
        MagPlasma mp = new MagPlasma();
        mp.setSpeed(3D);
        mp.setDensity(null);

        Double calculated = dprValueCalculator.calculate(mp);

        assertNull(calculated);
    }

    @Theory
    public void calculate_calledWithValidArgument_succeed(String[] data) {
        MagPlasma mpp = new MagPlasma();
        mpp.setSpeed(toDouble(data[7]));
        mpp.setDensity(toDouble(data[5]));

        Double dpr = dprValueCalculator.calculate(mpp);

        doAssert(toDouble(data[9]), dpr, 0.00);
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
