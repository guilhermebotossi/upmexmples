package br.inpe.climaespacial.swd.values.dpr.average.repositories;

import br.inpe.climaespacial.swd.home.validators.DefaultIntervalValidator;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;
import br.inpe.climaespacial.swd.values.dpr.average.dtos.AverageDPR;
import br.inpe.climaespacial.swd.values.dpr.average.factories.DefaultAverageDPRFactory;
import br.inpe.climaespacial.swd.values.dpr.average.mappers.DefaultAverageDPRMapper;
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
    DefaultAverageDPRRepository.class,
    DefaultAverageDPRMapper.class,
    DefaultAverageDPRFactory.class
})
public class AverageDPRRepositoryIntegrationTest extends BaseIntegrationTest {

    private final ZonedDateTime initialDateTime = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
    private final ZonedDateTime finalDateTime = ZonedDateTime.parse("2017-01-02T00:00:00z[UTC]");

    @Inject
    private EntityManager entityManager;

    @Inject
    private AverageDPRRepository averageDPRRepository;

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

        List<AverageDPR> adprl = averageDPRRepository.list(initialDateTime, finalDateTime);

        assertNotNull(adprl);
        assertThat(adprl, is(not(empty())));
        assertThat(adprl, hasSize(2));
        AverageDPR adpr1 = adprl.get(0);
        assertEquals(zdt1, adpr1.getTimeTag());
        assertEquals(value1, adpr1.getValue());
        AverageDPR adpr2 = adprl.get(1);
        assertEquals(zdt2, adpr2.getTimeTag());
        assertEquals(value2, adpr2.getValue());
    }

    public HourlyAverageEntity createHourlyAverageEntity(Double value, ZonedDateTime timeTag) {
        HourlyAverageEntity hae = new HourlyAverageEntity();

        hae.setId(UUID.randomUUID());
        hae.setCreationDate(ZonedDateTime.now());
        hae.setModificationDate(ZonedDateTime.now());
        hae.setTimeTag(timeTag);
        hae.setDpr(value);

        return hae;
    }
}
