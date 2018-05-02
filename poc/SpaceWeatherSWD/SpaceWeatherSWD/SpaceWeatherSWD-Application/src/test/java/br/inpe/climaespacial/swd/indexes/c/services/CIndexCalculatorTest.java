package br.inpe.climaespacial.swd.indexes.c.services;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.calculation.TestHelper;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.factories.CIndexFactory;
import java.time.ZonedDateTime;
import java.util.AbstractMap.SimpleEntry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(Theories.class)
public class CIndexCalculatorTest {

    private CIndexFactory cIndexFactory;

    private CIndexCalculator cIndexCalculator;

    @DataPoints
    public static Object[] data = TestHelper.getAllData();

    @Before
    public void before() {
        cIndexFactory = mock(CIndexFactory.class);
        cIndexCalculator = new DefaultCIndexCalculator(cIndexFactory);
    }

    @Test
    public void calculate_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            cIndexCalculator.calculate(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"hourlyAverage\" cannot be null.", re.getMessage());
    }

    @Test
    public void calculate_calledWithValidArgumentButNullTimeTag_throws() {
        HourlyAverage ha = new HourlyAverage();
        RuntimeException re = null;
        try {
            cIndexCalculator.calculate(ha);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Propriedade \"hourlyAverage.timeTag\" null.", re.getMessage());
    }

    @Test
    public void calculate_calledWithValidArgumentButNullBt_succeds() {
        HourlyAverage ha = new HourlyAverage();
        ZonedDateTime now = ZonedDateTime.now();
        ha.setTimeTag(now);
        ha.setRmp(null);
        when(cIndexFactory.create()).thenReturn(new CIndex());

        CIndex cIndex = cIndexCalculator.calculate(ha);

        assertNotNull(cIndex);
        assertNotNull(cIndex.getTimeTag());
        assertEquals(now, cIndex.getTimeTag());
        assertNull(cIndex.getPreValue());
        assertNull(cIndex.getPostValue());
        verify(cIndexFactory).create();
    }

    @Theory
    @SuppressWarnings("unchecked")
    public void calculate_calledWithValidArgument_succeds(Object obj) {
        SimpleEntry<HourlyAverage, String[]> se = (SimpleEntry<HourlyAverage, String[]>) obj;
        HourlyAverage ha = se.getKey();
        String[] data = se.getValue();
        Double rmp = calculateRmp(ha.getDensity(), ha.getSpeed(), ha.getBzGsm());
        ha.setRmp(rmp);
        when(cIndexFactory.create()).thenReturn(new CIndex());

        CIndex cIndex = cIndexCalculator.calculate(ha);

        Double pre_cmp = toDoubleIfNotNull(data[7]);
        Double pos_cmp = toDoubleIfNotNull(data[8]);

        assertNotNull(cIndex);
        assertNotNull(cIndex.getTimeTag());
        assertEquals(ha.getTimeTag(), cIndex.getTimeTag());
        if (pre_cmp != null) {
            assertEquals(pre_cmp, cIndex.getPreValue(), 0.01);
        } else {
            assertNull(pre_cmp);
        }

        if (pos_cmp != null) {
            assertEquals(pos_cmp, cIndex.getPostValue(), 0.01);
        } else {
            assertNull(pos_cmp);
        }
        verify(cIndexFactory).create();
    }

    private Double toDoubleIfNotNull(String str) {
        Double value = null;
        if (str != null) {
            value = Double.valueOf(str);
        }
        return value;
    }

    private Double calculateRmp(Double density, Double speed, Double bz) {
        if (density == null || speed == null || bz == null) {
            return null;
        }

        Double dpr = (1.672E-6 * density) * speed * speed;
        Double rmp = (10.22 + 1.29 * (Math.tanh(0.184 * (bz + 8.14)))) * Math.pow(dpr, (-1.0 / 6.6));
        return rmp;
    }

}
