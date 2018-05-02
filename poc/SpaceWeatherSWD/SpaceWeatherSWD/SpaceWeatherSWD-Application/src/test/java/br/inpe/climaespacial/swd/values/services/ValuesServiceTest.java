package br.inpe.climaespacial.swd.values.services;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_BT;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_BX;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_BY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_BZ;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_DENSITY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_DPR;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_EY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_RMP;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_SPEED;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.AVERAGE_TEMPERATURE;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.BT;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.BX;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.BY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.BZ;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.DENSITY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.DPR;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.EY;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.RMP;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.SPEED;
import static br.inpe.climaespacial.swd.values.enums.ValuesEnum.TEMPERATURE;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.commons.helpers.DateTimeHelper;
import br.inpe.climaespacial.swd.home.validators.IntervalValidator;
import br.inpe.climaespacial.swd.indexes.RangeDate;
import br.inpe.climaespacial.swd.indexes.RangeDateFactory;
import br.inpe.climaespacial.swd.values.bt.average.dtos.AverageBT;
import br.inpe.climaespacial.swd.values.bt.average.repositories.AverageBTRepository;
import br.inpe.climaespacial.swd.values.bt.value.dtos.BT;
import br.inpe.climaespacial.swd.values.bt.value.repositories.BTRepository;
import br.inpe.climaespacial.swd.values.bx.average.dtos.AverageBX;
import br.inpe.climaespacial.swd.values.bx.average.repositories.AverageBXRepository;
import br.inpe.climaespacial.swd.values.bx.value.dtos.BX;
import br.inpe.climaespacial.swd.values.bx.value.repositories.BXRepository;
import br.inpe.climaespacial.swd.values.by.average.dtos.AverageBY;
import br.inpe.climaespacial.swd.values.by.average.repositories.AverageBYRepository;
import br.inpe.climaespacial.swd.values.by.value.dtos.BY;
import br.inpe.climaespacial.swd.values.by.value.repositories.BYRepository;
import br.inpe.climaespacial.swd.values.bz.average.dtos.AverageBZ;
import br.inpe.climaespacial.swd.values.bz.average.repositories.AverageBZRepository;
import br.inpe.climaespacial.swd.values.bz.value.dtos.BZ;
import br.inpe.climaespacial.swd.values.bz.value.repositories.BZRepository;
import br.inpe.climaespacial.swd.values.density.average.dtos.AverageDensity;
import br.inpe.climaespacial.swd.values.density.average.repositories.AverageDensityRepository;
import br.inpe.climaespacial.swd.values.density.value.dtos.Density;
import br.inpe.climaespacial.swd.values.density.value.repositories.DensityRepository;
import br.inpe.climaespacial.swd.values.dpr.average.dtos.AverageDPR;
import br.inpe.climaespacial.swd.values.dpr.average.repositories.AverageDPRRepository;
import br.inpe.climaespacial.swd.values.dpr.value.dtos.DPR;
import br.inpe.climaespacial.swd.values.dpr.value.repositories.DPRRepository;
import br.inpe.climaespacial.swd.values.dtos.ValueEntry;
import br.inpe.climaespacial.swd.values.dtos.ValuesData;
import br.inpe.climaespacial.swd.values.enums.ValuesEnum;
import br.inpe.climaespacial.swd.values.ey.average.dtos.AverageEY;
import br.inpe.climaespacial.swd.values.ey.average.repositories.AverageEYRepository;
import br.inpe.climaespacial.swd.values.ey.value.dtos.EY;
import br.inpe.climaespacial.swd.values.ey.value.repositories.EYRepository;
import br.inpe.climaespacial.swd.values.factories.ValuesDataFactory;
import br.inpe.climaespacial.swd.values.helpers.ValueEntryDataFillerHelper;
import br.inpe.climaespacial.swd.values.mappers.ValuesEntryMapper;
import br.inpe.climaespacial.swd.values.repositories.ValuesReaderRepository;
import br.inpe.climaespacial.swd.values.rmp.average.dtos.AverageRMP;
import br.inpe.climaespacial.swd.values.rmp.average.repositories.AverageRMPRepository;
import br.inpe.climaespacial.swd.values.rmp.value.dtos.RMP;
import br.inpe.climaespacial.swd.values.rmp.value.repositories.RMPRepository;
import br.inpe.climaespacial.swd.values.speed.average.dtos.AverageSpeed;
import br.inpe.climaespacial.swd.values.speed.average.repositories.AverageSpeedRepository;
import br.inpe.climaespacial.swd.values.speed.value.dtos.Speed;
import br.inpe.climaespacial.swd.values.speed.value.repositories.SpeedRepository;
import br.inpe.climaespacial.swd.values.temperature.average.dtos.AverageTemperature;
import br.inpe.climaespacial.swd.values.temperature.average.repositories.AverageTemperatureRepository;
import br.inpe.climaespacial.swd.values.temperature.value.dtos.Temperature;
import br.inpe.climaespacial.swd.values.temperature.value.repositories.TemperatureRepository;
import static org.mockito.ArgumentMatchers.any;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultValuesService.class)
public class ValuesServiceTest {

    private static final int DAYS_TO_SUBTRACT = 6;
    private static final int PERIOD_SIZE = 31;
    private final ZonedDateTime initialDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

    private final ZonedDateTime finalDateTime = ZonedDateTime.parse("2017-01-04T12:00:00z[UTC]");
    private final ZonedDateTime finalDateTime2 = ZonedDateTime.parse("2017-02-01T12:00:00z[UTC]");

    private final ZonedDateTime maxFinalDateTime = ZonedDateTime.parse("2017-01-04T23:59:59z[UTC]");
    private final ZonedDateTime maxFinalDateTime2 = ZonedDateTime.parse("2017-02-01T23:59:59z[UTC]");

    @Mock
    @Produces
    private IntervalValidator intervalValidator;

    @Mock
    @Produces
    private BTRepository btRepository;

    @Mock
    @Produces
    private AverageBTRepository averageBTRepository;

    @Mock
    @Produces
    private BYRepository byRepository;

    @Mock
    @Produces
    private AverageBYRepository averageBYRepository;

    @Mock
    @Produces
    private BXRepository bxRepository;

    @Mock
    @Produces
    private AverageBXRepository averageBXRepository;

    @Mock
    @Produces
    private BZRepository bzRepository;

    @Mock
    @Produces
    private AverageBZRepository averageBZRepository;

    @Mock
    @Produces
    private DensityRepository densityRepository;

    @Mock
    @Produces
    private AverageDensityRepository averageDensityRepository;

    @Mock
    @Produces
    private SpeedRepository speedRepository;

    @Mock
    @Produces
    private AverageSpeedRepository averageSpeedRepository;

    @Mock
    @Produces
    private TemperatureRepository temperatureRepository;

    @Mock
    @Produces
    private AverageTemperatureRepository averageTemperatureRepository;

    @Mock
    @Produces
    private EYRepository eyRepository;

    @Mock
    @Produces
    private AverageEYRepository averageEYRepository;

    @Mock
    @Produces
    private DPRRepository dprRepository;

    @Mock
    @Produces
    private AverageDPRRepository averageDPRRepository;

    @Mock
    @Produces
    private RMPRepository rmpRepository;

    @Mock
    @Produces
    private AverageRMPRepository averageRMPRepository;

    @Mock
    @Produces
    private ValuesEntryMapper valuesEntryMapper;

    @Mock
    @Produces
    private ValuesDataFactory valuesDataFactory;

    @Produces
    @Mock
    private RangeDateFactory rangeDateFactory;

    @Mock
    @Produces
    private DateTimeHelper dateTimeHelper;

    @Mock
    @Produces
    private ValuesReaderRepository valuesReaderRepository;

    @Mock
    @Produces
    private ValueEntryDataFillerHelper valueEntryDataFillerHelper;

    @Mock
    @Produces
    private ListFactory<ValueEntry> valueEntryListFactory;

    @Inject
    private ValuesService valuesService;

    @Test
    public void getValuesData_calledPeriodEqualTo3Days_succeeds() {
        when(dateTimeHelper.setMaxDayTime(finalDateTime)).thenReturn(maxFinalDateTime);

        ZonedDateTime initialDateTimeAverage = initialDateTime.plusMinutes(30);
        ZonedDateTime maxFinalDateTimeAverage = maxFinalDateTime.plusMinutes(30);

        List<BT> btl = mockList(BT.class);
        when(btRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(btl);
        List<ValueEntry> btvel1 = mockList(ValueEntry.class);
        List<ValueEntry> btvel2 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapBT(btl)).thenReturn(btvel1);
        when(valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, btvel1)).thenReturn(btvel2);
        List<AverageBT> abtl = mockList(AverageBT.class);
        when(averageBTRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(abtl);
        List<ValueEntry> abtvel1 = mockList(ValueEntry.class);
        List<ValueEntry> abtvel2 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageBT(abtl)).thenReturn(abtvel1);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abtvel1)).thenReturn(abtvel2);

        List<BY> byl = mockList(BY.class);
        when(byRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(byl);
        List<ValueEntry> byvel1 = mockList(ValueEntry.class);
        List<ValueEntry> byvel2 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapBY(byl)).thenReturn(byvel1);
        when(valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, byvel1)).thenReturn(byvel2);
        List<AverageBY> abyl = mockList(AverageBY.class);
        when(averageBYRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(abyl);
        List<ValueEntry> abyvel1 = mockList(ValueEntry.class);
        List<ValueEntry> abyvel2 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageBY(abyl)).thenReturn(abyvel1);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abyvel1)).thenReturn(abyvel2);

        List<BX> bxl = mockList(BX.class);
        when(bxRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(bxl);
        List<ValueEntry> bxvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapBX(bxl)).thenReturn(bxvel1);
        List<ValueEntry> bxvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, bxvel1)).thenReturn(bxvel2);
        List<AverageBX> abxl = mockList(AverageBX.class);
        when(averageBXRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(abxl);
        List<ValueEntry> abxvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageBX(abxl)).thenReturn(abxvel1);
        List<ValueEntry> abxvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abxvel1)).thenReturn(abxvel2);

        List<BZ> bzl = mockList(BZ.class);
        when(bzRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(bzl);
        List<ValueEntry> bzvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapBZ(bzl)).thenReturn(bzvel1);
        List<ValueEntry> bzvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, bzvel1)).thenReturn(bzvel2);
        List<AverageBZ> abzl = mockList(AverageBZ.class);
        when(averageBZRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(abzl);
        List<ValueEntry> abzvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageBZ(abzl)).thenReturn(abzvel1);
        List<ValueEntry> abzvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abzvel1)).thenReturn(abzvel2);

        List<Density> dl = mockList(Density.class);
        when(densityRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(dl);
        List<ValueEntry> dvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapDensity(dl)).thenReturn(dvel1);
        List<ValueEntry> dvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, dvel1)).thenReturn(dvel2);
        List<AverageDensity> adl = mockList(AverageDensity.class);
        when(averageDensityRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(adl);
        List<ValueEntry> advel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageDensity(adl)).thenReturn(advel1);
        List<ValueEntry> advel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, advel1)).thenReturn(advel2);

        List<Speed> sl = mockList(Speed.class);
        when(speedRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(sl);
        List<ValueEntry> svel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapSpeed(sl)).thenReturn(svel1);
        List<ValueEntry> svel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, svel1)).thenReturn(svel2);
        List<AverageSpeed> asl = mockList(AverageSpeed.class);
        when(averageSpeedRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(asl);
        List<ValueEntry> asvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageSpeed(asl)).thenReturn(asvel1);
        List<ValueEntry> asvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, asvel1)).thenReturn(asvel2);

        List<Temperature> tl = mockList(Temperature.class);
        when(temperatureRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(tl);
        List<ValueEntry> tvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapTemperature(tl)).thenReturn(tvel1);
        List<ValueEntry> tvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, tvel1)).thenReturn(tvel2);
        List<AverageTemperature> atl = mockList(AverageTemperature.class);
        when(averageTemperatureRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(atl);
        List<ValueEntry> atvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageTemperature(atl)).thenReturn(atvel1);
        List<ValueEntry> atvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, atvel1)).thenReturn(atvel2);

        List<EY> eyl = mockList(EY.class);
        when(eyRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(eyl);
        List<ValueEntry> eyvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapEY(eyl)).thenReturn(eyvel1);
        List<ValueEntry> eyvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, eyvel1)).thenReturn(eyvel2);
        List<AverageEY> aeyl = mockList(AverageEY.class);
        when(averageEYRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(aeyl);
        List<ValueEntry> aeyvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageEY(aeyl)).thenReturn(aeyvel1);
        List<ValueEntry> aeyvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, aeyvel1)).thenReturn(aeyvel2);

        List<DPR> dprl = mockList(DPR.class);
        when(dprRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(dprl);
        List<ValueEntry> dprvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapDPR(dprl)).thenReturn(dprvel1);
        List<ValueEntry> dprvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, dprvel1)).thenReturn(dprvel2);
        List<AverageDPR> adprl = mockList(AverageDPR.class);
        when(averageDPRRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(adprl);
        List<ValueEntry> adprvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageDPR(adprl)).thenReturn(adprvel1);
        List<ValueEntry> adprvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, adprvel1)).thenReturn(adprvel2);

        List<RMP> rmpl = mockList(RMP.class);
        when(rmpRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(rmpl);
        List<ValueEntry> rmpvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapRMP(rmpl)).thenReturn(rmpvel1);
        List<ValueEntry> rmpvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, rmpvel1)).thenReturn(rmpvel2);
        List<AverageRMP> armpl = mockList(AverageRMP.class);
        when(averageRMPRepository.list(initialDateTime, maxFinalDateTime)).thenReturn(armpl);
        List<ValueEntry> armpvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageRMP(armpl)).thenReturn(armpvel1);
        List<ValueEntry> armpvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, armpvel1)).thenReturn(armpvel2);

        ValuesData vd1 = new ValuesData();
        vd1.setBTList(btvel2);
        vd1.setAverageBTList(abtvel2);

        vd1.setBYList(byvel2);
        vd1.setAverageBYList(abyvel2);

        vd1.setBXList(bxvel2);
        vd1.setAverageBXList(abxvel2);

        vd1.setBZList(bzvel2);
        vd1.setAverageBZList(abzvel2);

        vd1.setDensityList(dvel2);
        vd1.setAverageDensityList(advel2);

        vd1.setSpeed(svel2);
        vd1.setAverageSpeedList(asvel2);

        vd1.setTemperatureList(tvel2);
        vd1.setAverageTemperatureList(atvel2);

        vd1.setEYList(eyvel2);
        vd1.setAverageEYList(aeyvel2);

        vd1.setDPRList(dprvel2);
        vd1.setAverageDPRList(adprvel2);

        vd1.setRMPList(rmpvel2);
        vd1.setAverageRMPList(armpvel2);

        Map<ValuesEnum, List<ValueEntry>> vmap = new HashMap<>();
        vmap.put(BT, btvel2);
        vmap.put(AVERAGE_BT, abtvel2);
        vmap.put(BY, byvel2);
        vmap.put(AVERAGE_BY, abyvel2);
        vmap.put(BX, bxvel2);
        vmap.put(AVERAGE_BX, abxvel2);
        vmap.put(BZ, bzvel2);
        vmap.put(AVERAGE_BZ, abzvel2);
        vmap.put(DENSITY, dvel2);
        vmap.put(AVERAGE_DENSITY, advel2);
        vmap.put(SPEED, svel2);
        vmap.put(AVERAGE_SPEED, asvel2);
        vmap.put(TEMPERATURE, tvel2);
        vmap.put(AVERAGE_TEMPERATURE, atvel2);
        vmap.put(EY, eyvel2);
        vmap.put(AVERAGE_EY, aeyvel2);
        vmap.put(DPR, dprvel2);
        vmap.put(AVERAGE_DPR, adprvel2);
        vmap.put(RMP, rmpvel2);
        vmap.put(AVERAGE_RMP, armpvel2);

        when(valuesDataFactory.create(vmap)).thenReturn(vd1);

        ValuesData vd2 = valuesService.getValuesData(initialDateTime, finalDateTime);

        assertNotNull(vd2);
        assertSame(vd1, vd2);

        assertNotNull(vd2.getBTList());
        assertSame(btvel2, vd2.getBTList());
        assertNotNull(vd2.getAverageBTList());
        assertSame(abtvel2, vd2.getAverageBTList());

        assertNotNull(vd2.getBYList());
        assertSame(byvel2, vd2.getBYList());
        assertNotNull(vd2.getAverageBYList());
        assertSame(abyvel2, vd2.getAverageBYList());

        assertNotNull(vd2.getBXList());
        assertSame(bxvel2, vd2.getBXList());
        assertNotNull(vd1.getAverageBXList());
        assertSame(abxvel2, vd2.getAverageBXList());

        assertNotNull(vd2.getBZList());
        assertSame(bzvel2, vd2.getBZList());
        assertNotNull(vd2.getAverageBZList());
        assertSame(abzvel2, vd2.getAverageBZList());

        assertNotNull(vd2.getDensityList());
        assertSame(dvel2, vd2.getDensityList());
        assertNotNull(vd2.getAverageDensityList());
        assertSame(advel2, vd2.getAverageDensityList());

        assertNotNull(vd2.getSpeedList());
        assertSame(svel2, vd2.getSpeedList());
        assertNotNull(vd2.getAverageSpeedList());
        assertSame(asvel2, vd2.getAverageSpeedList());

        assertNotNull(vd2.getTemperatureList());
        assertSame(tvel2, vd2.getTemperatureList());
        assertNotNull(vd2.getAverageTemperatureList());
        assertSame(atvel2, vd2.getAverageTemperatureList());

        assertNotNull(vd2.getEYList());
        assertSame(eyvel2, vd2.getEYList());
        assertNotNull(vd2.getAverageEYList());
        assertSame(aeyvel2, vd2.getAverageEYList());

        assertNotNull(vd2.getDPRList());
        assertSame(dprvel2, vd2.getDPRList());
        assertNotNull(vd2.getAverageDPRList());
        assertSame(adprvel2, vd2.getAverageDPRList());

        assertNotNull(vd2.getRMPList());
        assertSame(rmpvel2, vd2.getRMPList());
        assertNotNull(vd2.getAverageRMPList());
        assertSame(armpvel2, vd2.getAverageRMPList());

        verify(intervalValidator).validate(initialDateTime, finalDateTime, PERIOD_SIZE);
        verify(dateTimeHelper).setMaxDayTime(finalDateTime);

        verify(btRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapBT(btl);
        verify(valueEntryDataFillerHelper).fillByMinutes(initialDateTime, maxFinalDateTime, btvel1);
        verify(averageBTRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapAverageBT(abtl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abtvel1);

        verify(byRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapBY(byl);
        verify(valueEntryDataFillerHelper).fillByMinutes(initialDateTime, maxFinalDateTime, byvel1);
        verify(averageBYRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapAverageBY(abyl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abyvel1);

        verify(bxRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapBX(bxl);
        verify(valueEntryDataFillerHelper).fillByMinutes(initialDateTime, maxFinalDateTime, bxvel1);
        verify(averageBXRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapAverageBX(abxl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abxvel1);

        verify(bzRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapBZ(bzl);
        verify(valueEntryDataFillerHelper).fillByMinutes(initialDateTime, maxFinalDateTime, bzvel1);
        verify(averageBZRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapAverageBZ(abzl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abzvel1);

        verify(densityRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapDensity(dl);
        verify(valueEntryDataFillerHelper).fillByMinutes(initialDateTime, maxFinalDateTime, dvel1);
        verify(averageDensityRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapAverageDensity(adl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, advel1);

        verify(speedRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapSpeed(sl);
        verify(valueEntryDataFillerHelper).fillByMinutes(initialDateTime, maxFinalDateTime, svel1);
        verify(averageSpeedRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapAverageSpeed(asl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, asvel1);

        verify(temperatureRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapTemperature(tl);
        verify(valueEntryDataFillerHelper).fillByMinutes(initialDateTime, maxFinalDateTime, tvel1);
        verify(averageTemperatureRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapAverageTemperature(atl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, atvel1);

        verify(eyRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapEY(eyl);
        verify(valueEntryDataFillerHelper).fillByMinutes(initialDateTime, maxFinalDateTime, eyvel1);
        verify(averageEYRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapAverageEY(aeyl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, aeyvel1);

        verify(dprRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapDPR(dprl);
        verify(valueEntryDataFillerHelper).fillByMinutes(initialDateTime, maxFinalDateTime, dprvel1);
        verify(averageDPRRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapAverageDPR(adprl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, adprvel1);

        verify(rmpRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapRMP(rmpl);
        verify(valueEntryDataFillerHelper).fillByMinutes(initialDateTime, maxFinalDateTime, rmpvel1);
        verify(averageRMPRepository).list(initialDateTime, maxFinalDateTime);
        verify(valuesEntryMapper).mapAverageRMP(armpl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, armpvel1);

        verify(valuesDataFactory).create(vmap);
    }

    @Test
    public void getValuesData_calledPeriodMoreThan3Days_succeeds() {
        ZonedDateTime initialDateTimeAverage = initialDateTime.plusMinutes(30);
        ZonedDateTime maxFinalDateTimeAverage = maxFinalDateTime2.plusMinutes(30);
        
        when(dateTimeHelper.setMaxDayTime(finalDateTime2)).thenReturn(maxFinalDateTime2);

        List<AverageBT> abtl = mockList(AverageBT.class);
        when(averageBTRepository.list(initialDateTime, maxFinalDateTime2)).thenReturn(abtl);
        List<ValueEntry> abtvel1 = mockList(ValueEntry.class);
        List<ValueEntry> abtvel2 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageBT(abtl)).thenReturn(abtvel1);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abtvel1)).thenReturn(abtvel2);

        List<AverageBY> abyl = mockList(AverageBY.class);
        when(averageBYRepository.list(initialDateTime, maxFinalDateTime2)).thenReturn(abyl);
        List<ValueEntry> abyvel1 = mockList(ValueEntry.class);
        List<ValueEntry> abyvel2 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageBY(abyl)).thenReturn(abyvel1);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abyvel1)).thenReturn(abyvel2);

        List<AverageBX> abxl = mockList(AverageBX.class);
        when(averageBXRepository.list(initialDateTime, maxFinalDateTime2)).thenReturn(abxl);
        List<ValueEntry> abxvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageBX(abxl)).thenReturn(abxvel1);
        List<ValueEntry> abxvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abxvel1)).thenReturn(abxvel2);

        List<AverageBZ> abzl = mockList(AverageBZ.class);
        when(averageBZRepository.list(initialDateTime, maxFinalDateTime2)).thenReturn(abzl);
        List<ValueEntry> abzvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageBZ(abzl)).thenReturn(abzvel1);
        List<ValueEntry> abzvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abzvel1)).thenReturn(abzvel2);

        List<AverageDensity> adl = mockList(AverageDensity.class);
        when(averageDensityRepository.list(initialDateTime, maxFinalDateTime2)).thenReturn(adl);
        List<ValueEntry> advel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageDensity(adl)).thenReturn(advel1);
        List<ValueEntry> advel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, advel1)).thenReturn(advel2);

        List<AverageSpeed> asl = mockList(AverageSpeed.class);
        when(averageSpeedRepository.list(initialDateTime, maxFinalDateTime2)).thenReturn(asl);
        List<ValueEntry> asvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageSpeed(asl)).thenReturn(asvel1);
        List<ValueEntry> asvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, asvel1)).thenReturn(asvel2);

        List<AverageTemperature> atl = mockList(AverageTemperature.class);
        when(averageTemperatureRepository.list(initialDateTime, maxFinalDateTime2)).thenReturn(atl);
        List<ValueEntry> atvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageTemperature(atl)).thenReturn(atvel1);
        List<ValueEntry> atvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, atvel1)).thenReturn(atvel2);

        List<AverageEY> aeyl = mockList(AverageEY.class);
        when(averageEYRepository.list(initialDateTime, maxFinalDateTime2)).thenReturn(aeyl);
        List<ValueEntry> aeyvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageEY(aeyl)).thenReturn(aeyvel1);
        List<ValueEntry> aeyvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, aeyvel1)).thenReturn(aeyvel2);

        List<AverageDPR> adprl = mockList(AverageDPR.class);
        when(averageDPRRepository.list(initialDateTime, maxFinalDateTime2)).thenReturn(adprl);
        List<ValueEntry> adprvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageDPR(adprl)).thenReturn(adprvel1);
        List<ValueEntry> adprvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, adprvel1)).thenReturn(adprvel2);

        List<AverageRMP> armpl = mockList(AverageRMP.class);
        when(averageRMPRepository.list(initialDateTime, maxFinalDateTime2)).thenReturn(armpl);
        List<ValueEntry> armpvel1 = mockList(ValueEntry.class);
        when(valuesEntryMapper.mapAverageRMP(armpl)).thenReturn(armpvel1);
        List<ValueEntry> armpvel2 = mockList(ValueEntry.class);
        when(valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, armpvel1)).thenReturn(armpvel2);

        List<List<ValueEntry>> llve = generateValues();

        ValuesData vd1 = createValueData(llve);
        vd1.setAverageBTList(abtvel2);
        vd1.setAverageBYList(abyvel2);
        vd1.setAverageBXList(abxvel2);
        vd1.setAverageBZList(abzvel2);
        vd1.setAverageDensityList(advel2);
        vd1.setAverageSpeedList(asvel2);
        vd1.setAverageTemperatureList(atvel2);
        vd1.setAverageEYList(aeyvel2);
        vd1.setAverageDPRList(adprvel2);
        vd1.setAverageRMPList(armpvel2);

        Map<ValuesEnum, List<ValueEntry>> vmap = new HashMap<>();
        vmap.put(BT, llve.get(0));
        vmap.put(AVERAGE_BT, abtvel2);
        vmap.put(BY, llve.get(1));
        vmap.put(AVERAGE_BY, abyvel2);
        vmap.put(BX, llve.get(2));
        vmap.put(AVERAGE_BX, abxvel2);
        vmap.put(BZ, llve.get(3));
        vmap.put(AVERAGE_BZ, abzvel2);
        vmap.put(DENSITY, llve.get(4));
        vmap.put(AVERAGE_DENSITY, advel2);
        vmap.put(SPEED, llve.get(5));
        vmap.put(AVERAGE_SPEED, asvel2);
        vmap.put(TEMPERATURE, llve.get(6));
        vmap.put(AVERAGE_TEMPERATURE, atvel2);
        vmap.put(EY, llve.get(7));
        vmap.put(AVERAGE_EY, aeyvel2);
        vmap.put(DPR, llve.get(8));
        vmap.put(AVERAGE_DPR, adprvel2);
        vmap.put(RMP, llve.get(9));
        vmap.put(AVERAGE_RMP, armpvel2);

        //when(valuesDataFactory.create(vmap)).thenReturn(vd1);
        
        when(valuesDataFactory.create(any())).thenReturn(vd1);

        ValuesData vd2 = valuesService.getValuesData(initialDateTime, finalDateTime2);

        assertNotNull(vd2);
        assertSame(vd1, vd2);

        assertNotNull(vd2.getBTList());
        assertSame(llve.get(0), vd2.getBTList());
        assertNotNull(vd2.getAverageBTList());
        assertSame(abtvel2, vd2.getAverageBTList());

        assertNotNull(vd2.getBYList());
        assertSame(llve.get(1), vd2.getBYList());
        assertNotNull(vd2.getAverageBYList());
        assertSame(abyvel2, vd2.getAverageBYList());

        assertNotNull(vd2.getBXList());
        assertSame(llve.get(2), vd2.getBXList());
        assertNotNull(vd1.getAverageBXList());
        assertSame(abxvel2, vd2.getAverageBXList());

        assertNotNull(vd2.getBZList());
        assertSame(llve.get(3), vd2.getBZList());
        assertNotNull(vd2.getAverageBZList());
        assertSame(abzvel2, vd2.getAverageBZList());

        assertNotNull(vd2.getDensityList());
        assertSame(llve.get(4), vd2.getDensityList());
        assertNotNull(vd2.getAverageDensityList());
        assertSame(advel2, vd2.getAverageDensityList());

        assertNotNull(vd2.getSpeedList());
        assertSame(llve.get(5), vd2.getSpeedList());
        assertNotNull(vd2.getAverageSpeedList());
        assertSame(asvel2, vd2.getAverageSpeedList());

        assertNotNull(vd2.getTemperatureList());
        assertSame(llve.get(6), vd2.getTemperatureList());
        assertNotNull(vd2.getAverageTemperatureList());
        assertSame(atvel2, vd2.getAverageTemperatureList());

        assertNotNull(vd2.getEYList());
        assertSame(llve.get(7), vd2.getEYList());
        assertNotNull(vd2.getAverageEYList());
        assertSame(aeyvel2, vd2.getAverageEYList());

        assertNotNull(vd2.getDPRList());
        assertSame(llve.get(8), vd2.getDPRList());
        assertNotNull(vd2.getAverageDPRList());
        assertSame(adprvel2, vd2.getAverageDPRList());

        assertNotNull(vd2.getRMPList());
        assertSame(llve.get(9), vd2.getRMPList());
        assertNotNull(vd2.getAverageRMPList());
        assertSame(armpvel2, vd2.getAverageRMPList());

        verify(intervalValidator).validate(initialDateTime, finalDateTime2, PERIOD_SIZE);
        verify(dateTimeHelper).setMaxDayTime(finalDateTime2);

        verify(averageBTRepository).list(initialDateTime, maxFinalDateTime2);
        verify(valuesEntryMapper).mapAverageBT(abtl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abtvel1);

        verify(averageBYRepository).list(initialDateTime, maxFinalDateTime2);
        verify(valuesEntryMapper).mapAverageBY(abyl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abyvel1);

        verify(averageBXRepository).list(initialDateTime, maxFinalDateTime2);
        verify(valuesEntryMapper).mapAverageBX(abxl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abxvel1);

        verify(averageBZRepository).list(initialDateTime, maxFinalDateTime2);
        verify(valuesEntryMapper).mapAverageBZ(abzl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abzvel1);

        verify(averageDensityRepository).list(initialDateTime, maxFinalDateTime2);
        verify(valuesEntryMapper).mapAverageDensity(adl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, advel1);

        verify(averageSpeedRepository).list(initialDateTime, maxFinalDateTime2);
        verify(valuesEntryMapper).mapAverageSpeed(asl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, asvel1);

        verify(averageTemperatureRepository).list(initialDateTime, maxFinalDateTime2);
        verify(valuesEntryMapper).mapAverageTemperature(atl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, atvel1);

        verify(averageEYRepository).list(initialDateTime, maxFinalDateTime2);
        verify(valuesEntryMapper).mapAverageEY(aeyl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, aeyvel1);

        verify(averageDPRRepository).list(initialDateTime, maxFinalDateTime2);
        verify(valuesEntryMapper).mapAverageDPR(adprl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, adprvel1);

        verify(averageRMPRepository).list(initialDateTime, maxFinalDateTime2);
        verify(valuesEntryMapper).mapAverageRMP(armpl);
        verify(valueEntryDataFillerHelper).fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, armpvel1);

        verify(valueEntryListFactory, times(10)).create();

        verify(valuesDataFactory).create(vmap);
    }

    private ValuesData createValueData(List<List<ValueEntry>> llve) {
        ValuesData vd1 = new ValuesData();
        vd1.setBTList(llve.get(0));
        vd1.setBYList(llve.get(1));
        vd1.setBXList(llve.get(2));
        vd1.setBZList(llve.get(3));
        vd1.setDensityList(llve.get(4));
        vd1.setSpeed(llve.get(5));
        vd1.setTemperatureList(llve.get(6));
        vd1.setEYList(llve.get(7));
        vd1.setDPRList(llve.get(8));
        vd1.setRMPList(llve.get(9));
        return vd1;
    }

    private List<List<ValueEntry>> generateValues() {
        List<List<ValueEntry>> llve = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            List<ValueEntry> lve = new ArrayList<>();
            when(valueEntryListFactory.create()).thenReturn(lve);
            llve.add(lve);
        }
        return llve;
    }

    @Test
    public void getRangeData_called_succeeds() {
        ZonedDateTime lid = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        ZonedDateTime elid = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        ZonedDateTime fid = elid.minusDays(DAYS_TO_SUBTRACT);
        when(valuesReaderRepository.lastValuesDate()).thenReturn(lid);
        when(dateTimeHelper.truncate(lid)).thenReturn(elid);
        RangeDate rd1 = mock(RangeDate.class);
        when(rangeDateFactory.create(fid, elid)).thenReturn(rd1);

        RangeDate rd2 = valuesService.getRangeDate();

        assertNotNull(rd2);
        assertSame(rd1, rd2);
        verify(valuesReaderRepository).lastValuesDate();
        verify(dateTimeHelper).truncate(lid);
        verify(rangeDateFactory).create(fid, elid);
    }

    @Test
    public void getRangeData_called_returnsRangeDateWithNullValues() {
        ZonedDateTime lid = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        ZonedDateTime elid = null;
        ZonedDateTime fid = null;
        when(valuesReaderRepository.lastValuesDate()).thenReturn(lid);
        when(dateTimeHelper.truncate(lid)).thenReturn(elid);
        RangeDate rd1 = mock(RangeDate.class);
        when(rangeDateFactory.create(fid, elid)).thenReturn(rd1);

        RangeDate rd2 = valuesService.getRangeDate();

        assertNotNull(rd2);
        assertSame(rd1, rd2);
        verify(valuesReaderRepository).lastValuesDate();
        verify(dateTimeHelper).truncate(lid);
        verify(rangeDateFactory).create(fid, elid);
    }

}
