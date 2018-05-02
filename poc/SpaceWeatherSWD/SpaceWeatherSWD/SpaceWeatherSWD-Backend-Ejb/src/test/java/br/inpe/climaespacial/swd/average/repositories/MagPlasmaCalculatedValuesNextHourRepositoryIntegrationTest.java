package br.inpe.climaespacial.swd.average.repositories;

import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultMagPlasmaCalculatedValuesNextHourRepository.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class})
public class MagPlasmaCalculatedValuesNextHourRepositoryIntegrationTest extends BaseIntegrationTest {

    @Inject
    private EntityManager entityManager;

    @Inject
    private MagPlasmaCalculatedValuesNextHourRepository magPlasmaNextHourRepository;

    @Test
    @InRequestScope
    public void getNextHour_called_returnsNull() {
        ZonedDateTime nextHour = magPlasmaNextHourRepository.getNextHour();

        assertNull(nextHour);
    }

    @Test
    @InRequestScope
    public void getNextHour_called_returnsDateTime() {
        entityManager.persist(createMag());
        entityManager.persist(createPlasma());
        entityManager.persist(createCalculatedValuesEntity());

        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00Z[UTC]");

        MagEntity me = createMag();
        me.setTimeTag(ZonedDateTime.parse("2017-01-01T13:00:00Z[UTC]"));
        entityManager.persist(me);

        PlasmaEntity pe = createPlasma();
        pe.setTimeTag(ZonedDateTime.parse("2017-01-01T13:00:00Z[UTC]"));
        entityManager.persist(pe);

        CalculatedValuesEntity cve = createCalculatedValuesEntity();
        cve.setTimeTag(ZonedDateTime.parse("2017-01-01T13:00:00Z[UTC]"));
        entityManager.persist(cve);

        ZonedDateTime nextHour = magPlasmaNextHourRepository.getNextHour();

        assertNotNull(nextHour);
        assertEquals(zdt, nextHour);
    }

    private MagEntity createMag() {
        MagEntity mag = new MagEntity();
        mag.setId(UUID.randomUUID());
        mag.setCreationDate(ZonedDateTime.now());
        mag.setModificationDate(ZonedDateTime.now());
        mag.setBt(2.1);
        mag.setBxGsm(2.2);
        mag.setByGsm(2.3);
        mag.setBzGsm(2.4);
        mag.setLatGsm(2.5);
        mag.setLonGsm(2.6);
        mag.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00Z[UTC]"));
        return mag;
    }

    private PlasmaEntity createPlasma() {
        PlasmaEntity plasma = new PlasmaEntity();
        plasma.setId(UUID.randomUUID());
        plasma.setCreationDate(ZonedDateTime.now());
        plasma.setModificationDate(ZonedDateTime.now());
        plasma.setDensity(2.1);
        plasma.setSpeed(2.1);
        plasma.setTemperature(2.1);
        plasma.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00Z[UTC]"));
        return plasma;
    }

    private CalculatedValuesEntity createCalculatedValuesEntity() {
        CalculatedValuesEntity cve = new CalculatedValuesEntity();
        cve.setId(UUID.randomUUID());
        cve.setCreationDate(ZonedDateTime.now());
        cve.setModificationDate(ZonedDateTime.now());
        cve.setDpr(1.0);
        cve.setEy(6.0);
        cve.setRmp(2.0);
        cve.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00Z[UTC]"));
        return cve;
    }

}
