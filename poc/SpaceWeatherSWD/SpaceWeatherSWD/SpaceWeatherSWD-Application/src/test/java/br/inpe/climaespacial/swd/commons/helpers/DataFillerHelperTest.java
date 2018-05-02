package br.inpe.climaespacial.swd.commons.helpers;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.commons.exceptions.ArgumentException;
import br.inpe.climaespacial.swd.commons.exceptions.NotUniformListException;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.indexes.v.helpers.DefaultTimeTagable;
import br.inpe.climaespacial.swd.values.factories.ValueEntryFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultDataFiller.class)
public class DataFillerHelperTest {
    
    private static final String NOT_UNIFORM_MESSAGE = "Argument \"listToBeFilled\" is not uniform.";
    
    private static final String INITIAL_DATE_NULL_MESSAGE = "Argument \"initialDate\" cannot be null.";
    
    private static final String FINAL_DATE_NULL_MESSAGE = "Argument \"finalDate\" cannot be null.";
    
    private static final String LIST_TO_BE_FILLED_NULL_MESSAGE = "Argument \"listToBeFilled\" cannot be null.";
    
    private static final String LIST_TO_BE_FILLED_MOST_ITEMS_MESSAGE = "Argument \"listToBeFilled\" must have at most 2 items.";
    
    private static final String INITIAL_DATE_AFTER_FINAL_DATE_MESSAGE = "Argument \"initialDate\" cannot be after than \"finalDate\".";

    private ZonedDateTime initialDateTime = ZonedDateTime.parse("2017-01-01T01:00:00z[UTC]");
    private ZonedDateTime finalDateTime = ZonedDateTime.parse("2017-01-01T02:00:00z[UTC]");

    private ZonedDateTime initialMinuteDateTime = ZonedDateTime.parse("2017-01-01T01:01:00z[UTC]");
    private ZonedDateTime finalMinuteDateTime = ZonedDateTime.parse("2017-01-01T01:02:00z[UTC]");

    @Mock
    @Produces
    private ListFactory<TimeTagable> timeTagableListFactory;

    @Mock
    @Produces
    private ValueEntryFactory valueEntryFactory;

    @Inject
    private DataFillerHelper<TimeTagable> dataGapFillerHelper;

    @Test
    public void fillByHours_calledWithInitialDateNull_throws() {
        ArgumentException ae = null;

        try {
            dataGapFillerHelper.fillByHours(null, null, null);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(INITIAL_DATE_NULL_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByHours_calledWithFinalDateNull_throws() {
        ArgumentException ae = null;

        try {
            dataGapFillerHelper.fillByHours(initialDateTime, null, null);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(FINAL_DATE_NULL_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByHours_calledWithListNull_throws() {
        ArgumentException ae = null;

        try {
            dataGapFillerHelper.fillByHours(initialDateTime, finalDateTime, null);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(LIST_TO_BE_FILLED_NULL_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByHours_calledWithListSizeHigherThanTargetSize_throws() {
        ArgumentException ae = null;
        List<TimeTagable> ttl = mockList(TimeTagable.class);
        when(ttl.size()).thenReturn(3);

        try {
            dataGapFillerHelper.fillByHours(initialDateTime, finalDateTime, ttl);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(LIST_TO_BE_FILLED_MOST_ITEMS_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByHours_calledHavingFinalDateBeforeInitialDate_throws() {
        ArgumentException ae = null;
        List<TimeTagable> ttl = mockList(TimeTagable.class);
        when(ttl.isEmpty()).thenReturn(false);

        try {
            dataGapFillerHelper.fillByHours(finalDateTime, initialDateTime, ttl);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(INITIAL_DATE_AFTER_FINAL_DATE_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByHours_calledWithListBlank_fills() {
        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByHours(initialDateTime, finalDateTime, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertNotSame(ttl1, ttl2);
        assertThat(ttl2, hasSize(0));
    }

    @Test
    public void fillByHours_calledWithListHavingItemAtTheBeginning_fillsAtTheEnd() {
        DefaultTimeTagable df = new DefaultTimeTagable();
        df.setTimeTag(initialDateTime);

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(df);

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByHours(initialDateTime, finalDateTime, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(2));
        TimeTagable tt1 = ttl2.get(0);
        TimeTagable tt2 = ttl2.get(1);
        assertNotSame(initialDateTime, tt1.getTimeTag());
        assertNotSame(finalDateTime, tt2.getTimeTag());
        assertEquals(initialDateTime, tt1.getTimeTag());
        assertEquals(finalDateTime, tt2.getTimeTag());
    }

    @Test
    public void fillByHours_calledWithListHavingItemAtTheEnd_fillsAtTheBeginning() {
        DefaultTimeTagable df = new DefaultTimeTagable();
        df.setTimeTag(finalDateTime);

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(df);

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByHours(initialDateTime, finalDateTime, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(2));
        TimeTagable tt1 = ttl2.get(0);
        TimeTagable tt2 = ttl2.get(1);
        assertNotSame(initialDateTime, tt1.getTimeTag());
        assertNotSame(finalDateTime, tt2.getTimeTag());
        assertEquals(initialDateTime, tt1.getTimeTag());
        assertEquals(finalDateTime, tt2.getTimeTag());
    }

    @Test
    public void fillByHours_calledWithListHavingItemAtTheBeginningAndEnd_fillsInTheMiddle() {
        DefaultTimeTagable dtt1 = new DefaultTimeTagable();
        dtt1.setTimeTag(initialDateTime);

        ZonedDateTime nfdt = finalDateTime.plusHours(1);
        DefaultTimeTagable dtt2 = new DefaultTimeTagable();
        dtt2.setTimeTag(nfdt);

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(dtt1);
        ttl1.add(dtt2);

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByHours(initialDateTime, nfdt, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(3));
        TimeTagable tt1 = ttl2.get(0);
        TimeTagable tt2 = ttl2.get(1);
        TimeTagable tt3 = ttl2.get(2);
        assertNotSame(initialDateTime, tt1.getTimeTag());
        assertNotSame(finalDateTime, tt2.getTimeTag());
        assertNotSame(nfdt, tt3.getTimeTag());
        assertEquals(initialDateTime, tt1.getTimeTag());
        assertEquals(finalDateTime, tt2.getTimeTag());
        assertEquals(nfdt, tt3.getTimeTag());
    }

    @Test
    public void fillByHours_calledWithListHavingItemInTheMiddle_fillsBeginningAndEnd() {
        DefaultTimeTagable dtt1 = new DefaultTimeTagable();
        dtt1.setTimeTag(finalDateTime);

        ZonedDateTime nfdt = finalDateTime.plusHours(1);

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(dtt1);

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByHours(initialDateTime, nfdt, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(3));
        TimeTagable tt1 = ttl2.get(0);
        TimeTagable tt2 = ttl2.get(1);
        TimeTagable tt3 = ttl2.get(2);
        assertNotSame(initialDateTime, tt1.getTimeTag());
        assertNotSame(finalDateTime, tt2.getTimeTag());
        assertNotSame(nfdt, tt3.getTimeTag());
        assertEquals(initialDateTime, tt1.getTimeTag());
        assertEquals(finalDateTime, tt2.getTimeTag());
        assertEquals(nfdt, tt3.getTimeTag());
    }

    @Test
    public void fillByHours_calledWithListHavingItemInTheMiddle_fillsAllGaps() {

        DefaultTimeTagable dtt1 = new DefaultTimeTagable();
        dtt1.setTimeTag(ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]"));

        DefaultTimeTagable dtt2 = new DefaultTimeTagable();
        dtt2.setTimeTag(ZonedDateTime.parse("2017-01-01T06:00:00z[UTC]"));

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(dtt1);
        ttl1.add(dtt2);

        ZonedDateTime idt = ZonedDateTime.parse("2017-01-01T01:00:00z[UTC]");
        ZonedDateTime fdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByHours(idt, fdt, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(8));
        int i = 0;

        for (ZonedDateTime zdt = idt; !zdt.isAfter(fdt); zdt = zdt.plusHours(1)) {
            assertEquals(zdt, ttl2.get(i++).getTimeTag());
        }
    }

    @Test
    public void fillByMinutes_calledWithInitialDateNull_throws() {
        ArgumentException ae = null;

        try {
            dataGapFillerHelper.fillByMinutes(null, null, null);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(INITIAL_DATE_NULL_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByMinutes_calledWithFinalDateNull_throws() {
        ArgumentException ae = null;

        try {
            dataGapFillerHelper.fillByMinutes(initialMinuteDateTime, null, null);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(FINAL_DATE_NULL_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByMinutes_calledWithListNull_throws() {
        ArgumentException ae = null;

        try {
            dataGapFillerHelper.fillByMinutes(initialMinuteDateTime, finalMinuteDateTime, null);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(LIST_TO_BE_FILLED_NULL_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByMinutes_calledWithListSizeHigherThanTargetSize_throws() {
        ArgumentException ae = null;
        List<TimeTagable> ttl = mockList(TimeTagable.class);
        when(ttl.size()).thenReturn(65);

        try {
            dataGapFillerHelper.fillByMinutes(initialMinuteDateTime, finalMinuteDateTime, ttl);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(LIST_TO_BE_FILLED_MOST_ITEMS_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByMinutes_calledHavingFinalDateBeforeInitialDate_throws() {
        ArgumentException ae = null;
        List<TimeTagable> ttl = mockList(TimeTagable.class);
        when(ttl.isEmpty()).thenReturn(false);

        try {
            dataGapFillerHelper.fillByMinutes(finalMinuteDateTime, initialMinuteDateTime, ttl);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(INITIAL_DATE_AFTER_FINAL_DATE_MESSAGE, ae.getMessage());

    }

    @Test
    public void fillByMinutes_calledWithListBlank_fills() {
        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByMinutes(initialMinuteDateTime, finalMinuteDateTime, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertNotSame(ttl1, ttl2);
        assertThat(ttl2, hasSize(0));

    }

    @Test
    public void fillByMinutes_calledWithListHavingItemAtTheBeginning_fillsAtTheEnd() {
        DefaultTimeTagable df = new DefaultTimeTagable();
        df.setTimeTag(initialMinuteDateTime);

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(df);

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByMinutes(initialMinuteDateTime, finalMinuteDateTime, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(2));
        TimeTagable tt1 = ttl2.get(0);
        TimeTagable tt2 = ttl2.get(1);
        assertNotSame(initialMinuteDateTime, tt1.getTimeTag());
        assertNotSame(finalMinuteDateTime, tt2.getTimeTag());
        assertEquals(initialMinuteDateTime, tt1.getTimeTag());
        assertEquals(finalMinuteDateTime, tt2.getTimeTag());
    }

    @Test
    public void fillByMinutes_calledWithListHavingItemAtTheEnd_fillsAtTheBeginning() {
        DefaultTimeTagable df = new DefaultTimeTagable();
        df.setTimeTag(finalMinuteDateTime);

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(df);

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByMinutes(initialMinuteDateTime, finalMinuteDateTime, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(2));
        TimeTagable tt1 = ttl2.get(0);
        TimeTagable tt2 = ttl2.get(1);
        assertNotSame(initialMinuteDateTime, tt1.getTimeTag());
        assertNotSame(finalMinuteDateTime, tt2.getTimeTag());
        assertEquals(initialMinuteDateTime, tt1.getTimeTag());
        assertEquals(finalMinuteDateTime, tt2.getTimeTag());
    }

    @Test
    public void fillByMinutes_calledWithListHavingItemAtTheBeginningAndEnd_fillsInTheMiddle() {
        DefaultTimeTagable dtt1 = new DefaultTimeTagable();
        dtt1.setTimeTag(initialMinuteDateTime);

        ZonedDateTime nfdt = finalMinuteDateTime.plusMinutes(1);
        DefaultTimeTagable dtt2 = new DefaultTimeTagable();
        dtt2.setTimeTag(nfdt);

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(dtt1);
        ttl1.add(dtt2);

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByMinutes(initialMinuteDateTime, nfdt, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(3));
        TimeTagable tt1 = ttl2.get(0);
        TimeTagable tt2 = ttl2.get(1);
        TimeTagable tt3 = ttl2.get(2);
        assertNotSame(initialMinuteDateTime, tt1.getTimeTag());
        assertNotSame(finalMinuteDateTime, tt2.getTimeTag());
        assertNotSame(nfdt, tt3.getTimeTag());
        assertEquals(initialMinuteDateTime, tt1.getTimeTag());
        assertEquals(finalMinuteDateTime, tt2.getTimeTag());
        assertEquals(nfdt, tt3.getTimeTag());
    }

    @Test
    public void fillByMinutes_calledWithListHavingItemInTheMiddle_fillsBeginningAndEnd() {
        DefaultTimeTagable dtt1 = new DefaultTimeTagable();
        dtt1.setTimeTag(finalMinuteDateTime);

        ZonedDateTime nfdt = finalMinuteDateTime.plusMinutes(1);

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(dtt1);

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByMinutes(initialMinuteDateTime, nfdt, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(3));
        TimeTagable tt1 = ttl2.get(0);
        TimeTagable tt2 = ttl2.get(1);
        TimeTagable tt3 = ttl2.get(2);
        assertNotSame(initialMinuteDateTime, tt1.getTimeTag());
        assertNotSame(finalMinuteDateTime, tt2.getTimeTag());
        assertNotSame(nfdt, tt3.getTimeTag());
        assertEquals(initialMinuteDateTime, tt1.getTimeTag());
        assertEquals(finalMinuteDateTime, tt2.getTimeTag());
        assertEquals(nfdt, tt3.getTimeTag());
    }

    @Test
    public void fillByMinutes_calledWithListHavingItemInTheMiddle_fillsAllGaps() {
        DefaultTimeTagable dtt1 = new DefaultTimeTagable();
        dtt1.setTimeTag(ZonedDateTime.parse("2017-01-01T01:03:00z[UTC]"));

        DefaultTimeTagable dtt2 = new DefaultTimeTagable();
        dtt2.setTimeTag(ZonedDateTime.parse("2017-01-01T01:06:00z[UTC]"));

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(dtt1);
        ttl1.add(dtt2);

        ZonedDateTime idt = ZonedDateTime.parse("2017-01-01T01:01:00z[UTC]");
        ZonedDateTime fdt = ZonedDateTime.parse("2017-01-01T01:08:00z[UTC]");

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByMinutes(idt, fdt, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(8));
        int i = 0;

        for (ZonedDateTime zdt = idt; !zdt.isAfter(fdt); zdt = zdt.plusMinutes(1)) {
            assertEquals(zdt, ttl2.get(i++).getTimeTag());
        }
    }

    @Test
    public void fillByMinutes_calledWithListHavingItemInTheMiddle_fillsAllGapsNotUniformlyAndThrows() {
        DefaultTimeTagable dtt1 = new DefaultTimeTagable();
        dtt1.setTimeTag(ZonedDateTime.parse("2017-01-01T01:03:30z[UTC]"));

        DefaultTimeTagable dtt2 = new DefaultTimeTagable();
        dtt2.setTimeTag(ZonedDateTime.parse("2017-01-01T01:06:00z[UTC]"));

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(dtt1);
        ttl1.add(dtt2);

        ZonedDateTime idt = ZonedDateTime.parse("2017-01-01T01:01:00z[UTC]");
        ZonedDateTime fdt = ZonedDateTime.parse("2017-01-01T01:08:00z[UTC]");

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());
        NotUniformListException nule = null;
        try {
            dataGapFillerHelper.fillByMinutes(idt, fdt, ttl1);
        } catch (NotUniformListException e) {
            nule = e;
        }

        verify(timeTagableListFactory).create();
        assertNotNull(nule);
        assertEquals(NotUniformListException.class, nule.getClass());
        assertEquals(NOT_UNIFORM_MESSAGE, nule.getMessage());
    }

    @Test
    public void fillByHours_calledWithListHavingItemInTheMiddle_fillsAllGapsNotUniformlyAndThrows() {
        DefaultTimeTagable dtt1 = new DefaultTimeTagable();
        dtt1.setTimeTag(ZonedDateTime.parse("2017-01-01T01:30:00z[UTC]"));

        DefaultTimeTagable dtt2 = new DefaultTimeTagable();
        dtt2.setTimeTag(ZonedDateTime.parse("2017-01-01T04:00:00z[UTC]"));

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(dtt1);
        ttl1.add(dtt2);

        ZonedDateTime idt = ZonedDateTime.parse("2017-01-01T01:00:00z[UTC]");
        ZonedDateTime fdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());
        NotUniformListException nule = null;
        try {
            dataGapFillerHelper.fillByHours(idt, fdt, ttl1);
        } catch (NotUniformListException e) {
            nule = e;
        }

        verify(timeTagableListFactory).create();
        assertNotNull(nule);
        assertEquals(NotUniformListException.class, nule.getClass());
        assertEquals(NOT_UNIFORM_MESSAGE, nule.getMessage());
    }

    @Test
    public void fillByHoursAnyway_calledWithInitialDateNull_throws() {
        ArgumentException ae = null;

        try {
            dataGapFillerHelper.fillByHoursAnyway(null, null, null);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(INITIAL_DATE_NULL_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByHoursAnyway_calledWithFinalDateNull_throws() {
        ArgumentException ae = null;

        try {
            dataGapFillerHelper.fillByHoursAnyway(initialDateTime, null, null);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(FINAL_DATE_NULL_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByHoursAnyway_calledWithListNull_throws() {
        ArgumentException ae = null;

        try {
            dataGapFillerHelper.fillByHoursAnyway(initialDateTime, finalDateTime, null);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(LIST_TO_BE_FILLED_NULL_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByHoursAnyway_calledWithListSizeHigherThanTargetSize_throws() {
        ArgumentException ae = null;
        List<TimeTagable> ttl = mockList(TimeTagable.class);
        when(ttl.size()).thenReturn(3);

        try {
            dataGapFillerHelper.fillByHoursAnyway(initialDateTime, finalDateTime, ttl);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(LIST_TO_BE_FILLED_MOST_ITEMS_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByHoursAnyway_calledHavingFinalDateBeforeInitialDate_throws() {
        ArgumentException ae = null;

        try {
            dataGapFillerHelper.fillByHoursAnyway(finalDateTime, initialDateTime, new ArrayList<>());
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(INITIAL_DATE_AFTER_FINAL_DATE_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByHoursAnyway_calledWithListHavingItemAtTheBeginning_fillsAtTheEnd() {
        DefaultTimeTagable df = new DefaultTimeTagable();
        df.setTimeTag(initialDateTime);

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(df);

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByHoursAnyway(initialDateTime, finalDateTime, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(2));
        TimeTagable tt1 = ttl2.get(0);
        TimeTagable tt2 = ttl2.get(1);
        assertNotSame(initialDateTime, tt1.getTimeTag());
        assertNotSame(finalDateTime, tt2.getTimeTag());
        assertEquals(initialDateTime, tt1.getTimeTag());
        assertEquals(finalDateTime, tt2.getTimeTag());
    }

    @Test
    public void fillByHoursAnyway_calledWithListHavingItemAtTheEnd_fillsAtTheBeginning() {
        DefaultTimeTagable df = new DefaultTimeTagable();
        df.setTimeTag(finalDateTime);

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(df);

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByHoursAnyway(initialDateTime, finalDateTime, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(2));
        TimeTagable tt1 = ttl2.get(0);
        TimeTagable tt2 = ttl2.get(1);
        assertNotSame(initialDateTime, tt1.getTimeTag());
        assertNotSame(finalDateTime, tt2.getTimeTag());
        assertEquals(initialDateTime, tt1.getTimeTag());
        assertEquals(finalDateTime, tt2.getTimeTag());
    }

    @Test
    public void fillByHoursAnyway_calledWithListHavingItemAtTheBeginningAndEnd_fillsInTheMiddle() {
        DefaultTimeTagable dtt1 = new DefaultTimeTagable();
        dtt1.setTimeTag(initialDateTime);

        ZonedDateTime nfdt = finalDateTime.plusHours(1);
        DefaultTimeTagable dtt2 = new DefaultTimeTagable();
        dtt2.setTimeTag(nfdt);

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(dtt1);
        ttl1.add(dtt2);

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByHoursAnyway(initialDateTime, nfdt, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(3));
        TimeTagable tt1 = ttl2.get(0);
        TimeTagable tt2 = ttl2.get(1);
        TimeTagable tt3 = ttl2.get(2);
        assertNotSame(initialDateTime, tt1.getTimeTag());
        assertNotSame(finalDateTime, tt2.getTimeTag());
        assertNotSame(nfdt, tt3.getTimeTag());
        assertEquals(initialDateTime, tt1.getTimeTag());
        assertEquals(finalDateTime, tt2.getTimeTag());
        assertEquals(nfdt, tt3.getTimeTag());
    }

    @Test
    public void fillByHoursAnyway_calledWithListHavingItemInTheMiddle_fillsBeginningAndEnd() {
        DefaultTimeTagable dtt1 = new DefaultTimeTagable();
        dtt1.setTimeTag(finalDateTime);

        ZonedDateTime nfdt = finalDateTime.plusHours(1);

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(dtt1);

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByHoursAnyway(initialDateTime, nfdt, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(3));
        TimeTagable tt1 = ttl2.get(0);
        TimeTagable tt2 = ttl2.get(1);
        TimeTagable tt3 = ttl2.get(2);
        assertNotSame(initialDateTime, tt1.getTimeTag());
        assertNotSame(finalDateTime, tt2.getTimeTag());
        assertNotSame(nfdt, tt3.getTimeTag());
        assertEquals(initialDateTime, tt1.getTimeTag());
        assertEquals(finalDateTime, tt2.getTimeTag());
        assertEquals(nfdt, tt3.getTimeTag());
    }

    @Test
    public void fillByHoursAnyway_calledWithListHavingItemInTheMiddle_fillsAllGaps() {

        DefaultTimeTagable dtt1 = new DefaultTimeTagable();
        dtt1.setTimeTag(ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]"));

        DefaultTimeTagable dtt2 = new DefaultTimeTagable();
        dtt2.setTimeTag(ZonedDateTime.parse("2017-01-01T06:00:00z[UTC]"));

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(dtt1);
        ttl1.add(dtt2);

        ZonedDateTime idt = ZonedDateTime.parse("2017-01-01T01:00:00z[UTC]");
        ZonedDateTime fdt = ZonedDateTime.parse("2017-01-01T08:00:00z[UTC]");

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByHoursAnyway(idt, fdt, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(8));
        int i = 0;

        for (ZonedDateTime zdt = idt; !zdt.isAfter(fdt); zdt = zdt.plusHours(1)) {
            assertEquals(zdt, ttl2.get(i++).getTimeTag());
        }
    }

    @Test
    public void fillByMinutesAnyway_calledWithInitialDateNull_throws() {
        ArgumentException ae = null;

        try {
            dataGapFillerHelper.fillByMinutesAnyway(null, null, null);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(INITIAL_DATE_NULL_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByMinutesAnyway_calledWithFinalDateNull_throws() {
        ArgumentException ae = null;

        try {
            dataGapFillerHelper.fillByMinutesAnyway(initialMinuteDateTime, null, null);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(FINAL_DATE_NULL_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByMinutesAnyway_calledWithListNull_throws() {
        ArgumentException ae = null;

        try {
            dataGapFillerHelper.fillByMinutesAnyway(initialMinuteDateTime, finalMinuteDateTime, null);
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(LIST_TO_BE_FILLED_NULL_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByMinutesAnyway_calledWithListSizeHigherThanTargetSize_throws() {
        ArgumentException re = null;
        List<TimeTagable> ttl = mockList(TimeTagable.class);
        when(ttl.size()).thenReturn(65);

        try {
            dataGapFillerHelper.fillByMinutesAnyway(initialMinuteDateTime, finalMinuteDateTime, ttl);
        } catch (ArgumentException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals(ArgumentException.class, re.getClass());
        assertEquals(LIST_TO_BE_FILLED_MOST_ITEMS_MESSAGE, re.getMessage());
    }

    @Test
    public void fillByMinutesAnyway_calledHavingFinalDateBeforeInitialDate_throws() {
        ArgumentException ae = null;

        try {
            dataGapFillerHelper.fillByMinutesAnyway(finalMinuteDateTime, initialMinuteDateTime, new ArrayList<>());
        } catch (ArgumentException e) {
            ae = e;
        }

        assertNotNull(ae);
        assertEquals(ArgumentException.class, ae.getClass());
        assertEquals(INITIAL_DATE_AFTER_FINAL_DATE_MESSAGE, ae.getMessage());
    }

    @Test
    public void fillByMinutesAnyway_calledWithListHavingItemAtTheBeginning_fillsAtTheEnd() {
        DefaultTimeTagable df = new DefaultTimeTagable();
        df.setTimeTag(initialMinuteDateTime);

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(df);

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByMinutesAnyway(initialMinuteDateTime, finalMinuteDateTime,
                ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(2));
        TimeTagable tt1 = ttl2.get(0);
        TimeTagable tt2 = ttl2.get(1);
        assertNotSame(initialMinuteDateTime, tt1.getTimeTag());
        assertNotSame(finalMinuteDateTime, tt2.getTimeTag());
        assertEquals(initialMinuteDateTime, tt1.getTimeTag());
        assertEquals(finalMinuteDateTime, tt2.getTimeTag());
    }

    @Test
    public void fillByMinutesAnyway_calledWithListHavingItemAtTheEnd_fillsAtTheBeginning() {
        DefaultTimeTagable df = new DefaultTimeTagable();
        df.setTimeTag(finalMinuteDateTime);

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(df);

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByMinutesAnyway(initialMinuteDateTime, finalMinuteDateTime,
                ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(2));
        TimeTagable tt1 = ttl2.get(0);
        TimeTagable tt2 = ttl2.get(1);
        assertNotSame(initialMinuteDateTime, tt1.getTimeTag());
        assertNotSame(finalMinuteDateTime, tt2.getTimeTag());
        assertEquals(initialMinuteDateTime, tt1.getTimeTag());
        assertEquals(finalMinuteDateTime, tt2.getTimeTag());
    }

    @Test
    public void fillByMinutesAnyway_calledWithListHavingItemAtTheBeginningAndEnd_fillsInTheMiddle() {
        DefaultTimeTagable dtt1 = new DefaultTimeTagable();
        dtt1.setTimeTag(initialMinuteDateTime);

        ZonedDateTime nfdt = finalMinuteDateTime.plusMinutes(1);
        DefaultTimeTagable dtt2 = new DefaultTimeTagable();
        dtt2.setTimeTag(nfdt);

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(dtt1);
        ttl1.add(dtt2);

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByMinutesAnyway(initialMinuteDateTime, nfdt, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(3));
        TimeTagable tt1 = ttl2.get(0);
        TimeTagable tt2 = ttl2.get(1);
        TimeTagable tt3 = ttl2.get(2);
        assertNotSame(initialMinuteDateTime, tt1.getTimeTag());
        assertNotSame(finalMinuteDateTime, tt2.getTimeTag());
        assertNotSame(nfdt, tt3.getTimeTag());
        assertEquals(initialMinuteDateTime, tt1.getTimeTag());
        assertEquals(finalMinuteDateTime, tt2.getTimeTag());
        assertEquals(nfdt, tt3.getTimeTag());
    }

    @Test
    public void fillByMinutesAnyway_calledWithListHavingItemInTheMiddle_fillsBeginningAndEnd() {
        DefaultTimeTagable dtt1 = new DefaultTimeTagable();
        dtt1.setTimeTag(finalMinuteDateTime);

        ZonedDateTime nfdt = finalMinuteDateTime.plusMinutes(1);

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(dtt1);

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByMinutesAnyway(initialMinuteDateTime, nfdt, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(3));
        TimeTagable tt1 = ttl2.get(0);
        TimeTagable tt2 = ttl2.get(1);
        TimeTagable tt3 = ttl2.get(2);
        assertNotSame(initialMinuteDateTime, tt1.getTimeTag());
        assertNotSame(finalMinuteDateTime, tt2.getTimeTag());
        assertNotSame(nfdt, tt3.getTimeTag());
        assertEquals(initialMinuteDateTime, tt1.getTimeTag());
        assertEquals(finalMinuteDateTime, tt2.getTimeTag());
        assertEquals(nfdt, tt3.getTimeTag());
    }

    @Test
    public void fillByMinutesAnyway_calledWithListHavingItemInTheMiddle_fillsAllGaps() {
        DefaultTimeTagable dtt1 = new DefaultTimeTagable();
        dtt1.setTimeTag(ZonedDateTime.parse("2017-01-01T01:03:00z[UTC]"));

        DefaultTimeTagable dtt2 = new DefaultTimeTagable();
        dtt2.setTimeTag(ZonedDateTime.parse("2017-01-01T01:06:00z[UTC]"));

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(dtt1);
        ttl1.add(dtt2);

        ZonedDateTime idt = ZonedDateTime.parse("2017-01-01T01:01:00z[UTC]");
        ZonedDateTime fdt = ZonedDateTime.parse("2017-01-01T01:08:00z[UTC]");

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());

        List<TimeTagable> ttl2 = dataGapFillerHelper.fillByMinutesAnyway(idt, fdt, ttl1);

        verify(timeTagableListFactory).create();
        assertNotNull(ttl2);
        assertThat(ttl2, hasSize(8));
        int i = 0;

        for (ZonedDateTime zdt = idt; !zdt.isAfter(fdt); zdt = zdt.plusMinutes(1)) {
            assertEquals(zdt, ttl2.get(i++).getTimeTag());
        }
    }

    @Test
    public void fillByMinutesAnyway_calledWithListHavingItemInTheMiddle_fillsAllGapsNotUniformlyAndThrows() {
        DefaultTimeTagable dtt1 = new DefaultTimeTagable();
        dtt1.setTimeTag(ZonedDateTime.parse("2017-01-01T01:03:30z[UTC]"));

        DefaultTimeTagable dtt2 = new DefaultTimeTagable();
        dtt2.setTimeTag(ZonedDateTime.parse("2017-01-01T01:06:00z[UTC]"));

        List<TimeTagable> ttl1 = new ArrayList<TimeTagable>();
        ttl1.add(dtt1);
        ttl1.add(dtt2);

        ZonedDateTime idt = ZonedDateTime.parse("2017-01-01T01:01:00z[UTC]");
        ZonedDateTime fdt = ZonedDateTime.parse("2017-01-01T01:08:00z[UTC]");

        when(timeTagableListFactory.create()).thenReturn(new ArrayList<>());
        NotUniformListException nule = null;
        try {
            dataGapFillerHelper.fillByMinutesAnyway(idt, fdt, ttl1);
        } catch (NotUniformListException e) {
            nule = e;
        }

        verify(timeTagableListFactory).create();
        assertNotNull(nule);
        assertEquals(NotUniformListException.class, nule.getClass());
        assertEquals(NOT_UNIFORM_MESSAGE, nule.getMessage());
    }
    
}
