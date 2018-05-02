package br.inpe.climaespacial.swd.indexes;

import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
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
@AdditionalClasses({
    DefaultFirstHourlyAverageToCalculateIndexesReaderRepository.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class
})
public class FirstHourlyAverageToCalculateIndexesReaderRepositoryIntegrationTest extends BaseIntegrationTest {    

    @Inject
    private EntityManager entityManager;

    @Inject
    private FirstHourlyAverageToCalculateIndexesReaderRepository firstHourlyAverageToCalculateIndexesReaderRepository;

    @InRequestScope
    @Test
    public void getFirstHour_called_returnsNull() {
        ZonedDateTime lch = firstHourlyAverageToCalculateIndexesReaderRepository.getFirstHour();

        assertNull(lch);
    }

    @InRequestScope
    @Test
    public void getFirstHour_called_returnsFirstTimeTag() {
        
        HourlyAverageEntity hae1 = new HourlyAverageEntity();
        hae1.setId(UUID.randomUUID());
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        hae1.setCreationDate(zdt1);
        hae1.setModificationDate(zdt1);
        hae1.setTimeTag(zdt1);
        entityManager.persist(hae1);
        
        HourlyAverageEntity hae2 = new HourlyAverageEntity();
        hae2.setId(UUID.randomUUID());
        ZonedDateTime zdt2 = ZonedDateTime.parse("2016-01-01T12:00:00z[UTC]");
        hae2.setCreationDate(zdt2);
        hae2.setModificationDate(zdt2);
        hae2.setTimeTag(zdt2);
        entityManager.persist(hae2);

        ZonedDateTime lch = firstHourlyAverageToCalculateIndexesReaderRepository.getFirstHour();

        assertNotNull(lch);
        assertEquals(zdt2, lch);
    }

}
