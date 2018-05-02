package br.inpe.climaespacial.swd.indexes.b.services;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.calculation.TestHelper;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.b.factories.BIndexFactory;
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
public class BIndexCalculatorTest {

    private BIndexFactory bIndexFactory;

    private BIndexCalculator bIndexCalculator;

    @DataPoints
    public static Object[] data = TestHelper.getAllData();

    @Before
    public void before() {
        bIndexFactory = mock(BIndexFactory.class);
        bIndexCalculator = new DefaultBIndexCalculator(bIndexFactory);
    }

    @Test
    public void calculate_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            bIndexCalculator.calculate(null);
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
            bIndexCalculator.calculate(ha);
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
        ha.setBt(null);
        when(bIndexFactory.create()).thenReturn(new BIndex());

        BIndex bIndex = bIndexCalculator.calculate(ha);

        assertNotNull(bIndex);
        assertNotNull(bIndex.getTimeTag());
        assertEquals(now, bIndex.getTimeTag());
        assertNull(bIndex.getPreValue());
        assertNull(bIndex.getPostValue());
        verify(bIndexFactory).create();
    }

    @Theory
    @SuppressWarnings("unchecked")
    public void calculate_calledWithValidArgument_succeds(Object obj) {
        SimpleEntry<HourlyAverage, String[]> se = (SimpleEntry<HourlyAverage, String[]>) obj;
        HourlyAverage ha = se.getKey();
        String[] data = se.getValue();
        when(bIndexFactory.create()).thenReturn(new BIndex());

        BIndex bIndex = bIndexCalculator.calculate(ha);

        double pre_bt = Double.valueOf(data[1]);
        double pos_bt = Double.valueOf(data[2]);

        assertNotNull(bIndex);
        assertNotNull(bIndex.getTimeTag());
        assertEquals(ha.getTimeTag(), bIndex.getTimeTag());
        assertEquals(pre_bt, bIndex.getPreValue(), 0.02);
        assertEquals(pos_bt, bIndex.getPostValue(), 0.02);
        verify(bIndexFactory).create();
    }

}
