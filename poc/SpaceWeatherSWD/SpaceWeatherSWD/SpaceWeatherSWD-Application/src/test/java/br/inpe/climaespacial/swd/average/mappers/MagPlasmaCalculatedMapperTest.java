package br.inpe.climaespacial.swd.average.mappers;

import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.average.entities.MagPlasmaCalculatedEntity;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasmaCalculated;
import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;
import br.inpe.climaespacial.swd.calculation.factories.DefaultMagPlasmaCalculatedFactory;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultMagPlasmaCalculatedMapper.class,
    DefaultListFactory.class,
    DefaultMagPlasmaCalculatedFactory.class
})
public class MagPlasmaCalculatedMapperTest {

    @Inject
    private MagPlasmaCalculatedMapper magPlasmaCalculatedMapper;

    private final static double DELTA = 0.001;

    @Test
    public void map_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            magPlasmaCalculatedMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"magPlasmaCalculatedEntityList\" cannot be null.", re.getMessage());

    }

    @Test
    public void map_called_returnsMagPlasmaCalculatedList() {

        MagEntity me = createMagEntity();
        PlasmaEntity pe = createPlasmaEntity();
        CalculatedValuesEntity cve = createCalculatedValuesEntity();
        MagPlasmaCalculatedEntity mpce1 = new MagPlasmaCalculatedEntity(me, pe, cve);
        MagPlasmaCalculatedEntity mpce2 = new MagPlasmaCalculatedEntity(me, pe, cve);
        List<MagPlasmaCalculatedEntity> magPlasmaCalculatedEntityList = Arrays.asList(mpce1, mpce2);

        List<MagPlasmaCalculated> mpcl = magPlasmaCalculatedMapper.map(magPlasmaCalculatedEntityList);

        assertNotNull(mpcl);
        assertEquals(2, mpcl.size());

        MagPlasmaCalculated mpc1 = mpcl.get(0);
        assertEquals(me.getBxGsm(), mpc1.getBxGsm(), DELTA);
        assertEquals(me.getByGsm(), mpc1.getByGsm(), DELTA);
        assertEquals(me.getBzGsm(), mpc1.getBzGsm(), DELTA);
        assertEquals(me.getBt(), mpc1.getBt(), DELTA);
        assertEquals(pe.getDensity(), mpc1.getDensity(), DELTA);
        assertEquals(pe.getSpeed(), mpc1.getSpeed(), DELTA);
        assertEquals(pe.getTemperature(), mpc1.getTemperature(), DELTA);
        assertEquals(cve.getEy(), mpc1.getEy(), DELTA);
        assertEquals(cve.getDpr(), mpc1.getDpr(), DELTA);
        assertEquals(cve.getRmp(), mpc1.getRmp(), DELTA);

        MagPlasmaCalculated mpc2 = mpcl.get(0);
        assertEquals(me.getBxGsm(), mpc2.getBxGsm(), DELTA);
        assertEquals(me.getByGsm(), mpc2.getByGsm(), DELTA);
        assertEquals(me.getBzGsm(), mpc2.getBzGsm(), DELTA);
        assertEquals(me.getBt(), mpc2.getBt(), DELTA);
        assertEquals(pe.getDensity(), mpc2.getDensity(), DELTA);
        assertEquals(pe.getSpeed(), mpc2.getSpeed(), DELTA);
        assertEquals(pe.getTemperature(), mpc2.getTemperature(), DELTA);
        assertEquals(cve.getEy(), mpc2.getEy(), DELTA);
        assertEquals(cve.getDpr(), mpc2.getDpr(), DELTA);
        assertEquals(cve.getRmp(), mpc2.getRmp(), DELTA);
    }

    private CalculatedValuesEntity createCalculatedValuesEntity() {
        CalculatedValuesEntity cve = new CalculatedValuesEntity();
        cve.setDpr(1.1);
        cve.setEy(1.2);
        cve.setRmp(1.3);
        cve.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        return cve;
    }

    private PlasmaEntity createPlasmaEntity() {
        PlasmaEntity pe = new PlasmaEntity();
        pe.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        pe.setDensity(1.1);
        pe.setSpeed(1.2);
        pe.setTemperature(1.3);
        return pe;
    }

    private MagEntity createMagEntity() {
        MagEntity me = new MagEntity();
        me.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        me.setBt(1.1);
        me.setBxGsm(1.2);
        me.setByGsm(1.3);
        me.setBzGsm(1.4);
        me.setLatGsm(1.5);
        me.setLonGsm(1.6);
        return me;
    }

}
