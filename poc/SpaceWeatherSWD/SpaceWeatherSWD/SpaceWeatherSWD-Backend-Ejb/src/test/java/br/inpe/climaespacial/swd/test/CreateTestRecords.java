package br.inpe.climaespacial.swd.test;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import br.inpe.climaespacial.swd.acquisition.dtos.Mag;
import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;
import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.calculation.dtos.CalculatedValues;
import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;

public class CreateTestRecords {

    public MagEntity createMagEntity() {

        MagEntity me = new MagEntity();
        me.setId(UUID.randomUUID());
        me.setCreationDate(ZonedDateTime.now());
        me.setModificationDate(ZonedDateTime.now());
        me.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        me.setBt(0.11);
        me.setBxGsm(0.12);
        me.setByGsm(0.13);
        me.setBzGsm(0.14);
        me.setLatGsm(0.15);
        me.setLonGsm(0.16);

        return me;
    }

    public List<MagEntity> createMagEntityList() {

        MagEntity me1 = new MagEntity();
        me1.setId(UUID.randomUUID());
        me1.setCreationDate(ZonedDateTime.now());
        me1.setModificationDate(ZonedDateTime.now());
        me1.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        me1.setBt(0.11);
        me1.setBxGsm(0.12);
        me1.setByGsm(0.13);
        me1.setBzGsm(0.14);
        me1.setLatGsm(0.15);
        me1.setLonGsm(0.16);

        MagEntity me2 = new MagEntity();
        me2.setId(UUID.randomUUID());
        me2.setCreationDate(ZonedDateTime.now());
        me2.setModificationDate(ZonedDateTime.now());
        me2.setTimeTag(ZonedDateTime.parse("2017-01-02T12:00:00z[UTC]"));
        me2.setBt(0.21);
        me2.setBxGsm(0.22);
        me2.setByGsm(0.23);
        me2.setBzGsm(0.24);
        me2.setLatGsm(0.25);
        me2.setLonGsm(0.26);

        List<MagEntity> mel = Arrays.asList(me1, me2);

        return mel;
    }

    public List<Mag> createMagList() {

        Mag m1 = new Mag();
        m1.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        m1.setBt(0.11);
        m1.setBxGsm(0.12);
        m1.setByGsm(0.13);
        m1.setBzGsm(0.14);
        m1.setLatGsm(0.15);
        m1.setLonGsm(0.16);

        Mag m2 = new Mag();
        m2.setTimeTag(ZonedDateTime.parse("2017-01-02T12:00:00z[UTC]"));
        m2.setBt(0.21);
        m2.setBxGsm(0.22);
        m2.setByGsm(0.23);
        m2.setBzGsm(0.24);
        m2.setLatGsm(0.25);
        m2.setLonGsm(0.26);

        List<Mag> ml = Arrays.asList(m1, m2);

        return ml;
    }

    public PlasmaEntity createPlasmaEntity() {

        PlasmaEntity pe = new PlasmaEntity();
        pe.setId(UUID.randomUUID());
        pe.setCreationDate(ZonedDateTime.now());
        pe.setModificationDate(ZonedDateTime.now());
        pe.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        pe.setDensity(0.17);
        pe.setTemperature(0.18);
        pe.setSpeed(0.19);

        return pe;
    }

    public List<PlasmaEntity> createPlasmaEntityList() {

        PlasmaEntity pe1 = new PlasmaEntity();
        pe1.setId(UUID.randomUUID());
        pe1.setCreationDate(ZonedDateTime.now());
        pe1.setModificationDate(ZonedDateTime.now());
        pe1.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        pe1.setDensity(0.17);
        pe1.setTemperature(0.18);
        pe1.setSpeed(0.19);

        PlasmaEntity pe2 = new PlasmaEntity();
        pe2.setId(UUID.randomUUID());
        pe2.setCreationDate(ZonedDateTime.now());
        pe2.setModificationDate(ZonedDateTime.now());
        pe2.setTimeTag(ZonedDateTime.parse("2017-01-02T12:00:00z[UTC]"));
        pe2.setDensity(0.27);
        pe2.setTemperature(0.28);
        pe2.setSpeed(0.29);

        List<PlasmaEntity> pel = Arrays.asList(pe1, pe2);

        return pel;
    }

    public List<Plasma> createPlasmaList() {

        Plasma p1 = new Plasma();
        p1.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        p1.setDensity(0.17);
        p1.setTemperature(0.18);
        p1.setSpeed(0.19);

        Plasma p2 = new Plasma();
        p2.setTimeTag(ZonedDateTime.parse("2017-01-02T12:00:00z[UTC]"));
        p2.setDensity(0.27);
        p2.setTemperature(0.28);
        p2.setSpeed(0.29);

        List<Plasma> pl = Arrays.asList(p1, p2);

        return pl;
    }

    public CalculatedValuesEntity createCalculatedValuesEntity() {

        CalculatedValuesEntity cv = new CalculatedValuesEntity();
        cv.setId(UUID.randomUUID());
        cv.setCreationDate(ZonedDateTime.now());
        cv.setModificationDate(ZonedDateTime.now());
        cv.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        cv.setEy(0.37);
        cv.setDpr(0.38);
        cv.setRmp(0.39);

        return cv;
    }

    public List<CalculatedValuesEntity> createCalculatedValuesEntityList() {

        CalculatedValuesEntity cv1 = new CalculatedValuesEntity();
        cv1.setId(UUID.randomUUID());
        cv1.setCreationDate(ZonedDateTime.now());
        cv1.setModificationDate(ZonedDateTime.now());
        cv1.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        cv1.setEy(0.37);
        cv1.setDpr(0.38);
        cv1.setRmp(0.39);

        CalculatedValuesEntity cv2 = new CalculatedValuesEntity();
        cv2.setId(UUID.randomUUID());
        cv2.setCreationDate(ZonedDateTime.now());
        cv2.setModificationDate(ZonedDateTime.now());
        cv2.setTimeTag(ZonedDateTime.parse("2017-01-02T12:00:00z[UTC]"));
        cv2.setEy(0.47);
        cv2.setDpr(0.48);
        cv2.setRmp(0.49);

        List<CalculatedValuesEntity> cvel = Arrays.asList(cv1, cv2);

        return cvel;
    }

    public List<CalculatedValues> createCalculatedValuesList() {

        CalculatedValues cv1 = new CalculatedValues();
        cv1.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        cv1.setEy(0.37);
        cv1.setDpr(0.38);
        cv1.setRmp(0.39);

        CalculatedValues cv2 = new CalculatedValues();
        cv2.setTimeTag(ZonedDateTime.parse("2017-01-02T12:00:00z[UTC]"));
        cv2.setEy(0.47);
        cv2.setDpr(0.48);
        cv2.setRmp(0.49);

        List<CalculatedValues> cvel = Arrays.asList(cv1, cv2);

        return cvel;
    }

    public HourlyAverage createHourlyAverage() {
        HourlyAverage hourlyAverage = new HourlyAverage();

        hourlyAverage.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        hourlyAverage.setBxGsm(2.1);
        hourlyAverage.setByGsm(2.1);
        hourlyAverage.setBzGsm(2.1);
        hourlyAverage.setBt(2.1);
        hourlyAverage.setTemperature(2.1);
        hourlyAverage.setDensity(2.1);
        hourlyAverage.setSpeed(2.1);
        hourlyAverage.setEy(2.1);
        hourlyAverage.setDpr(2.1);
        hourlyAverage.setRmp(2.1);

        return hourlyAverage;
    }
    
}
