package br.inpe.climaespacial.swd.acquisition.repositories;

import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.acquisition.qualifiers.PlasmaLastRecordQualifier;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;
import java.time.ZoneId;
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
@AdditionalClasses({
    DefaultPlasmaLastRecordRepository.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class
})
public class PlasmaLastRecordRepositoryIntegrationTest extends BaseIntegrationTest {

    @Inject
    private EntityManager entityManager;

    @Inject
    @PlasmaLastRecordQualifier
    private LastRecordRepository lastRecordRepository;

    @InRequestScope
    @Test
    public void getLast_called_succeedsWithNullReturn() {
        ZonedDateTime last = lastRecordRepository.getLast();

        assertNull(last);
    }

    @InRequestScope
    @Test
    public void getLast_called_succeedsWithDateTimeResponse() {
        PlasmaEntity pe = createPlasmaEntity();
        entityManager.persist(pe);

        ZonedDateTime last = lastRecordRepository.getLast();

        assertNotNull(last);
        assertEquals(pe.getTimeTag(), last);
    }

    private PlasmaEntity createPlasmaEntity() {
        PlasmaEntity pe = new PlasmaEntity();
        pe.setId(UUID.randomUUID());
        pe.setCreationDate(ZonedDateTime.now());
        pe.setModificationDate(ZonedDateTime.now(ZoneId.of("UTC")));
        pe.setTimeTag(ZonedDateTime.parse("2017-01-01T13:00:00.000z[UTC]"));
        pe.setDensity(2.0);
        pe.setSpeed(3.0);
        pe.setTemperature(4.0);
        return pe;
    }

}
