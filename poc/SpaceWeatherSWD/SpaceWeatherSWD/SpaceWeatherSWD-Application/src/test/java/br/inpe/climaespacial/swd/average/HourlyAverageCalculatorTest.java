package br.inpe.climaespacial.swd.average;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.calculation.TestHelper;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasmaCalculated;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultHourlyAverageCalculator.class)
public class HourlyAverageCalculatorTest {

    private final ZonedDateTime zonedDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

    @Inject
    private HourlyAverageCalculator hourlyAverageCalculator;

    @Test
    public void calculate_calledWithNullDateTime_throws() {
        RuntimeException re = null;

        try {
            hourlyAverageCalculator.calculate(null, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"dateTime\" cannot be null.", re.getMessage());

    }

    @Test
    public void calculate_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            hourlyAverageCalculator.calculate(zonedDateTime, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"magPlasmaCalculatedList\" cannot be null.", re.getMessage());

    }

    @Test
    public void calculate_calledWith0Elements_succeed() {
        List<MagPlasmaCalculated> pairs = new ArrayList<>();

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNull(ha.getBt());
        assertNull(ha.getBxGsm());
        assertNull(ha.getByGsm());
        assertNull(ha.getBzGsm());
        assertNull(ha.getDensity());
        assertNull(ha.getDpr());
        assertNull(ha.getEy());
        assertNull(ha.getSpeed());
        assertNull(ha.getTemperature());
        assertNull(ha.getRmp());
    }

    @Test
    public void calculate_calledWithLessThan31Elements_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 30);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNull(ha.getBt());
        assertNull(ha.getBxGsm());
        assertNull(ha.getByGsm());
        assertNull(ha.getBzGsm());
        assertNull(ha.getDensity());
        assertNull(ha.getDpr());
        assertNull(ha.getEy());
        assertNull(ha.getSpeed());
        assertNull(ha.getTemperature());
        assertNull(ha.getRmp());
    }

    @Test
    public void calculate_calledWith31_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertEquals(4.048064516129032, ha.getBt(), 0.001);
        assertEquals(0.465483870967741, ha.getBxGsm(), 0.001);
        assertEquals(-0.06451612903225808, ha.getByGsm(), 0.001);
        assertEquals(-3.0254838709677423, ha.getBzGsm(), 0.001);
        assertEquals(6.597741935483871, ha.getDensity(), 0.001);
        assertEquals(485109.0967741936, ha.getTemperature(), 0.001);
        assertEquals(717.8645161290323, ha.getSpeed(), 0.001);
        assertEquals(2.1674193548387097, ha.getEy(), 0.001);
        assertEquals(5.610967741935484, ha.getDpr(), 0.001);
        assertEquals(8.609354838709677, ha.getRmp(), 0.001);
    }

    @Test
    public void calculate_calledWithMoreThan31Elements_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair();

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertEquals(4.565524476, ha.getBt(), 0.001);
        assertEquals(2.70193007, ha.getBxGsm(), 0.001);
        assertEquals(-1.441384615, ha.getByGsm(), 0.001);
        assertEquals(-0.3478881119, ha.getBzGsm(), 0.001);
        assertEquals(6.35820979, ha.getDensity(), 0.001);
        assertEquals(472655.0601, ha.getTemperature(), 0.001);
        assertEquals(688.0033566, ha.getSpeed(), 0.001);
        assertEquals(0.2615664336, ha.getEy(), 0.001);
        assertEquals(4.941846154, ha.getDpr(), 0.001);
        assertEquals(8.915832168, ha.getRmp(), 0.001);
    }

    @Test
    public void calculate_calledWithLessThanMinimumBt_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);
        pairs.get(0).setBt(null);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNull(ha.getBt());
    }

    @Test
    public void calculate_calledWithMinimumBt_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getBt());
        assertEquals(4.048064516129032, ha.getBt(), 0.001);
    }

    @Test
    public void calculate_calledWithMoreThanMinimumBt_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair();

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha); 
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getBt());
        assertEquals(4.565524476, ha.getBt(), 0.001);
    }

    @Test
    public void calculate_calledWithLessThanMinimumBxGsm_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);
        pairs.get(0).setBxGsm(null);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNull(ha.getBxGsm());
    }

    @Test
    public void calculate_calledWithMinimumBxGsm_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getBxGsm());
        assertEquals(0.465483870967741, ha.getBxGsm(), 0.001);
    }

    @Test
    public void calculate_calledWithMoreThanMinimumBxGsm_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair();

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getBxGsm());
        assertEquals(2.70193007, ha.getBxGsm(), 0.001);
    }

    @Test
    public void calculate_calledWithLessThanMinimumByGsm_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);
        pairs.get(0).setByGsm(null);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNull(ha.getByGsm());
    }

    @Test
    public void calculate_calledWithMinimumByGsm_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getByGsm());
        assertEquals(-0.06451612903225808, ha.getByGsm(), 0.001);
    }

    @Test
    public void calculate_calledWithMoreThanMinimumByGsm_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair();

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getByGsm());
        assertEquals(-1.441384615, ha.getByGsm(), 0.001);
    }

    @Test
    public void calculate_calledWithLessThanMinimumBzGsm_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);
        pairs.get(0).setBzGsm(null);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNull(ha.getBzGsm());
    }

    @Test
    public void calculate_calledWithMinimumBzGsm_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getBzGsm());
        assertEquals(-3.0254838709677423, ha.getBzGsm(), 0.001);
    }

    @Test
    public void calculate_calledWithMoreThanMinimumBzGsm_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair();

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getBzGsm());
        assertEquals(-0.3478881119, ha.getBzGsm(), 0.001);
    }

    @Test
    public void calculate_calledWithLessThanMinimumDensity_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);
        pairs.get(0).setDensity(null);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNull(ha.getDensity());
    }

    @Test
    public void calculate_calledWithMinimumDensity_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getDensity());
        assertEquals(6.597741935483871, ha.getDensity(), 0.001);
    }

    @Test
    public void calculate_calledWithMoreThanMinimumDensity_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair();

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getDensity());
        assertEquals(6.35820979, ha.getDensity(), 0.001);
    }

    @Test
    public void calculate_calledWithLessThanMinimumTemperature_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);
        pairs.get(0).setTemperature(null);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNull(ha.getTemperature());
    }

    @Test
    public void calculate_calledWithMinimumTemperature_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getTemperature());
        assertEquals(485109.0967741936, ha.getTemperature(), 0.001);
    }

    @Test
    public void calculate_calledWithMoreThanMinimumTemperature_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair();

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getTemperature());
        assertEquals(472655.0601, ha.getTemperature(), 0.001);
    }

    @Test
    public void calculate_calledWithLessThanMinimumSpeed_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);
        pairs.get(0).setSpeed(null);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNull(ha.getSpeed());
    }

    @Test
    public void calculate_calledWithMinimumSpeed_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getSpeed());
        assertEquals(717.8645161290323, ha.getSpeed(), 0.001);
    }

    @Test
    public void calculate_calledWithMoreThanMinimumSpeed_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair();

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getSpeed());
        assertEquals(688.0033566, ha.getSpeed(), 0.001);
    }

    @Test
    public void calculate_calledWithLessThanMinimumDpr_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);
        pairs.get(0).setDpr(null);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNull(ha.getDpr());
    }

    @Test
    public void calculate_calledWithMinimumDpr_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getDpr());
        assertEquals(5.610967741935484, ha.getDpr(), 0.001);
    }

    @Test
    public void calculate_calledWithMoreThanMinimumDpr_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair();

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getDpr());
        assertEquals(4.941846154, ha.getDpr(), 0.001);
    }

    @Test
    public void calculate_calledWithLessThanMinimumEy_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);
        pairs.get(0).setEy(null); 

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNull(ha.getEy());
    }

    @Test
    public void calculate_calledWithMinimumEy_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getEy());
        assertEquals(2.1674193548387097, ha.getEy(), 0.001);
    }

    @Test
    public void calculate_calledWithMoreThanMinimumEy_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair();

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getEy());
        assertEquals(0.2615664336, ha.getEy(), 0.001);
    }

    @Test
    public void calculate_calledWithLessThanMinimumRmp_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);
        pairs.get(0).setRmp(null);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNull(ha.getRmp());
    }

    @Test
    public void calculate_calledWithMinimumRmp_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair().subList(0, 31);

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getRmp());
        assertEquals(8.609354838709677, ha.getRmp(), 0.001);
    }

    @Test
    public void calculate_calledWithMoreThanMinimumRmp_succeed() {
        List<MagPlasmaCalculated> pairs = TestHelper.getMagPlasmaPair();

        HourlyAverage ha = hourlyAverageCalculator.calculate(zonedDateTime, pairs);

        assertNotNull(ha);
        assertNotNull(ha.getTimeTag());
        assertSame(zonedDateTime, ha.getTimeTag());
        assertNotNull(ha.getRmp());
        assertEquals(8.915832168, ha.getRmp(), 0.001);
    }
}
