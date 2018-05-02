package br.inpe.climaespacial.swd.indexes.z.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.utils.DefaultCollectionUtils;
import br.inpe.climaespacial.swd.indexes.FirstHourlyAverageToCalculateIndexesReaderRepository;
import br.inpe.climaespacial.swd.indexes.b.factories.DefaultHourlyAverageFactory;
import br.inpe.climaespacial.swd.indexes.b.mappers.DefaultHourlyAverageMapper;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultListFactory.class,
    DefaultZIndexHourlyAverageReaderRepository.class,
    DefaultHourlyAverageFactory.class,
    DefaultHourlyAverageMapper.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class,
    DefaultCollectionUtils.class
})
public class ZIndexHourlyAverageReaderRepositoryIntegrationTest extends BaseIntegrationTest {

    private final ZonedDateTime zonedDateTime = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

    private final int maxSize = 3;

    @Produces
    @Mock
    private ZIndexReaderRepository zIndexReaderRepository;

    @Produces
    @Mock
    private FirstHourlyAverageToCalculateIndexesReaderRepository firstHourlyAverageToCalculateIndexesReaderRepository;

    @Inject
    private EntityManager entityManager;

    @Inject
    private ZIndexHourlyAverageReaderRepository zIndexHourlyAverageReaderRepository;

    @Test
    @InRequestScope
    public void getHourlyAverage_called_returnsHourlyAverageFromLastCalculatedZIndex() {
        when(zIndexReaderRepository.getNextHourToBeCalculated()).thenReturn(zonedDateTime);
        HourlyAverageEntity hae = create(zonedDateTime);
        entityManager.persist(hae);

        List<HourlyAverage> hal = zIndexHourlyAverageReaderRepository.getHourlyAverages();

        assertNotNull(hal);
        assertEquals(1, hal.size());
        HourlyAverage ha = hal.get(0);
        assertEquals(hae.getTimeTag(), ha.getTimeTag());
    }

    @Test
    @InRequestScope
    public void getHourlyAverage_called_returnsHourlyAverageFromFirstHourlyAverageToCalculateIndexesReaderRepository() {
        when(firstHourlyAverageToCalculateIndexesReaderRepository.getFirstHour()).thenReturn(zonedDateTime);
        HourlyAverageEntity hae = create(zonedDateTime);
        entityManager.persist(hae);

        List<HourlyAverage> hal = zIndexHourlyAverageReaderRepository.getHourlyAverages();

        assertNotNull(hal);
        assertEquals(1, hal.size());
        HourlyAverage ha = hal.get(0);
        assertEquals(hae.getTimeTag(), ha.getTimeTag());
    }

    @Test
    @InRequestScope
    public void getHourlyAverage_called_lastRecords() {
        when(zIndexReaderRepository.getNextHourToBeCalculated()).thenReturn(zonedDateTime);

        List<HourlyAverageEntity> hael1 = new ArrayList<HourlyAverageEntity>();

        for (int i = 0; i <= maxSize; i++) {
            HourlyAverageEntity hae = create(zonedDateTime.minusHours(i));
            entityManager.persist(hae);
            hael1.add(0, hae);
        }

        List<HourlyAverage> hal = zIndexHourlyAverageReaderRepository.getHourlyAverages();
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
