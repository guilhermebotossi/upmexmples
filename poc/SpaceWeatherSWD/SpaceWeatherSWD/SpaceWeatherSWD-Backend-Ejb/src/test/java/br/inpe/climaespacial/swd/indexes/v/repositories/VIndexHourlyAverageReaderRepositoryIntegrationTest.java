package br.inpe.climaespacial.swd.indexes.v.repositories;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.utils.DefaultCollectionUtils;
import br.inpe.climaespacial.swd.indexes.FirstHourlyAverageToCalculateIndexesReaderRepository;
import br.inpe.climaespacial.swd.indexes.b.factories.DefaultHourlyAverageFactory;
import br.inpe.climaespacial.swd.indexes.b.mappers.DefaultHourlyAverageMapper;
import br.inpe.climaespacial.swd.indexes.v.helpers.DefaultHourlyAverageDataFillerHelper;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
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
    DefaultVIndexHourlyAverageReaderRepository.class,
    DefaultHourlyAverageFactory.class,
    DefaultHourlyAverageMapper.class,
    DefaultHourlyAverageDataFillerHelper.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class,
    DefaultCollectionUtils.class
})
public class VIndexHourlyAverageReaderRepositoryIntegrationTest extends BaseIntegrationTest {

    private final ZonedDateTime zonedDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

    private final int maxSize = 26;

    private final int lastPosition = maxSize - 1;

    @Produces
    @Mock
    private VIndexReaderRepository vIndexReaderRepository;

    @Produces
    @Mock
    private FirstHourlyAverageToCalculateIndexesReaderRepository firstHourlyAverageToCalculateIndexesReaderRepository;

    @Inject
    private EntityManager entityManager;

    @Inject
    private VIndexHourlyAverageReaderRepository vIndexHourlyAverageReaderRepository;

    @Test
    @InRequestScope
    public void getHourlyAverage_called_returnsHourlyAverageFromLastCalculatedVIndex() {
        when(vIndexReaderRepository.getNextHourToBeCalculated()).thenReturn(zonedDateTime);
        HourlyAverageEntity hae = create(zonedDateTime);
        entityManager.persist(hae);

        List<HourlyAverage> hal = vIndexHourlyAverageReaderRepository.getHourlyAverages();

        assertNotNull(hal);
        assertEquals(maxSize, hal.size());
        HourlyAverage ha = hal.get(lastPosition);
        assertEquals(hae.getTimeTag(), ha.getTimeTag());
    }

    @Test
    @InRequestScope
    public void getHourlyAverage_called_returnsHourlyAverageFromFirstHourlyAverageToCalculateIndexesReaderRepository() {

        when(firstHourlyAverageToCalculateIndexesReaderRepository.getFirstHour()).thenReturn(zonedDateTime);
        HourlyAverageEntity hae = create(zonedDateTime);
        entityManager.persist(hae);

        List<HourlyAverage> hal = vIndexHourlyAverageReaderRepository.getHourlyAverages();

        assertNotNull(hal);
        assertEquals(maxSize, hal.size());
        HourlyAverage ha = hal.get(lastPosition);
        assertEquals(hae.getTimeTag(), ha.getTimeTag());
    }

    @Test
    @InRequestScope
    public void getHourlyAverage_called_lastRecords() {
        when(vIndexReaderRepository.getNextHourToBeCalculated()).thenReturn(zonedDateTime);

        List<HourlyAverageEntity> hael1 = new ArrayList<HourlyAverageEntity>();

        for (int i = 0; i <= maxSize; i++) {
            HourlyAverageEntity hae = create(zonedDateTime.minusHours(i));
            entityManager.persist(hae);
            hael1.add(0, hae);
        }

        List<HourlyAverage> hal = vIndexHourlyAverageReaderRepository.getHourlyAverages();
        
        assertNotNull(hal);
        assertEquals(maxSize, hal.size());

        for (int i = 0; i < maxSize; i++) {
            HourlyAverageEntity hae = hael1.get(i + 1);
            HourlyAverage ha = hal.get(i);
            assertEquals(hae.getTimeTag(), ha.getTimeTag());
        }
    }

    private HourlyAverageEntity create(ZonedDateTime zonedDateTime) {
        UUID id = UUID.randomUUID();
        HourlyAverageEntity hae = new HourlyAverageEntity();
        hae.setId(id);
        hae.setCreationDate(zonedDateTime);
        hae.setModificationDate(zonedDateTime);
        hae.setTimeTag(zonedDateTime);
        return hae;
    }

}
