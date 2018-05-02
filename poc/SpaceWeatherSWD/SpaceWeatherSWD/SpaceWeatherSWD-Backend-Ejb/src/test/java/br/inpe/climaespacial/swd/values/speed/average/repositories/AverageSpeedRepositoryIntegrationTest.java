package br.inpe.climaespacial.swd.values.speed.average.repositories;

import br.inpe.climaespacial.swd.home.validators.DefaultIntervalValidator;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;
import br.inpe.climaespacial.swd.values.speed.average.dtos.AverageSpeed;
import br.inpe.climaespacial.swd.values.speed.average.factories.DefaultAverageSpeedFactory;
import br.inpe.climaespacial.swd.values.speed.average.mappers.DefaultAverageSpeedMapper;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class,
    HelperFactoryProducer.class,
    DefaultListFactory.class,
    DefaultIntervalValidator.class,
    DefaultAverageSpeedRepository.class,
    DefaultAverageSpeedMapper.class,
    DefaultAverageSpeedFactory.class
})
public class AverageSpeedRepositoryIntegrationTest extends BaseIntegrationTest {

    private final ZonedDateTime initialDateTime = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
    private final ZonedDateTime finalDateTime = ZonedDateTime.parse("2017-01-02T00:00:00z[UTC]");

    @Inject
    private EntityManager entityManager; 

    @Inject
    private AverageSpeedRepository averageSpeedRepository;

    @Test
    @InRequestScope
    public void list_called_succeeds() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        Double value1 = 1.0;
        entityManager.persist(createHourlyAverageEntity(value1, zdt1));

        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T23:00:00z[UTC]");
        Double value2 = 2.0;
        entityManager.persist(createHourlyAverageEntity(value2, zdt2));
        
        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-02T00:00:00z[UTC]");
        Double value3 = 3.0;
        entityManager.persist(createHourlyAverageEntity(value3, zdt3));

        List<AverageSpeed> asl = averageSpeedRepository.list(initialDateTime, finalDateTime);

        assertNotNull(asl);
        assertThat(asl, is(not(empty())));
        assertThat(asl, hasSize(2));
        AverageSpeed as1 = asl.get(0);
        assertEquals(zdt1, as1.getTimeTag());
        assertEquals(value1, as1.getValue());
        AverageSpeed as2 = asl.get(1);
        assertEquals(zdt2, as2.getTimeTag());
        assertEquals(value2, as2.getValue());
    }
    
    public HourlyAverageEntity createHourlyAverageEntity(Double value, ZonedDateTime timeTag) {
        HourlyAverageEntity hae = new HourlyAverageEntity();

        hae.setId(UUID.randomUUID());
        hae.setCreationDate(ZonedDateTime.now());
        hae.setModificationDate(ZonedDateTime.now());
        hae.setTimeTag(timeTag);
        hae.setSpeed(value);

        return hae;
    }

}
