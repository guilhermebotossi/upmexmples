package br.inpe.climaespacial.swd.calculation.services;

import br.inpe.climaespacial.swd.calculation.TestHelper;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasma;
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
public class RmpValueCalculatorTest {

    @DataPoints
    public static String[][] pairs = TestHelper.getPairs();
    private RmpValueCalculator rmpValueCalculator;

    @Before
    public void before() {
        rmpValueCalculator = new DefaultRmpValueCalculator();
    }

    @Test
    public void calculate_calledWithInvalidArgument_throws() {
        RuntimeException re = null;

        try {
            rmpValueCalculator.calculate(null, 0.0);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"magPlasma\" cannot be null.", re.getMessage());
    }

    @Test
    public void calculate_calledWithBzGsmNull_returnsNull() {
        MagPlasma mp = new MagPlasma();
        mp.setBzGsm(null);

        Double rmp = rmpValueCalculator.calculate(mp, 0.0);

        assertNull(rmp);
    }

    @Test
    public void calculate_calledWithDprNull_returnsNull() {
        MagPlasma mp = new MagPlasma();
        mp.setBzGsm(1.0);

        Double rmp = rmpValueCalculator.calculate(mp, null);

        assertNull(rmp);
    }

    @Theory
    public void calculate_calledWithValidArgument_succeeds(String[] data) {
        MagPlasma mpp = new MagPlasma();
        mpp.setBzGsm(toDouble(data[4]));

        Double rmp = rmpValueCalculator.calculate(mpp, toDouble(data[9]));

        doAssert(toDouble(data[10]), rmp, 0.02);
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
