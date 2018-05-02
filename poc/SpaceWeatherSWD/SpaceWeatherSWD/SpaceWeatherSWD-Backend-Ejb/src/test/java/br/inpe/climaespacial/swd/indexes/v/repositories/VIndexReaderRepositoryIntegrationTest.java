package br.inpe.climaespacial.swd.indexes.v.repositories;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.helpers.DefaultDateTimeHelper;
import br.inpe.climaespacial.swd.commons.utils.DefaultCollectionUtils;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.entities.VIndexEntity;
import br.inpe.climaespacial.swd.indexes.v.factories.DefaultVIndexFactory;
import br.inpe.climaespacial.swd.indexes.v.helpers.DefaultVIndexDataFillerHelper;
import br.inpe.climaespacial.swd.indexes.v.mappers.DefaultVIndexMapper;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultVIndexReaderRepository.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class,
    DefaultVIndexMapper.class,
    DefaultVIndexFactory.class,
    DefaultListFactory.class,
    DefaultDateTimeHelper.class,
    DefaultVIndexDataFillerHelper.class,
    DefaultCollectionUtils.class
})
public class VIndexReaderRepositoryIntegrationTest extends BaseIntegrationTest {

    @Inject
    private EntityManager entityManager;

    @Inject
    private VIndexReaderRepository vIndexReaderRepository;

    @InRequestScope
    @Test
    public void getLastCalculatedHour_called_returnsNull() {
        ZonedDateTime lch = vIndexReaderRepository.getNextHourToBeCalculated();

        assertNull(lch);
    }

    @InRequestScope
    @Test
    public void getLastCalculatedHour_called_returnsLastTimeTag() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00Z[UTC]");
        final VIndexEntity vie1 = createVIndexEntity(zdt1);
        entityManager.persist(vie1);

        ZonedDateTime zdt2 = ZonedDateTime.parse("2016-01-01T12:00:00Z[UTC]");
        final VIndexEntity vie = createVIndexEntity(zdt2);
        entityManager.persist(vie);

        ZonedDateTime lch = vIndexReaderRepository.getNextHourToBeCalculated();

        assertNotNull(lch);
        assertEquals(zdt1.plusHours(1), lch);
    }

    @InRequestScope
    @Test
    public void listByPeriod_called_returnsListEmpty() {
        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-01T12:00:00Z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-03T12:00:00Z[UTC]");

        List<VIndex> vil = vIndexReaderRepository.listByPeriod(ffn, nfn);

        assertNotNull(vil);
        assertEquals(0, vil.size());
    }

    @InRequestScope
    @Test
    public void listByPeriod_calledWithDurationSmallerThan2Days_returnsList() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-02T00:00:00Z[UTC]");
        VIndexEntity vie1 = createVIndexEntity(zdt1);
        entityManager.persist(vie1);

        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-02T23:00:00Z[UTC]");
        VIndexEntity vie2 = createVIndexEntity(zdt2);
        entityManager.persist(vie2);

        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-03T00:00:00Z[UTC]");
        VIndexEntity vie3 = createVIndexEntity(zdt3);
        entityManager.persist(vie3);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-02T00:00:00Z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-03T00:00:00Z[UTC]");

        List<VIndex> vil = vIndexReaderRepository.listByPeriod(ffn, nfn);

        assertNotNull(vil);
        assertEquals(2, vil.size());
        assertEquals(zdt1, vil.get(0).getTimeTag());
        assertEquals(zdt2, vil.get(1).getTimeTag());
    }

    @InRequestScope
    @Test
    public void listByPeriod_calledWithDurationBiggerThan2Days_returnsList() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-02T00:00:00Z[UTC]");
        VIndexEntity vie1 = createVIndexEntity(zdt1);
        entityManager.persist(vie1);

        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-05T23:00:00Z[UTC]");
        VIndexEntity vie2 = createVIndexEntity(zdt2);
        entityManager.persist(vie2);

        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-06T00:00:00Z[UTC]");
        VIndexEntity vie3 = createVIndexEntity(zdt3);
        entityManager.persist(vie3);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-02T00:00:00Z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-06T00:00:00Z[UTC]");

        List<VIndex> vil = vIndexReaderRepository.listByPeriod(ffn, nfn);

        assertNotNull(vil);
        assertEquals(2, vil.size());
        assertEquals(zdt1, vil.get(0).getTimeTag());
        assertEquals(zdt2, vil.get(1).getTimeTag());
    }

    @InRequestScope
    @Test
    public void getLastVIndexesFromDateTime_called_succeedsFilledList() {

        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T00:00:00Z[UTC]");
        ZonedDateTime zdtFirst = zdt.plusHours(1);
        for (int i = 0; i < 25; i++) {
            VIndexEntity vie = createVIndexEntity(zdt);
            entityManager.persist(vie);
            zdt = zdt.plusHours(1);
        }

        List<VIndex> vil = vIndexReaderRepository.getLastVIndexesFromDateTime(zdt);

        assertNotNull(vil);
        assertThat(vil, hasSize(24));

        for (int i = 0; i < 24; i++) {
            VIndex vi = vil.get(i);
            assertNotNull(vi);
            assertEquals(zdtFirst, vi.getTimeTag());
            zdtFirst = zdtFirst.plusHours(1);
        }
    }

    private VIndexEntity createVIndexEntity(ZonedDateTime lastTimeTag) {
        VIndexEntity vie = new VIndexEntity();
        vie.setId(UUID.randomUUID());
        vie.setCreationDate(ZonedDateTime.now());
        vie.setModificationDate(ZonedDateTime.now());
        vie.setTimeTag(lastTimeTag);
        vie.setPreValue(1.0);
        vie.setPostValue(2.0);
        vie.setIsCycleBegin(true);
        return vie;
    }

}
