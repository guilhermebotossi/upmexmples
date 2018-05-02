package br.inpe.climaespacial.swd.indexes.v.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.calculation.TestHelper;
import br.inpe.climaespacial.swd.calculation.VIndexTestData;
import br.inpe.climaespacial.swd.commons.exceptions.ArgumentException;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.factories.VIndexFactory;

@RunWith(Theories.class)
public class VIndexCalculatorTest {

    private VIndexFactory vIndexFactory;

    private ListFactory<Double> doubleListFactory;

    private VIndexCalculator vIndexCalculator;

    @DataPoints("vIndexData")
    public static Object[] data = TestHelper.getVIndexData();

    @DataPoints("vIndexPostValueLimits")
    public static Object[] dataVIndexPostValue = TestHelper.getVIndexDataToTestVIndexPostValueLimits();

    @DataPoints("vIndexData2")
    public static Object[] data2 = TestHelper.getVIndexDataToEval();

    @Before
    @SuppressWarnings("unchecked")
    public void before() {
        vIndexFactory = mock(VIndexFactory.class);
        doubleListFactory = mock(ListFactory.class);
        vIndexCalculator = new DefaultVIndexCalculator(vIndexFactory, doubleListFactory);
    }

    @Test
    public void calculate_withHourlyAvgListNull_throws() {

        ArgumentException ae = null;

        try {
            vIndexCalculator.calculate(null, null);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals("Argument \"hourlyAverageList\" cannot be null.", ae.getMessage());
    }

    @Test
    public void calculate_withVIndexListNull_throws() {

        HourlyAverage ha = new HourlyAverage();
        ha.setTimeTag(ZonedDateTime.parse("2017-01-01T01:01:01z[UTC]"));
        List<HourlyAverage> hal = Arrays.asList(ha);

        ArgumentException ae = null;

        try {
            vIndexCalculator.calculate(hal, null);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals("Argument \"vIndexList\" cannot be null.", ae.getMessage());
    }

    @Test
    public void calculate_hourlyAverageListLt26_throws() {
        RuntimeException re = null;

        List<HourlyAverage> hal = getHourlyAverageList(25);
        List<VIndex> vil = getVIndexList(0);

        try {
            vIndexCalculator.calculate(hal, vil);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"hourlyAverageList\" shall have 26 elements.", re.getMessage());
    }

    @Test
    public void calculate_vIndexListLt24_throws() {
        RuntimeException re = null;

        List<HourlyAverage> hal = getHourlyAverageList(26);
        List<VIndex> vil = getVIndexList(23);

        try {
            vIndexCalculator.calculate(hal, vil);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"vIndexList\" shall have 24 elements.", re.getMessage());
    }

    @Test
    public void calculate_withHourlyAvgSpeedListEmpty_returnsNull() {
        List<HourlyAverage> hal = getHourlyAverageList(26);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T01:01:01z[UTC]");
        hal.get(25).setTimeTag(zdt1);

        List<VIndex> vil = getVIndexList(24);

        when(vIndexFactory.create()).thenReturn(new VIndex());

        VIndex vi = vIndexCalculator.calculate(hal, vil);

        assertNotNull(vi);
        assertEquals(zdt1, vi.getTimeTag());
        assertNull(vi.getPreValue());
        assertNull(vi.getPostValue());
        assertFalse(vi.getIsCycleBegin());
    }

    @Test
    public void calculate_withHourlyAvgSpeedListHasOne_returnsNull() {
        List<HourlyAverage> hal = getHourlyAverageList(26);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T01:01:01z[UTC]");
        hal.get(25).setTimeTag(zdt1);
        hal.get(25).setSpeed(2.0);

        List<VIndex> vil = getVIndexList(24);

        when(vIndexFactory.create()).thenReturn(new VIndex());

        VIndex vi = vIndexCalculator.calculate(hal, vil);

        assertNotNull(vi);
        assertEquals(zdt1, vi.getTimeTag());
        assertNull(vi.getPreValue());
        assertNull(vi.getPostValue());
        assertFalse(vi.getIsCycleBegin());
    }

    @Test
    public void calculate_withHourlyAvgLastSpeedValueEqNull_returnsNull() {
        List<HourlyAverage> hal = getHourlyAverageList(26);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]");
        hal.get(25).setTimeTag(zdt1);

        hal.get(24).setTimeTag(ZonedDateTime.parse("2017-01-01T02:00:00z[UTC]"));
        hal.get(24).setSpeed(2.0);

        hal.get(23).setTimeTag(ZonedDateTime.parse("2017-01-01T01:00:00z[UTC]"));
        hal.get(23).setSpeed(3.0);

        List<VIndex> vil = getVIndexList(24);

        when(vIndexFactory.create()).thenReturn(new VIndex());

        VIndex vi = vIndexCalculator.calculate(hal, vil);

        assertNotNull(vi);
        assertEquals(zdt1, vi.getTimeTag());
        assertNull(vi.getPreValue());
        assertNull(vi.getPostValue());
        assertFalse(vi.getIsCycleBegin());
    }

    @Test
    public void calculate_withGapBetweenHourlyAvgSpeedListHasNotPosix1_returns() {

        List<HourlyAverage> hal = getHourlyAverageList(26);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]");

        hal.get(25).setTimeTag(zdt1);
        hal.get(25).setSpeed(5.0);

        hal.get(24).setTimeTag(ZonedDateTime.parse("2017-01-01T02:00:00z[UTC]"));
        hal.get(24).setSpeed(4.0);

        hal.get(22).setTimeTag(ZonedDateTime.parse("2016-12-31T00:00:00z[UTC]"));
        hal.get(22).setSpeed(3.00);

        hal.get(2).setTimeTag(ZonedDateTime.parse("2016-12-31T07:00:00z[UTC]"));
        hal.get(2).setSpeed(2.0);

        hal.get(0).setTimeTag(ZonedDateTime.parse("2016-12-31T05:00:00z[UTC]"));
        hal.get(0).setSpeed(1.0);

        List<VIndex> vil = getVIndexList(24);

        when(vIndexFactory.create()).thenReturn(new VIndex());
        when(doubleListFactory.create()).thenReturn(new ArrayList<>());

        VIndex vi = vIndexCalculator.calculate(hal, vil);

        assertNotNull(vi);
        assertNotNull(vi.getTimeTag());
        assertEquals(zdt1, vi.getTimeTag());
        assertNotNull(vi.getPreValue());
        assertEquals(4.0, vi.getPreValue(), 0.001);
        assertNotNull(vi.getPostValue());
        assertEquals(0.04, vi.getPostValue(), 0.001);
        assertFalse(vi.getIsCycleBegin());
    }

    @Test
    public void calculate_withGapBetweenHourlyAvgSpeedListHasPosix1_returns() {

        List<HourlyAverage> hal = getHourlyAverageList(26);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]");

        hal.get(25).setTimeTag(zdt1);
        hal.get(25).setSpeed(8.0);

        hal.get(24).setTimeTag(ZonedDateTime.parse("2017-01-01T02:00:00z[UTC]"));
        hal.get(24).setSpeed(7.0);

        hal.get(22).setTimeTag(ZonedDateTime.parse("2016-12-31T00:00:00z[UTC]"));
        hal.get(22).setSpeed(5.00);

        hal.get(2).setTimeTag(ZonedDateTime.parse("2016-12-31T07:00:00z[UTC]"));
        hal.get(2).setSpeed(3.5);

        hal.get(1).setTimeTag(ZonedDateTime.parse("2016-12-31T07:00:00z[UTC]"));
        hal.get(1).setSpeed(2.5);

        hal.get(0).setTimeTag(ZonedDateTime.parse("2016-12-31T05:00:00z[UTC]"));
        hal.get(0).setSpeed(1.7);

        List<VIndex> vil = getVIndexList(24);

        when(vIndexFactory.create()).thenReturn(new VIndex());
        when(doubleListFactory.create()).thenReturn(new ArrayList<>());

        VIndex vi = vIndexCalculator.calculate(hal, vil);

        assertNotNull(vi);
        assertNotNull(vi.getTimeTag());
        assertEquals(zdt1, vi.getTimeTag());
        assertNotNull(vi.getPreValue());
        assertEquals(5.5, vi.getPreValue(), 0.001);
        assertNotNull(vi.getPostValue());
        assertEquals(0.055, vi.getPostValue(), 0.001);
        assertFalse(vi.getIsCycleBegin());
    }

    @Test
    public void calculate_withVIndexHasNotEvent24hsAgo_returns() {

        List<HourlyAverage> hal = getHourlyAverageList(26);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]");

        hal.get(25).setTimeTag(zdt1);
        hal.get(25).setSpeed(5.0);

        hal.get(24).setSpeed(4.0);

        hal.get(23).setSpeed(3.0);

        List<VIndex> vil = getVIndexList(24);

        vil.get(0).setPreValue(99.99);

        when(vIndexFactory.create()).thenReturn(new VIndex());
        when(doubleListFactory.create()).thenReturn(new ArrayList<>());

        VIndex vi = vIndexCalculator.calculate(hal, vil);

        assertNotNull(vi);
        assertNotNull(vi.getTimeTag());
        assertEquals(zdt1, vi.getTimeTag());
        assertNotNull(vi.getPreValue());
        assertEquals(2.0, vi.getPreValue(), 0.001);
        assertNotNull(vi.getPostValue());
        assertEquals(0.02, vi.getPostValue(), 0.001);
        assertFalse(vi.getIsCycleBegin());
    }

    @Test
    public void calculate_withVIndexHasEvent24hsAgoAndLast23hsNotCalmHasSpeedEq101_returns() {

        List<HourlyAverage> hal = getHourlyAverageList(26);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]");

        hal.get(25).setTimeTag(zdt1);
        hal.get(25).setSpeed(5.0);

        hal.get(24).setSpeed(4.0);

        hal.get(23).setSpeed(3.0);

        List<VIndex> vil = getVIndexList(24);

        vil.get(1).setPreValue(101.0);

        vil.get(0).setPreValue(100.0);

        when(vIndexFactory.create()).thenReturn(new VIndex());
        when(doubleListFactory.create()).thenReturn(new ArrayList<>());

        VIndex vi = vIndexCalculator.calculate(hal, vil);

        assertNotNull(vi);
        assertNotNull(vi.getTimeTag());
        assertEquals(zdt1, vi.getTimeTag());
        assertNotNull(vi.getPreValue());
        assertEquals(103.0, vi.getPreValue(), 0.001);
        assertNotNull(vi.getPostValue());
        assertEquals(1.03, vi.getPostValue(), 0.001);
        assertFalse(vi.getIsCycleBegin());
    }

    @Test
    public void calculate_withVIndexHasEvent24hsAgoAndLast23hsNotCalmHasSpeedEq100_returns() {

        List<HourlyAverage> hal = getHourlyAverageList(26);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]");

        hal.get(25).setTimeTag(zdt1);
        hal.get(25).setSpeed(5.0);

        hal.get(24).setSpeed(4.0);

        hal.get(23).setSpeed(3.0);

        List<VIndex> vil = getVIndexList(24);

        vil.get(1).setPreValue(100.0);

        vil.get(0).setPreValue(100.0);

        when(vIndexFactory.create()).thenReturn(new VIndex());
        when(doubleListFactory.create()).thenReturn(new ArrayList<>());

        VIndex vi = vIndexCalculator.calculate(hal, vil);

        assertNotNull(vi);
        assertNotNull(vi.getTimeTag());
        assertEquals(zdt1, vi.getTimeTag());
        assertNotNull(vi.getPreValue());
        assertEquals(102.0, vi.getPreValue(), 0.001);
        assertNotNull(vi.getPostValue());
        assertEquals(1.02, vi.getPostValue(), 0.001);
        assertFalse(vi.getIsCycleBegin());
    }

    @Test
    public void calculate_withVIndexHasEvent24hsAgoAndLast23hsCalm_returnsLastStandardDeviation() {

        List<HourlyAverage> hal = getHourlyAverageList(26);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]");

        hal.get(25).setTimeTag(zdt1);
        hal.get(25).setSpeed(5.0);

        hal.get(24).setSpeed(4.0);

        hal.get(23).setSpeed(3.0);

        List<VIndex> vil = getVIndexList(24);

        vil.get(1).setPreValue(99.99);

        vil.get(0).setPreValue(100.0);

        when(vIndexFactory.create()).thenReturn(new VIndex());
        when(doubleListFactory.create()).thenReturn(new ArrayList<>());

        VIndex vi = vIndexCalculator.calculate(hal, vil);

        assertNotNull(vi);
        assertNotNull(vi.getTimeTag());
        assertEquals(zdt1, vi.getTimeTag());
        assertNotNull(vi.getPreValue());
        assertEquals(1.0, vi.getPreValue(), 0.001);
        assertNotNull(vi.getPostValue());
        assertEquals(0.01, vi.getPostValue(), 0.001);
        assertTrue(vi.getIsCycleBegin());
    }

    @Test
    public void calculate_withSlicePointVMax_returnsSumFromSliceOfStandardDeviationList() {

        List<HourlyAverage> hal = getHourlyAverageList(26);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-02T01:00:00z[UTC]");

        hal.get(25).setTimeTag(zdt1);
        hal.get(25).setSpeed(128.0);

        hal.get(24).setTimeTag(ZonedDateTime.parse("2017-01-02T00:00:00z[UTC]"));
        hal.get(24).setSpeed(64.0);

        hal.get(23).setTimeTag(ZonedDateTime.parse("2017-01-01T23:00:00z[UTC]"));
        hal.get(23).setSpeed(32.0);

        hal.get(22).setTimeTag(ZonedDateTime.parse("2017-01-01T22:00:00z[UTC]"));
        hal.get(22).setSpeed(16.0);

        hal.get(21).setTimeTag(ZonedDateTime.parse("2017-01-01T21:00:00z[UTC]"));
        hal.get(21).setSpeed(8.0);

        hal.get(02).setTimeTag(ZonedDateTime.parse("2017-01-01T02:00:00z[UTC]"));
        hal.get(02).setSpeed(4.0);

        hal.get(01).setTimeTag(ZonedDateTime.parse("2017-01-01T01:00:00z[UTC]"));
        hal.get(01).setSpeed(2.0);

        List<VIndex> vil = getVIndexList(24);

        vil.get(21).setTimeTag(ZonedDateTime.parse("2017-01-01T22:00:00z[UTC]"));
        vil.get(21).setPreValue(100.0);

        when(vIndexFactory.create()).thenReturn(new VIndex());
        when(doubleListFactory.create()).thenReturn(new ArrayList<>());

        VIndex vi = vIndexCalculator.calculate(hal, vil);

        assertNotNull(vi);
        assertNotNull(vi.getTimeTag());
        assertEquals(zdt1, vi.getTimeTag());
        assertNotNull(vi.getPreValue());
        assertEquals(212.0, vi.getPreValue(), 0.001);
        assertNotNull(vi.getPostValue());
        assertEquals(2.12, vi.getPostValue(), 0.001);
        assertFalse(vi.getIsCycleBegin());
    }

    @Test
    public void calculate_withSlicePointNewCycleBegin_returnsSumFromSliceOfStandardDeviationList() {

        List<HourlyAverage> hal = getHourlyAverageList(26);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-02T01:00:00z[UTC]");

        hal.get(25).setTimeTag(zdt1);
        hal.get(25).setSpeed(128.0);

        hal.get(24).setTimeTag(ZonedDateTime.parse("2017-01-02T00:00:00z[UTC]"));
        hal.get(24).setSpeed(64.0);

        hal.get(23).setTimeTag(ZonedDateTime.parse("2017-01-01T23:00:00z[UTC]"));
        hal.get(23).setSpeed(32.0);

        hal.get(22).setTimeTag(ZonedDateTime.parse("2017-01-01T22:00:00z[UTC]"));
        hal.get(22).setSpeed(16.0);

        hal.get(21).setTimeTag(ZonedDateTime.parse("2017-01-01T21:00:00z[UTC]"));
        hal.get(21).setSpeed(8.0);

        hal.get(02).setTimeTag(ZonedDateTime.parse("2017-01-01T02:00:00z[UTC]"));
        hal.get(02).setSpeed(4.0);

        hal.get(01).setTimeTag(ZonedDateTime.parse("2017-01-01T01:00:00z[UTC]"));
        hal.get(01).setSpeed(2.0);

        List<VIndex> vil = getVIndexList(24);

        vil.get(21).setTimeTag(ZonedDateTime.parse("2017-01-01T22:00:00z[UTC]"));
        vil.get(21).setPreValue(99.0);
        vil.get(21).setIsCycleBegin(true);

        when(vIndexFactory.create()).thenReturn(new VIndex());
        when(doubleListFactory.create()).thenReturn(new ArrayList<>());

        VIndex vi = vIndexCalculator.calculate(hal, vil);

        assertNotNull(vi);
        assertNotNull(vi.getTimeTag());
        assertEquals(zdt1, vi.getTimeTag());
        assertNotNull(vi.getPreValue());
        assertEquals(211.0, vi.getPreValue(), 0.001);
        assertNotNull(vi.getPostValue());
        assertEquals(2.11, vi.getPostValue(), 0.001);
        assertFalse(vi.getIsCycleBegin());
    }

    @Theory
    public void calculate_calledWithValidArgument_succeds(@FromDataPoints("vIndexData") VIndexTestData vIndexTestData) {
        List<HourlyAverage> hal = vIndexTestData.getHourlyAverageList();
        List<VIndex> vil = vIndexTestData.getvIndexList();
        Double expectedVIndexPreValue = vIndexTestData.getExpectedVIndexPreValue();
        Double expectedVIndexPostvalue = vIndexTestData.getExpectedVIndexPostvalue();
        boolean expectedVIndexIsCycleBegin = vIndexTestData.isExpectedVIndexIsCycleBegin();
        ZonedDateTime expectedZdt = hal.get(25).getTimeTag();
        when(vIndexFactory.create()).thenReturn(new VIndex());
        when(doubleListFactory.create()).thenReturn(new ArrayList<>());

        VIndex vi = vIndexCalculator.calculate(hal, vil);

        assertNotNull(vi.getTimeTag());
        assertEquals(expectedZdt, vi.getTimeTag());
        assertEqualsVIndexPreValue(expectedVIndexPreValue, vi.getPreValue());
        assertEqualsVIndexPostValue(expectedVIndexPostvalue, vi.getPostValue());
        assertEqualsVIndexIsCycleBegin(expectedVIndexIsCycleBegin, vi.getIsCycleBegin());
    }

    private static int count = 0;

    @Theory
    public void calculate_calledWithValidArgument2_succeds(
            @FromDataPoints("vIndexData2") VIndexTestData vIndexTestData) {
        List<HourlyAverage> hal = vIndexTestData.getHourlyAverageList();
        List<VIndex> vil = vIndexTestData.getvIndexList();
        Double expectedVIndexPreValue = vIndexTestData.getExpectedVIndexPreValue();
        boolean expectedVIndexIsCycleBegin = vIndexTestData.isExpectedVIndexIsCycleBegin();
        ZonedDateTime expectedZdt = hal.get(25).getTimeTag();
        when(vIndexFactory.create()).thenReturn(new VIndex());
        when(doubleListFactory.create()).thenReturn(new ArrayList<>());

        VIndex vi = vIndexCalculator.calculate(hal, vil);

        assertNotNull(vi.getTimeTag());
        assertEquals(expectedZdt, vi.getTimeTag());
        System.out.println(vi.getPreValue());

        count += 1;
        if (vi.getPreValue() != null && Math.abs(expectedVIndexPreValue - vi.getPreValue()) > 0.02) {
            System.out.println(count + ": " + expectedVIndexPreValue + " | " + vi.getPreValue());
        }

        if (count > 48) {
            assertEqualsVIndexPreValue(expectedVIndexPreValue, vi.getPreValue());
            assertEqualsVIndexIsCycleBegin(expectedVIndexIsCycleBegin, vi.getIsCycleBegin());
        }
    }

    @Theory
    public void calculate_calledWithValidArgumentTestPostValueLimits_succeds(
            @FromDataPoints("vIndexPostValueLimits") VIndexTestData vIndexTestData) {
        List<HourlyAverage> hal = vIndexTestData.getHourlyAverageList();
        List<VIndex> vil = vIndexTestData.getvIndexList();
        Double expectedVIndexPreValue = vIndexTestData.getExpectedVIndexPreValue();
        Double expectedVIndexPostvalue = vIndexTestData.getExpectedVIndexPostvalue();

        ZonedDateTime expectedZdt = hal.get(25).getTimeTag();

        when(vIndexFactory.create()).thenReturn(new VIndex());
        when(doubleListFactory.create()).thenReturn(new ArrayList<>());

        VIndex vi = vIndexCalculator.calculate(hal, vil);

        assertNotNull(vi.getTimeTag());
        assertEquals(expectedZdt, vi.getTimeTag());

        assertEqualsVIndexPreValue(expectedVIndexPreValue, vi.getPreValue());
        assertEqualsVIndexPostValue(expectedVIndexPostvalue, vi.getPostValue());
    }

    private void assertEqualsVIndexPreValue(Double expectedVIndexPreValue, Double vIndexPreValue) {
        if (expectedVIndexPreValue == null) {
            assertNull(vIndexPreValue);
        } else {
            assertNotNull(vIndexPreValue);
            assertEquals(expectedVIndexPreValue, vIndexPreValue, 0.11);
        }
    }

    private void assertEqualsVIndexPostValue(Double expectedVIndexPostValue, Double vIndexPostValue) {
        if (expectedVIndexPostValue == null) {
            assertNull(vIndexPostValue);
        } else {
            assertNotNull(vIndexPostValue);
            assertEquals(expectedVIndexPostValue, vIndexPostValue, 0.1);
        }
    }

    private void assertEqualsVIndexIsCycleBegin(boolean expectedIsCycleBegin, boolean actualIsCycleBegin) {
        if (expectedIsCycleBegin) {
            assertTrue(actualIsCycleBegin);
        } else {
            assertFalse(actualIsCycleBegin);
        }
    }

    private List<HourlyAverage> getHourlyAverageList(int size) {
        List<HourlyAverage> hal = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            hal.add(new HourlyAverage());
        }
        return hal;
    }

    private List<VIndex> getVIndexList(int size) {
        List<VIndex> vil = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            vil.add(new VIndex());
        }
        return vil;
    }

}
