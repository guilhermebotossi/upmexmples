package br.inpe.climaespacial.swd.values.mappers;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.values.bt.average.dtos.AverageBT;
import br.inpe.climaespacial.swd.values.bt.value.dtos.BT;
import br.inpe.climaespacial.swd.values.bx.average.dtos.AverageBX;
import br.inpe.climaespacial.swd.values.bx.value.dtos.BX;
import br.inpe.climaespacial.swd.values.by.average.dtos.AverageBY;
import br.inpe.climaespacial.swd.values.by.value.dtos.BY;
import br.inpe.climaespacial.swd.values.bz.average.dtos.AverageBZ;
import br.inpe.climaespacial.swd.values.bz.value.dtos.BZ;
import br.inpe.climaespacial.swd.values.density.average.dtos.AverageDensity;
import br.inpe.climaespacial.swd.values.density.value.dtos.Density;
import br.inpe.climaespacial.swd.values.dpr.average.dtos.AverageDPR;
import br.inpe.climaespacial.swd.values.dpr.value.dtos.DPR;
import br.inpe.climaespacial.swd.values.dtos.ValueEntry;
import br.inpe.climaespacial.swd.values.ey.average.dtos.AverageEY;
import br.inpe.climaespacial.swd.values.ey.value.dtos.EY;
import br.inpe.climaespacial.swd.values.factories.ValueEntryFactory;
import br.inpe.climaespacial.swd.values.rmp.average.dtos.AverageRMP;
import br.inpe.climaespacial.swd.values.rmp.value.dtos.RMP;
import br.inpe.climaespacial.swd.values.speed.average.dtos.AverageSpeed;
import br.inpe.climaespacial.swd.values.speed.value.dtos.Speed;
import br.inpe.climaespacial.swd.values.temperature.average.dtos.AverageTemperature;
import br.inpe.climaespacial.swd.values.temperature.value.dtos.Temperature;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultValuesEntryMapper.class)
public class ValuesEntryMapperTest {

    @Mock
    @Produces
    private ListFactory<ValueEntry> valueEntryListFactory;

    @Mock
    @Produces
    private ValueEntryFactory valueEntryFactory;

    @Inject
    private ValuesEntryMapper valuesEntryMapper;

    @Test
    public void mapBT_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapBT(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"BT\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapBT_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        BT bt = new BT();
        bt.setTimeTag(timeTag);
        bt.setValue(value);

        List<BT> btl = Arrays.asList(bt);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapBT(btl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertSame(timeTag, ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapBY_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapBY(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"BY\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapBY_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        BY by = new BY();
        by.setTimeTag(timeTag);
        by.setValue(value);

        List<BY> byl = Arrays.asList(by);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapBY(byl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertSame(timeTag, ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapBX_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapBX(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"BX\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapBX_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        BX bx = new BX();
        bx.setTimeTag(timeTag);
        bx.setValue(value);

        List<BX> bxl = Arrays.asList(bx);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapBX(bxl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertSame(timeTag, ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapBZ_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapBZ(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"BZ\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapBZ_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        BZ bz = new BZ();
        bz.setTimeTag(timeTag);
        bz.setValue(value);

        List<BZ> bzl = Arrays.asList(bz);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapBZ(bzl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertSame(timeTag, ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapDensity_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapDensity(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"DENSITY\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapDensity_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        Density density = new Density();
        density.setTimeTag(timeTag);
        density.setValue(value);

        List<Density> dl = Arrays.asList(density);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapDensity(dl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertSame(timeTag, ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapSpeed_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapSpeed(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"SPEED\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapSpeed_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        Speed s = new Speed();
        s.setTimeTag(timeTag);
        s.setValue(value);

        List<Speed> sl = Arrays.asList(s);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapSpeed(sl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertSame(timeTag, ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapTemperature_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapTemperature(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"TEMPERATURE\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapTemperature_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        Temperature t = new Temperature();
        t.setTimeTag(timeTag);
        t.setValue(value);

        List<Temperature> temperatureList = Arrays.asList(t);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapTemperature(temperatureList);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertSame(timeTag, ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapEY_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapEY(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"EY\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapEY_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        EY ey = new EY();
        ey.setTimeTag(timeTag);
        ey.setValue(value);

        List<EY> eyl = Arrays.asList(ey);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapEY(eyl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertSame(timeTag, ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapDPR_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapDPR(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"DPR\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapDPR_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        DPR dpr = new DPR();
        dpr.setTimeTag(timeTag);
        dpr.setValue(value);

        List<DPR> dprl = Arrays.asList(dpr);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapDPR(dprl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertSame(timeTag, ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapRMP_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapRMP(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"RMP\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapRMP_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        RMP rmp = new RMP();
        rmp.setTimeTag(timeTag);
        rmp.setValue(value);

        List<RMP> rmpl = Arrays.asList(rmp);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapRMP(rmpl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertSame(timeTag, ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapAverageBT_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapAverageBT(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"AVERAGE_BT\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapAverageBT_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        AverageBT abt = new AverageBT();
        abt.setTimeTag(timeTag);
        abt.setValue(value);

        List<AverageBT> abtl = Arrays.asList(abt);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapAverageBT(abtl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertEquals(timeTag.plusMinutes(30), ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapAverageBY_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapAverageBY(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"AVERAGE_BY\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapAverageBY_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        AverageBY aby = new AverageBY();
        aby.setTimeTag(timeTag);
        aby.setValue(value);

        List<AverageBY> abyl = Arrays.asList(aby);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapAverageBY(abyl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertEquals(timeTag.plusMinutes(30), ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapAverageBX_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapAverageBX(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"AVERAGE_BX\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapAverageBX_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        AverageBX abx = new AverageBX();
        abx.setTimeTag(timeTag);
        abx.setValue(value);

        List<AverageBX> abxl = Arrays.asList(abx);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapAverageBX(abxl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertEquals(timeTag.plusMinutes(30), ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapAverageBZ_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapAverageBZ(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"AVERAGE_BZ\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapAverageBZ_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        AverageBZ abz = new AverageBZ();
        abz.setTimeTag(timeTag);
        abz.setValue(value);

        List<AverageBZ> abzl = Arrays.asList(abz);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapAverageBZ(abzl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertEquals(timeTag.plusMinutes(30), ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapAverageDensity_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapAverageDensity(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"AVERAGE_DENSITY\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapAverageDensity_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        AverageDensity ad = new AverageDensity();
        ad.setTimeTag(timeTag);
        ad.setValue(value);

        List<AverageDensity> dl = Arrays.asList(ad);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapAverageDensity(dl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertEquals(timeTag.plusMinutes(30), ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapAverageSpeed_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapAverageSpeed(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"AVERAGE_SPEED\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapAverageSpeed_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        AverageSpeed as = new AverageSpeed();
        as.setTimeTag(timeTag);
        as.setValue(value);

        List<AverageSpeed> asl = Arrays.asList(as);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapAverageSpeed(asl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertEquals(timeTag.plusMinutes(30), ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapAverageTemperature_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapAverageTemperature(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"AVERAGE_TEMPERATURE\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapAverageTemperature_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        AverageTemperature at = new AverageTemperature();
        at.setTimeTag(timeTag);
        at.setValue(value);

        List<AverageTemperature> atl = Arrays.asList(at);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapAverageTemperature(atl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertEquals(timeTag.plusMinutes(30), ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapAverageEY_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapAverageEY(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"AVERAGE_EY\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapAverageEY_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        AverageEY aey = new AverageEY();
        aey.setTimeTag(timeTag);
        aey.setValue(value);

        List<AverageEY> aeyl = Arrays.asList(aey);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapAverageEY(aeyl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertEquals(timeTag.plusMinutes(30), ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapAverageDPR_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapAverageDPR(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"AVERAGE_DPR\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapAverageDPR_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        AverageDPR adpr = new AverageDPR();
        adpr.setTimeTag(timeTag);
        adpr.setValue(value);

        List<AverageDPR> adprl = Arrays.asList(adpr);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapAverageDPR(adprl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertEquals(timeTag.plusMinutes(30), ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

    @Test
    public void mapAverageRMP_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            valuesEntryMapper.mapAverageRMP(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"AVERAGE_RMP\" cannot be null.", re.getMessage());
    }

    @Test
    public void mapAverageRMP_called_succeeds() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        Double value = 2.0;
        AverageRMP armp = new AverageRMP();
        armp.setTimeTag(timeTag);
        armp.setValue(value);

        List<AverageRMP> armpl = Arrays.asList(armp);
        ValueEntry ve1 = new ValueEntry();
        when(valueEntryFactory.create()).thenReturn(ve1);
        when(valueEntryListFactory.create()).thenReturn(new ArrayList<>());

        List<ValueEntry> vel = valuesEntryMapper.mapAverageRMP(armpl);

        verify(valueEntryListFactory).create();
        verify(valueEntryFactory).create();

        assertNotNull(vel);
        assertThat(vel, is(not(empty())));
        ValueEntry ve2 = vel.get(0);
        assertNotNull(ve2);
        assertNotNull(ve2.getTimeTag());
        assertEquals(timeTag.plusMinutes(30), ve2.getTimeTag());
        assertNotNull(ve2.getValue());
        assertSame(value, ve2.getValue());
    }

}
