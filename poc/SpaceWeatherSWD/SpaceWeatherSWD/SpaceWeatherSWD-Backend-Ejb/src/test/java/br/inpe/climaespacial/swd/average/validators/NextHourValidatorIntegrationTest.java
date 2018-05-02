package br.inpe.climaespacial.swd.average.validators;

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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultNextHourValidator.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class})
public class NextHourValidatorIntegrationTest extends BaseIntegrationTest {

    @Inject
    private EntityManager entityManager;

    @Inject
    private NextHourValidator nextHourValidator;

    @Test
    @InRequestScope
    public void validate_called_returnsFalse() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        entityManager.persist(createMag());
        entityManager.persist(createPlasma());
        entityManager.persist(createCalculatedValuesEntity());

        boolean validate = nextHourValidator.validate(zdt);

        assertFalse(validate);
    }

    @Test
    @InRequestScope
    public void validate_called_returnsTrue() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        entityManager.persist(createMag());
        entityManager.persist(createPlasma());
        entityManager.persist(createCalculatedValuesEntity());

        MagEntity me = createMag();
        me.setTimeTag(ZonedDateTime.parse("2017-01-01T13:00:00z[UTC]"));
        entityManager.persist(me);
        PlasmaEntity pe = createPlasma();
        pe.setTimeTag(ZonedDateTime.parse("2017-01-01T13:00:00z[UTC]"));
        entityManager.persist(pe);
        CalculatedValuesEntity cve = createCalculatedValuesEntity();
        cve.setTimeTag(ZonedDateTime.parse("2017-01-01T13:00:00z[UTC]"));
        entityManager.persist(cve);

        boolean validate = nextHourValidator.validate(zdt);

        assertTrue(validate);
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
        mag.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
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
        plasma.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        return plasma;
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

}
