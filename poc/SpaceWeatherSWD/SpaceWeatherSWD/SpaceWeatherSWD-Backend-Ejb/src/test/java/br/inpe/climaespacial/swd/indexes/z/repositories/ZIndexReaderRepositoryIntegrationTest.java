package br.inpe.climaespacial.swd.indexes.z.repositories;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.helpers.DefaultDateTimeHelper;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import br.inpe.climaespacial.swd.indexes.z.entities.ZIndexEntity;
import br.inpe.climaespacial.swd.indexes.z.factories.DefaultZIndexFactory;
import br.inpe.climaespacial.swd.indexes.z.mappers.DefaultZIndexMapper;
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
    DefaultZIndexReaderRepository.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class,
    DefaultZIndexMapper.class,
    DefaultZIndexFactory.class,
    DefaultListFactory.class,
    DefaultDateTimeHelper.class
})
public class ZIndexReaderRepositoryIntegrationTest extends BaseIntegrationTest {

    private static final double VALUE = 1.0;

    @Inject
    private EntityManager entityManager;

    @Inject
    private ZIndexReaderRepository zIndexReaderRepository;

    @InRequestScope
    @Test
    public void getLastCalculatedHour_called_returnsNull() {
        ZonedDateTime lch = zIndexReaderRepository.getNextHourToBeCalculated();

        assertNull(lch);
    }

    @InRequestScope
    @Test
    public void getLastCalculatedHour_called_returnsLastTimeTag() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00Z[UTC]");
        ZIndexEntity zie1 = createZIndexEntity(zdt1);
        entityManager.persist(zie1);

        ZonedDateTime zdt2 = ZonedDateTime.parse("2016-01-01T12:00:00Z[UTC]");
        ZIndexEntity zie2 = createZIndexEntity(zdt2);
        entityManager.persist(zie2);

        ZonedDateTime lch = zIndexReaderRepository.getNextHourToBeCalculated();

        assertNotNull(lch);
        assertEquals(zdt1.plusHours(1), lch);
    }

    @InRequestScope
    @Test
    public void listByPeriod_called_returnsListEmpty() {

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-01T12:00:00Z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-03T12:00:00Z[UTC]");

        List<ZIndex> zil = zIndexReaderRepository.listByPeriod(ffn, nfn);

        assertNotNull(zil);
        assertEquals(0, zil.size());
    }

    @InRequestScope
    @Test
    public void listByPeriod_calledWithDurationSmallerThan2Days_returnsList() {

        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-02T00:00:00Z[UTC]");
        ZIndexEntity zie1 = createZIndexEntity(zdt1);
        entityManager.persist(zie1);

        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-02T23:00:00Z[UTC]");
        ZIndexEntity zie2 = createZIndexEntity(zdt2);
        entityManager.persist(zie2);

        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-03T00:00:00Z[UTC]");
        ZIndexEntity zie3 = createZIndexEntity(zdt3);
        entityManager.persist(zie3);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-02T00:00:00Z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-03T00:00:00Z[UTC]");

        List<ZIndex> zil = zIndexReaderRepository.listByPeriod(ffn, nfn);

        assertNotNull(zil);
        assertEquals(2, zil.size());
        assertEquals(zdt1, zil.get(0).getTimeTag());
        assertEquals(zdt2, zil.get(1).getTimeTag());
    }

    @InRequestScope
    @Test
    public void listByPeriod_calledWithDurationBiggerThan2Days_returnsList() {

        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-02T00:00:00Z[UTC]");
        ZIndexEntity zie1 = createZIndexEntity(zdt1);
        entityManager.persist(zie1);

        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-05T23:00:00Z[UTC]");
        ZIndexEntity zie2 = createZIndexEntity(zdt2);
        entityManager.persist(zie2);

        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-06T00:00:00Z[UTC]");
        ZIndexEntity zie3 = createZIndexEntity(zdt3);
        entityManager.persist(zie3);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-02T00:00:00Z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-06T00:00:00Z[UTC]");

        List<ZIndex> zil = zIndexReaderRepository.listByPeriod(ffn, nfn);

        assertNotNull(zil);
        assertEquals(2, zil.size());
        assertEquals(zdt1, zil.get(0).getTimeTag());
        assertEquals(zdt2, zil.get(1).getTimeTag());
    }

    private ZIndexEntity createZIndexEntity(ZonedDateTime zonedDateTime) {
        ZIndexEntity zie = new ZIndexEntity();
        zie.setId(UUID.randomUUID());
        zie.setCreationDate(zonedDateTime);
        zie.setModificationDate(zonedDateTime);
        zie.setTimeTag(zonedDateTime);
        zie.setPreValue(VALUE);
        zie.setPostValue(VALUE);
        return zie;
    }

}
