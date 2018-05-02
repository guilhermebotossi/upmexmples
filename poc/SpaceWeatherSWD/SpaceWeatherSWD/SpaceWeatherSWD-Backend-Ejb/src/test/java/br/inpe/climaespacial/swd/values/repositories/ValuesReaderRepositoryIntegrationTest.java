package br.inpe.climaespacial.swd.values.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.ZonedDateTime;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class,
    HelperFactoryProducer.class,
    DefaultValuesReaderRepository.class
})
public class ValuesReaderRepositoryIntegrationTest extends BaseIntegrationTest {

    @Inject
    private EntityManager entityManager;

    @Inject
    private ValuesReaderRepository valuesReaderRepository;

    @Test
    @InRequestScope
    public void lastValuesDate_calledNoDatabaseValues_returnNull() {
        ZonedDateTime zdt = valuesReaderRepository.lastValuesDate();

        assertNull(zdt);
    }

    @Test
    @InRequestScope
    public void lastValuesDate_calledWithJustMagValue_returnDateTime() {
        MagEntity me = createMagEntity(ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]"));
        entityManager.persist(me);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-03T13:00:00z[UTC]");
        MagEntity me2 = createMagEntity(zdt1);
        entityManager.persist(me2);

        ZonedDateTime zdt2 = valuesReaderRepository.lastValuesDate();

        assertNotNull(zdt2);
        assertEquals(zdt1, zdt2);
    }

    @Test
    @InRequestScope
    public void lastValuesDate_calledWithJustPlasmaValue_returnDateTime() {
        PlasmaEntity pe = createPlasmaEntity(ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]"));
        entityManager.persist(pe);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-03T13:00:00z[UTC]");
        PlasmaEntity pe2 = createPlasmaEntity(zdt1);
        entityManager.persist(pe2);

        ZonedDateTime zdt2 = valuesReaderRepository.lastValuesDate();

        assertNotNull(zdt2);
        assertEquals(zdt1, zdt2);
    }

    @Test
    @InRequestScope
    public void lastValuesDate_calledWithJustCalculatedValue_returnDateTime() {
        CalculatedValuesEntity cve = createCalculatedValuesEntity(ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]"));
        entityManager.persist(cve);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-03T13:00:00z[UTC]");
        CalculatedValuesEntity cve2 = createCalculatedValuesEntity(zdt1);
        entityManager.persist(cve2);

        ZonedDateTime zdt2 = valuesReaderRepository.lastValuesDate();

        assertNotNull(zdt2);
        assertEquals(zdt1, zdt2);
    }

    @Test
    @InRequestScope
    public void lastValuesDate_calledWithAllValues_returnDateTime() {
        MagEntity me = createMagEntity(ZonedDateTime.parse("2017-01-03T12:00:00z[UTC]"));
        entityManager.persist(me);
        MagEntity me2 = createMagEntity(ZonedDateTime.parse("2017-01-03T13:00:00z[UTC]"));
        entityManager.persist(me2);

        PlasmaEntity pe = createPlasmaEntity(ZonedDateTime.parse("2017-01-03T16:00:00z[UTC]"));
        entityManager.persist(pe);
        PlasmaEntity pe2 = createPlasmaEntity(ZonedDateTime.parse("2017-01-03T17:00:00z[UTC]"));
        entityManager.persist(pe2);

        CalculatedValuesEntity cve = createCalculatedValuesEntity(ZonedDateTime.parse("2017-01-03T18:00:00z[UTC]"));
        entityManager.persist(cve);
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-03T19:00:00z[UTC]");
        CalculatedValuesEntity cve2 = createCalculatedValuesEntity(zdt1);
        entityManager.persist(cve2);

        ZonedDateTime zdt2 = valuesReaderRepository.lastValuesDate();

        assertNotNull(zdt2);
        assertEquals(zdt1, zdt2);
    }

    private MagEntity createMagEntity(ZonedDateTime zonedDateTime) {
        MagEntity me = new MagEntity();
        me.setId(UUID.randomUUID());
        me.setCreationDate(ZonedDateTime.now());
        me.setModificationDate(ZonedDateTime.now());
        me.setTimeTag(zonedDateTime);
        me.setBt(2.0);
        return me;
    }

    private PlasmaEntity createPlasmaEntity(ZonedDateTime zonedDateTime) {
        PlasmaEntity pe = new PlasmaEntity();
        pe.setId(UUID.randomUUID());
        pe.setCreationDate(ZonedDateTime.now());
        pe.setModificationDate(ZonedDateTime.now());
        pe.setTimeTag(zonedDateTime);
        pe.setSpeed(2.0);
        return pe;
    }

    private CalculatedValuesEntity createCalculatedValuesEntity(ZonedDateTime zonedDateTime) {
        CalculatedValuesEntity cve = new CalculatedValuesEntity();
        cve.setId(UUID.randomUUID());
        cve.setCreationDate(ZonedDateTime.now());
        cve.setModificationDate(ZonedDateTime.now());
        cve.setTimeTag(zonedDateTime);
        cve.setDpr(2.0);
        return cve;
    }

}
