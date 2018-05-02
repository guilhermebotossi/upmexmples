package br.inpe.climaespacial.swd.indexes.z.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.calculation.TestHelper;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import br.inpe.climaespacial.swd.indexes.z.factories.ZIndexFactory;

@RunWith(Theories.class)
public class ZIndexCalculatorTest {

    private static double DELTA = 0.01;

    private ZIndexCalculator zIndexCalculator;
    
    private ZIndexFactory zIndexFactory; 

    @DataPoints
    public static Object[] data = TestHelper.getDataEvery3Hours();

    @Before
    public void before() {
        zIndexFactory = mock(ZIndexFactory.class);
        zIndexCalculator = new DefaultZIndexCalculator(zIndexFactory);
    }

    @Test
    public void calculate_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            zIndexCalculator.calculate(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Parametro \"hourlyAverageList\" null/empty.", re.getMessage());
    }

    @Test
    public void calculate_calledWithEmptyList_throws() {
        List<HourlyAverage> hal = new ArrayList<>();
        RuntimeException re = null;
        try {
            zIndexCalculator.calculate(hal);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Parametro \"hourlyAverageList\" null/empty.", re.getMessage());
    }

    @Test
    public void calculate_calledWithValidArgumentButNullTimeTag_throws() {
        List<HourlyAverage> hal = new ArrayList<>();
        hal.add(new HourlyAverage());
        RuntimeException re = null;
        try {
            zIndexCalculator.calculate(hal);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Propriedade \"hourlyAverage.timeTag\" null.", re.getMessage());
    }

    @Test
    public void calculate_calledWithValidArgumentButNullBz_succeds() {
        List<HourlyAverage> hal = new ArrayList<>();
        HourlyAverage ha = new HourlyAverage();
        ZonedDateTime now = ZonedDateTime.now();
        ha.setTimeTag(now);
        ha.setBzGsm(null);
        hal.add(ha);
        when(zIndexFactory.create()).thenReturn(new ZIndex());

        ZIndex zIndex = zIndexCalculator.calculate(hal);

        assertNotNull(zIndex);
        assertNotNull(zIndex.getTimeTag());
        assertEquals(now, zIndex.getTimeTag());
        assertNull(zIndex.getPreValue());
        assertNull(zIndex.getPostValue());
        verify(zIndexFactory).create();
    }

    @Theory
    public void calculate_calledWithValidArgument_succeds(SimpleEntry<List<HourlyAverage>, String[]> se) {
        List<HourlyAverage> hal = se.getKey();
        String[] v = se.getValue();
        ZonedDateTime timeTag = getTimeTag(hal);
        when(zIndexFactory.create()).thenAnswer(z -> new ZIndex());

        ZIndex zIndex = zIndexCalculator.calculate(hal);

        Double pre_bz = toDoubleIfNotNull(v[3]);
        Double pos_bz = toDoubleIfNotNull(v[4]);

        assertNotNull(zIndex);
        assertNotNull(zIndex.getTimeTag());
        assertEquals(timeTag, zIndex.getTimeTag());
        assertEquals(pre_bz, zIndex.getPreValue(), DELTA);
        assertEquals(pos_bz, zIndex.getPostValue(), DELTA);
        verify(zIndexFactory).create();
    }

    @Test
    public void calculate_calledShouldReturnZeroWhenIndexIsEqualsToZero_succeds() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T13:00:00z[UTC]");
        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-01T14:00:00z[UTC]");

        HourlyAverage ha1 = new HourlyAverage();
        ha1.setTimeTag(zdt1);
        ha1.setBzGsm(0.00);

        HourlyAverage ha2 = new HourlyAverage();
        ha2.setTimeTag(zdt2);

        HourlyAverage ha3 = new HourlyAverage();
        ha3.setTimeTag(zdt3);

        List<HourlyAverage> hal = Arrays.asList(ha1, ha2, ha3);
        when(zIndexFactory.create()).thenReturn(new ZIndex());

        ZIndex zIndex = zIndexCalculator.calculate(hal);

        assertNotNull(zIndex);
        assertNotNull(zIndex.getTimeTag());
        assertEquals(zdt3, zIndex.getTimeTag());
        assertEquals(0.0, zIndex.getPreValue(), DELTA);
        assertEquals(0.0, zIndex.getPostValue(), DELTA);
        verify(zIndexFactory).create();
    }

    @Test
    public void calculate_calledShouldReturnZeroWhenIndexIsMoreThanZero_succeds() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T13:00:00z[UTC]");
        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-01T14:00:00z[UTC]");

        HourlyAverage ha1 = new HourlyAverage();
        ha1.setTimeTag(zdt1);
        ha1.setBzGsm(1.00);

        HourlyAverage ha2 = new HourlyAverage();
        ha2.setTimeTag(zdt2);

        HourlyAverage ha3 = new HourlyAverage();
        ha3.setTimeTag(zdt3);

        List<HourlyAverage> hal = Arrays.asList(ha1, ha2, ha3);
        when(zIndexFactory.create()).thenReturn(new ZIndex());

        ZIndex zIndex = zIndexCalculator.calculate(hal);

        assertNotNull(zIndex);
        assertNotNull(zIndex.getTimeTag());
        assertEquals(zdt3, zIndex.getTimeTag());
        assertEquals(1.0, zIndex.getPreValue(), DELTA);
        assertEquals(0.0, zIndex.getPostValue(), DELTA);
        verify(zIndexFactory).create();
    }

    @Test
    public void calculate_calledShouldReturnFiveDotNineNineWhenIndexIsEqualsToNegativeForty_succeds() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T13:00:00z[UTC]");
        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-01T14:00:00z[UTC]");

        HourlyAverage ha1 = new HourlyAverage();
        ha1.setTimeTag(zdt1);
        ha1.setBzGsm(-40.00);

        HourlyAverage ha2 = new HourlyAverage();
        ha2.setTimeTag(zdt2);

        HourlyAverage ha3 = new HourlyAverage();
        ha3.setTimeTag(zdt3);

        List<HourlyAverage> hal = Arrays.asList(ha1, ha2, ha3);
        when(zIndexFactory.create()).thenReturn(new ZIndex());

        ZIndex zIndex = zIndexCalculator.calculate(hal);

        assertNotNull(zIndex);
        assertNotNull(zIndex.getTimeTag());
        assertEquals(zdt3, zIndex.getTimeTag());
        assertEquals(-40, zIndex.getPreValue(), DELTA);
        assertEquals(5.99, zIndex.getPostValue(), DELTA);
        verify(zIndexFactory).create();
    }

    @Test
    public void calculate_calledShouldReturnFiveDotNineNineWhenIndexIsLessThanNegativeForty_succeds() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T13:00:00z[UTC]");
        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-01T14:00:00z[UTC]");

        HourlyAverage ha1 = new HourlyAverage();
        ha1.setTimeTag(zdt1);
        ha1.setBzGsm(-50.00);

        HourlyAverage ha2 = new HourlyAverage();
        ha2.setTimeTag(zdt2);

        HourlyAverage ha3 = new HourlyAverage();
        ha3.setTimeTag(zdt3);

        List<HourlyAverage> hal = Arrays.asList(ha1, ha2, ha3);
        when(zIndexFactory.create()).thenReturn(new ZIndex());

        ZIndex zIndex = zIndexCalculator.calculate(hal);

        assertNotNull(zIndex);
        assertNotNull(zIndex.getTimeTag());
        assertEquals(zdt3, zIndex.getTimeTag());
        assertEquals(-50, zIndex.getPreValue(), DELTA);
        assertEquals(5.99, zIndex.getPostValue(), DELTA);
        verify(zIndexFactory).create();
    }

    private ZonedDateTime getTimeTag(List<HourlyAverage> hourlyAverageList) {
        int size = hourlyAverageList.size();

        return hourlyAverageList.get(size - 1).getTimeTag();
    }

    private Double toDoubleIfNotNull(String value) {
        return value != null ? Double.valueOf(value) : null;
    }
}
