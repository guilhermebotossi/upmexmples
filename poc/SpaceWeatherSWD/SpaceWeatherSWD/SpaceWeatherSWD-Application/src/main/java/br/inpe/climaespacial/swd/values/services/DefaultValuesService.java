package br.inpe.climaespacial.swd.values.services;

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

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

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
import java.time.Duration;

@Dependent
public class DefaultValuesService implements ValuesService {

    private static final int DAYS_TO_SUBTRACT = 6;    
    private static final int PERIOD_SIZE = 31;

    @Inject
    private IntervalValidator intervalValidator;

    @Inject
    private BTRepository bTRepository;

    @Inject
    private AverageBTRepository averageBTRepository;

    @Inject
    private BYRepository byRepository;

    @Inject
    private AverageBYRepository averageBYRepository;

    @Inject
    private BXRepository bxRepository;

    @Inject
    private AverageBXRepository averageBXRepository;

    @Inject
    private BZRepository bzRepository;

    @Inject
    private AverageBZRepository averageBZRepository;

    @Inject
    private DensityRepository densityRepository;

    @Inject
    private AverageDensityRepository averageDensityRepository;

    @Inject
    private SpeedRepository speedRepository;

    @Inject
    private AverageSpeedRepository averageSpeedRepository;

    @Inject
    private TemperatureRepository temperatureRepository;

    @Inject
    private AverageTemperatureRepository averageTemperatureRepository;

    @Inject
    private EYRepository eyRepository;

    @Inject
    private AverageEYRepository averageEYRepository;

    @Inject
    private DPRRepository dprRepository;

    @Inject
    private AverageDPRRepository averageDPRRepository;

    @Inject
    private RMPRepository rmpRepository;

    @Inject
    private AverageRMPRepository averageRMPRepository;

    @Inject
    private ValuesEntryMapper valuesEntryMapper;

    @Inject
    private DateTimeHelper dateTimeHelper;

    @Inject
    private ValuesDataFactory valuesDataFactory;

    @Inject
    private ValuesReaderRepository valuesReaderRepository;

    @Inject
    private RangeDateFactory rangeDateFactory;

    @Inject
    private ValueEntryDataFillerHelper valueEntryDataFillerHelper;

    @Inject
    private ListFactory<ValueEntry> valueEntryListFactory;

    @Override
    public ValuesData getValuesData(ZonedDateTime initialDateTime, ZonedDateTime finalDateTime) {
        intervalValidator.validate(initialDateTime, finalDateTime, PERIOD_SIZE);

        ZonedDateTime maxFinalDateTime = dateTimeHelper.setMaxDayTime(finalDateTime);
        
        ZonedDateTime initialDateTimeAverage = initialDateTime.plusMinutes(30);
        ZonedDateTime maxFinalDateTimeAverage = maxFinalDateTime.plusMinutes(30);

        Map<ValuesEnum, List<ValueEntry>> vmap = new HashMap<>();

        List<ValueEntry> btvel;
        List<ValueEntry> bxvel;
        List<ValueEntry> byvel;
        List<ValueEntry> bzvel;
        List<ValueEntry> dvel;
        List<ValueEntry> svel;
        List<ValueEntry> tvel;
        List<ValueEntry> eyvel;
        List<ValueEntry> dprvel;
        List<ValueEntry> rmpvel;

        Duration d = Duration.between(initialDateTime, maxFinalDateTime);

        if (d.toDays() <= 3) {
            List<BT> btl = bTRepository.list(initialDateTime, maxFinalDateTime);
            btvel = valuesEntryMapper.mapBT(btl);
            btvel = valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, btvel);

            List<BY> byl = byRepository.list(initialDateTime, maxFinalDateTime);
            byvel = valuesEntryMapper.mapBY(byl);
            byvel = valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, byvel);

            List<BX> bxl = bxRepository.list(initialDateTime, maxFinalDateTime);
            bxvel = valuesEntryMapper.mapBX(bxl);
            bxvel = valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, bxvel);

            List<BZ> bzList = bzRepository.list(initialDateTime, maxFinalDateTime);
            bzvel = valuesEntryMapper.mapBZ(bzList);
            bzvel = valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, bzvel);

            List<Density> dl = densityRepository.list(initialDateTime, maxFinalDateTime);
            dvel = valuesEntryMapper.mapDensity(dl);
            dvel = valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, dvel);

            List<Speed> sl = speedRepository.list(initialDateTime, maxFinalDateTime);
            svel = valuesEntryMapper.mapSpeed(sl);
            svel = valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, svel);

            List<Temperature> tl = temperatureRepository.list(initialDateTime, maxFinalDateTime);
            tvel = valuesEntryMapper.mapTemperature(tl);
            tvel = valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, tvel);

            List<EY> eyl = eyRepository.list(initialDateTime, maxFinalDateTime);
            eyvel = valuesEntryMapper.mapEY(eyl);
            eyvel = valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, eyvel);

            List<DPR> dprl = dprRepository.list(initialDateTime, maxFinalDateTime);
            dprvel = valuesEntryMapper.mapDPR(dprl);
            dprvel = valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, dprvel);

            List<RMP> rmpl = rmpRepository.list(initialDateTime, maxFinalDateTime);
            rmpvel = valuesEntryMapper.mapRMP(rmpl);
            rmpvel = valueEntryDataFillerHelper.fillByMinutes(initialDateTime, maxFinalDateTime, rmpvel);
        } else {
            btvel = valueEntryListFactory.create();
            bxvel = valueEntryListFactory.create();
            byvel = valueEntryListFactory.create();
            bzvel = valueEntryListFactory.create();
            dvel = valueEntryListFactory.create();
            svel = valueEntryListFactory.create();
            tvel = valueEntryListFactory.create();
            eyvel = valueEntryListFactory.create();
            dprvel = valueEntryListFactory.create();
            rmpvel = valueEntryListFactory.create();
        }

        vmap.put(BT, btvel);
        vmap.put(BY, byvel);
        vmap.put(BX, bxvel);
        vmap.put(BZ, bzvel);
        vmap.put(DENSITY, dvel);
        vmap.put(SPEED, svel);
        vmap.put(TEMPERATURE, tvel);
        vmap.put(EY, eyvel);
        vmap.put(DPR, dprvel);
        vmap.put(RMP, rmpvel);

        List<AverageBT> abtl = averageBTRepository.list(initialDateTime, maxFinalDateTime);
        List<ValueEntry> abtvel = valuesEntryMapper.mapAverageBT(abtl);
        abtvel = valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abtvel);
        vmap.put(AVERAGE_BT, abtvel);

        List<AverageBY> abyl = averageBYRepository.list(initialDateTime, maxFinalDateTime);
        List<ValueEntry> abyvel = valuesEntryMapper.mapAverageBY(abyl);
        abyvel = valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abyvel);
        vmap.put(AVERAGE_BY, abyvel);

        List<AverageBX> abxl = averageBXRepository.list(initialDateTime, maxFinalDateTime);
        List<ValueEntry> abxvel = valuesEntryMapper.mapAverageBX(abxl);
        abxvel = valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abxvel);
        vmap.put(AVERAGE_BX, abxvel);

        List<AverageBZ> abzl = averageBZRepository.list(initialDateTime, maxFinalDateTime);
        List<ValueEntry> abzvel = valuesEntryMapper.mapAverageBZ(abzl);
        abzvel = valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, abzvel);
        vmap.put(AVERAGE_BZ, abzvel);

        List<AverageDensity> adl = averageDensityRepository.list(initialDateTime, maxFinalDateTime);
        List<ValueEntry> advel = valuesEntryMapper.mapAverageDensity(adl);
        advel = valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, advel);
        vmap.put(AVERAGE_DENSITY, advel);

        List<AverageSpeed> asl = averageSpeedRepository.list(initialDateTime, maxFinalDateTime);
        List<ValueEntry> asvel = valuesEntryMapper.mapAverageSpeed(asl);
        asvel = valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, asvel);
        vmap.put(AVERAGE_SPEED, asvel);

        List<AverageTemperature> atl = averageTemperatureRepository.list(initialDateTime, maxFinalDateTime);
        List<ValueEntry> atvel = valuesEntryMapper.mapAverageTemperature(atl);
        atvel = valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, atvel);
        vmap.put(AVERAGE_TEMPERATURE, atvel);

        List<AverageEY> aeyl = averageEYRepository.list(initialDateTime, maxFinalDateTime);
        List<ValueEntry> aeyvel = valuesEntryMapper.mapAverageEY(aeyl);
        aeyvel = valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, aeyvel);
        vmap.put(AVERAGE_EY, aeyvel);

        List<AverageDPR> adprl = averageDPRRepository.list(initialDateTime, maxFinalDateTime);
        List<ValueEntry> adprvel = valuesEntryMapper.mapAverageDPR(adprl);
        adprvel = valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, adprvel);
        vmap.put(AVERAGE_DPR, adprvel);

        List<AverageRMP> armpl = averageRMPRepository.list(initialDateTime, maxFinalDateTime);
        List<ValueEntry> armpvel = valuesEntryMapper.mapAverageRMP(armpl);
        armpvel = valueEntryDataFillerHelper.fillByHours(initialDateTimeAverage, maxFinalDateTimeAverage, armpvel);
        vmap.put(AVERAGE_RMP, armpvel);

        return valuesDataFactory.create(vmap);
    }

    @Override
    public RangeDate getRangeDate() {
        ZonedDateTime lvd = valuesReaderRepository.lastValuesDate();

        ZonedDateTime tlvd = dateTimeHelper.truncate(lvd);

        ZonedDateTime fvd =  tlvd == null ? null : tlvd.minusDays(DAYS_TO_SUBTRACT);
        return rangeDateFactory.create(fvd, tlvd);
    }

}
