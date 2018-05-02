package br.inpe.climaespacial.swd.indexes.b.repositories;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.helpers.DefaultDateTimeHelper;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.b.entities.BIndexEntity;
import br.inpe.climaespacial.swd.indexes.b.factories.DefaultBIndexFactory;
import br.inpe.climaespacial.swd.indexes.b.mappers.DefaultBIndexMapper;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;
import java.time.ZonedDateTime;
import java.util.List;
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
    HelperFactoryProducer.class,
    DefaultBIndexReaderRepository.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class,
    DefaultBIndexMapper.class,
    DefaultBIndexFactory.class,
    DefaultListFactory.class,
    DefaultDateTimeHelper.class
})
public class BIndexReaderRepositoryIntegrationTest extends BaseIntegrationTest {

    private static final double VALUE = 1.0;

    @Inject
    private EntityManager entityManager;

    @Inject
    private BIndexReaderRepository bIndexReaderRepository;

    @InRequestScope
    @Test
    public void getLastCalculatedHour_called_returnsNull() {
        ZonedDateTime nhtbc = bIndexReaderRepository.getNextHourToBeCalculated();

        assertNull(nhtbc);
    }

    @InRequestScope
    @Test
    public void getLastCalculatedHour_called_returnsLastTimeTag() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00Z[UTC]");
        BIndexEntity bie1 = createBIndexEntity(zdt1);
        entityManager.persist(bie1);

        ZonedDateTime zdt2 = ZonedDateTime.parse("2016-01-01T12:00:00Z[UTC]");
        BIndexEntity bie2 = createBIndexEntity(zdt2);
        entityManager.persist(bie2);

        ZonedDateTime nhtbc = bIndexReaderRepository.getNextHourToBeCalculated();

        assertNotNull(nhtbc);
        assertEquals(zdt1.plusHours(1), nhtbc);
    }

    @InRequestScope
    @Test
    public void listByPeriod_called_returnsListEmpty() {

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-01T12:00:00Z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-03T12:00:00Z[UTC]");

        List<BIndex> bil = bIndexReaderRepository.listByPeriod(ffn, nfn);

        assertNotNull(bil);
        assertEquals(0, bil.size());
    }

    @InRequestScope
    @Test
    public void listByPeriod_calledWithDurationSmallerThan3Days_returnsList() {

        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-02T00:00:00Z[UTC]");
        BIndexEntity bie1 = createBIndexEntity(zdt1);
        entityManager.persist(bie1);

        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-02T23:00:00Z[UTC]");
        BIndexEntity bie2 = createBIndexEntity(zdt2);
        entityManager.persist(bie2);

        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-03T00:00:00Z[UTC]");
        BIndexEntity bie3 = createBIndexEntity(zdt3);
        entityManager.persist(bie3);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-02T00:00:00Z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-03T00:00:00Z[UTC]");

        List<BIndex> bil = bIndexReaderRepository.listByPeriod(ffn, nfn);

        assertNotNull(bil);
        assertEquals(2, bil.size());
        assertEquals(zdt1, bil.get(0).getTimeTag());
        assertEquals(zdt2, bil.get(1).getTimeTag());
    }

    @InRequestScope
    @Test
    public void listByPeriod_calledWithDurationBiggerThan3Days_returnsList() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-02T00:00:00Z[UTC]");
        BIndexEntity bie1 = createBIndexEntity(zdt1);
        entityManager.persist(bie1);

        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-05T23:00:00Z[UTC]");
        BIndexEntity bie2 = createBIndexEntity(zdt2);
        entityManager.persist(bie2);

        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-06T00:00:00Z[UTC]");
        BIndexEntity bie3 = createBIndexEntity(zdt3);
        entityManager.persist(bie3);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-02T00:00:00Z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-06T00:00:00Z[UTC]");

        List<BIndex> bil = bIndexReaderRepository.listByPeriod(ffn, nfn);

        assertNotNull(bil);
        assertEquals(2, bil.size());
        assertEquals(zdt1, bil.get(0).getTimeTag());
        assertEquals(zdt2, bil.get(1).getTimeTag());
    }

    private BIndexEntity createBIndexEntity(ZonedDateTime zonedDateTime) {
        BIndexEntity bie = new BIndexEntity();
        bie.setId(UUID.randomUUID());
        bie.setCreationDate(zonedDateTime);
        bie.setModificationDate(zonedDateTime);
        bie.setTimeTag(zonedDateTime);
        bie.setPreValue(VALUE);
        bie.setPostValue(VALUE);
        return bie;
    }

}
