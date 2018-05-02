package br.inpe.climaespacial.swd.average;

import br.inpe.climaespacial.swd.acquisition.providers.DefaultDateTimeProvider;
import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import br.inpe.climaespacial.swd.average.factories.DefaultHourlyAverageEntityFactory;
import br.inpe.climaespacial.swd.average.mappers.DefaultHourlyAverageEntityMapper;
import br.inpe.climaespacial.swd.average.repositories.DefaultHourlyAverageWriterRepository;
import br.inpe.climaespacial.swd.average.repositories.HourlyAverageWriterRepository;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.DefaultUUIDProvider;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.CreateTestRecords;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultHourlyAverageWriterRepository.class,
    DefaultHourlyAverageEntityMapper.class,
    DefaultHourlyAverageEntityFactory.class,
    DefaultDateTimeProvider.class,
    DefaultUUIDProvider.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class
})
public class HourlyAverageWriterRepositoryIntegrationTest extends BaseIntegrationTest {

    private CreateTestRecords createRecords = new CreateTestRecords();

    @Inject
    private EntityManager entityManager;

    @Inject
    private HourlyAverageWriterRepository hourlyAverageWriterRepository;

    @InRequestScope
    @Test
    public void save_called_saves() {

        HourlyAverage hourlyAverage = createRecords.createHourlyAverage();

        hourlyAverageWriterRepository.save(hourlyAverage);

        TypedQuery<HourlyAverageEntity> q = entityManager.createQuery("SELECT hae FROM HourlyAverageEntity hae", HourlyAverageEntity.class);
        HourlyAverageEntity hourlyAverageEntity = q.getSingleResult();
        assertNotNull(hourlyAverageEntity);
    }

}
