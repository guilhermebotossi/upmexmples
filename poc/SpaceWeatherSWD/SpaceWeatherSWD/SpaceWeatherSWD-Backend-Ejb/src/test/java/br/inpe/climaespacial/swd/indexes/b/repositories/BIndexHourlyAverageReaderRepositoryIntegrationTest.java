package br.inpe.climaespacial.swd.indexes.b.repositories;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.indexes.FirstHourlyAverageToCalculateIndexesReaderRepository;
import br.inpe.climaespacial.swd.indexes.b.factories.DefaultHourlyAverageFactory;
import br.inpe.climaespacial.swd.indexes.b.mappers.DefaultHourlyAverageMapper;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultListFactory.class,
    DefaultBIndexHourlyAverageReaderRepository.class,
    DefaultHourlyAverageFactory.class,
    DefaultHourlyAverageMapper.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class
})
public class BIndexHourlyAverageReaderRepositoryIntegrationTest extends BaseIntegrationTest {

    private final ZonedDateTime zonedDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

    @Produces
    @Mock
    private BIndexReaderRepository bIndexReaderRepository;

    @Produces
    @Mock
    private FirstHourlyAverageToCalculateIndexesReaderRepository firstHourlyAverageToCalculateIndexesReaderRepository;

    @Inject
    private EntityManager entityManager;

    @Inject
    private BIndexHourlyAverageReaderRepository bIndexHourlyAverageReaderRepository;

    @Test
    @InRequestScope
    public void getHourlyAverage_called_returnsHourlyAverageFromLastCalculatedBIndex() {
        when(bIndexReaderRepository.getNextHourToBeCalculated()).thenReturn(zonedDateTime);
        HourlyAverageEntity hae = create();
        entityManager.persist(hae);

        HourlyAverage ha = bIndexHourlyAverageReaderRepository.getHourlyAverage();

        assertNotNull(hae);
        assertEquals(hae.getTimeTag(), ha.getTimeTag());
    }

    @Test
    @InRequestScope
    public void getHourlyAverage_called_returnsHourlyAverageFromFirstHourlyAverageToCalculateIndexesReaderRepository() {
        when(firstHourlyAverageToCalculateIndexesReaderRepository.getFirstHour()).thenReturn(zonedDateTime);
        HourlyAverageEntity hae = create();
        entityManager.persist(hae);

        HourlyAverage ha = bIndexHourlyAverageReaderRepository.getHourlyAverage();

        assertNotNull(hae);
        assertEquals(hae.getTimeTag(), ha.getTimeTag());
    }

    private HourlyAverageEntity create() {
        UUID id = UUID.fromString("672e0316-df1f-4589-a1fe-9b8abc1fc286");
        HourlyAverageEntity hae = new HourlyAverageEntity();
        hae.setId(id);
        hae.setCreationDate(zonedDateTime);
        hae.setModificationDate(zonedDateTime);
        hae.setTimeTag(zonedDateTime);
        return hae;
    }
}
