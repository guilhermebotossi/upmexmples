package br.inpe.climaespacial.swd.average;

import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.average.mappers.DefaultMagPlasmaCalculatedMapper;
import br.inpe.climaespacial.swd.average.providers.NextHourProvider;
import br.inpe.climaespacial.swd.average.repositories.DefaultMagPlasmaCalculatedReaderRepository;
import br.inpe.climaespacial.swd.average.repositories.MagPlasmaCalculatedReaderRepository;
import br.inpe.climaespacial.swd.calculation.dtos.MagPlasmaCalculated;
import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;
import br.inpe.climaespacial.swd.calculation.factories.DefaultMagPlasmaCalculatedFactory;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.DefaultSimpleEntryFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;
import java.time.ZonedDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.UUID;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultMagPlasmaCalculatedReaderRepository.class,
    DefaultMagPlasmaCalculatedMapper.class,
    DefaultListFactory.class,
    DefaultMagPlasmaCalculatedFactory.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class,
    DefaultSimpleEntryFactory.class
})
public class MagPlasmaCalculatedReaderRepositoryIntegrationTest extends BaseIntegrationTest {

    @Inject
    private EntityManager entityManager;

    @Produces
    @Mock
    private NextHourProvider nextHour;

    @Inject
    private MagPlasmaCalculatedReaderRepository magPlasmaCalculatedReaderRepository;

    @InRequestScope
    @Test
    public void list_called_returnedList() {
        MagEntity me = createMagEntity();
        entityManager.persist(me);
        PlasmaEntity pe = createPlasmaEntity();
        entityManager.persist(pe);
        CalculatedValuesEntity cve = createCalculatedValuesEntity();
        entityManager.persist(cve);

        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        when(nextHour.getNextHour()).thenReturn(zdt);

        SimpleEntry<ZonedDateTime, List<MagPlasmaCalculated>> se = magPlasmaCalculatedReaderRepository.list();

        assertNotNull(se);
        assertNotNull(se.getValue());
        List<MagPlasmaCalculated> mpcl = se.getValue();
        assertNotNull(mpcl);
        assertThat(mpcl, is(not(empty())));
        MagPlasmaCalculated mpc = mpcl.get(0);
        assertEquals(me.getBt(), mpc.getBt(), 0);
        assertEquals(me.getBxGsm(), mpc.getBxGsm(), 0);
        assertEquals(me.getByGsm(), mpc.getByGsm(), 0);
        assertEquals(me.getBzGsm(), mpc.getBzGsm(), 0);
        assertEquals(pe.getDensity(), mpc.getDensity(), 0);
        assertEquals(pe.getSpeed(), mpc.getSpeed(), 0);
        assertEquals(pe.getTemperature(), mpc.getTemperature(), 0);
        assertEquals(cve.getEy(), mpc.getEy(), 0);
        assertEquals(cve.getDpr(), mpc.getDpr(), 0);
        assertEquals(cve.getRmp(), mpc.getRmp(), 0);
    }

    @InRequestScope
    @Test
    public void list_called_returnedSimpleEntryWithNullDate() {
        when(nextHour.getNextHour()).thenReturn(null);

        SimpleEntry<ZonedDateTime, List<MagPlasmaCalculated>> se = magPlasmaCalculatedReaderRepository.list();

        assertNotNull(se);
        assertNull(se.getKey());
        assertNotNull(se.getValue());
        List<MagPlasmaCalculated> mpcl = se.getValue();
        assertNotNull(mpcl);
        assertThat(mpcl, is(empty()));
    }

    private MagEntity createMagEntity() {
        MagEntity me = new MagEntity();
        me.setId(UUID.randomUUID());
        me.setCreationDate(ZonedDateTime.now());
        me.setModificationDate(ZonedDateTime.now());
        me.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        me.setBt(1D);
        me.setBxGsm(2D);
        me.setByGsm(3D);
        me.setBzGsm(5D);
        me.setLatGsm(6D);
        me.setLonGsm(7D);
        return me;
    }

    private PlasmaEntity createPlasmaEntity() {
        PlasmaEntity pe = new PlasmaEntity();
        pe.setId(UUID.randomUUID());
        pe.setCreationDate(ZonedDateTime.now());
        pe.setModificationDate(ZonedDateTime.now());
        pe.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00.000z[UTC]"));
        pe.setDensity(8D);
        pe.setSpeed(9D);
        pe.setTemperature(10D);
        return pe;
    }

    private CalculatedValuesEntity createCalculatedValuesEntity() {
        CalculatedValuesEntity cve = new CalculatedValuesEntity();
        cve.setId(UUID.randomUUID());
        cve.setCreationDate(ZonedDateTime.now());
        cve.setModificationDate(ZonedDateTime.now());
        cve.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00.000z[UTC]"));
        cve.setEy(11D);
        cve.setDpr(12D);
        cve.setRmp(13D);
        return cve;
    }
}
