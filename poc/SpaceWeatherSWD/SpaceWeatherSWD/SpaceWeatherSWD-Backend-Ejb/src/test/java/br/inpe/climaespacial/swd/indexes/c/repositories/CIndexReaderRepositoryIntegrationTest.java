package br.inpe.climaespacial.swd.indexes.c.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import br.inpe.climaespacial.swd.indexes.c.factories.DefaultCIndexFactory;
import br.inpe.climaespacial.swd.indexes.c.mappers.DefaultCIndexMapper;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultCIndexReaderRepository.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class,
    DefaultCIndexMapper.class,
    DefaultCIndexFactory.class,
    DefaultListFactory.class,
    DefaultDateTimeHelper.class
})
public class CIndexReaderRepositoryIntegrationTest extends BaseIntegrationTest {

    private static final double VALUE = 1.0;

    @Inject
    private EntityManager entityManager;

    @Inject
    private CIndexReaderRepository cIndexReaderRepository;

    @InRequestScope
    @Test
    public void getLastCalculatedHour_called_returnsNull() {
        ZonedDateTime nhtbc = cIndexReaderRepository.getNextHourToBeCalculated();

        assertNull(nhtbc);
    }

    @InRequestScope
    @Test
    public void getLastCalculatedHour_called_returnsLastTimeTag() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T12:00:00Z[UTC]");
        CIndexEntity cie1 = createCIndexEntity(zdt1);
        entityManager.persist(cie1);

        ZonedDateTime zdt2 = ZonedDateTime.parse("2016-01-01T12:00:00Z[UTC]");
        CIndexEntity cie2 = createCIndexEntity(zdt2);
        entityManager.persist(cie2);

        ZonedDateTime nhtbc = cIndexReaderRepository.getNextHourToBeCalculated();

        assertNotNull(nhtbc);
        assertEquals(zdt1.plusHours(1), nhtbc);
    }

    @InRequestScope
    @Test
    public void listByPeriod_called_returnsListEmpty() {
        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-01T12:00:00Z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-03T12:00:00Z[UTC]");

        List<CIndex> cil = cIndexReaderRepository.listByPeriod(ffn, nfn);

        assertNotNull(cil);
        assertEquals(0, cil.size());
    }

    @InRequestScope
    @Test
    public void listByPeriod_calledWithDurationSmallerThan2Days_returnsList() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-02T00:00:00Z[UTC]");
        CIndexEntity cie1 = createCIndexEntity(zdt1);
        entityManager.persist(cie1);

        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-02T23:00:00Z[UTC]");
        CIndexEntity cie2 = createCIndexEntity(zdt2);
        entityManager.persist(cie2);

        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-03T00:00:00Z[UTC]");
        CIndexEntity cie3 = createCIndexEntity(zdt3);
        entityManager.persist(cie3);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-02T00:00:00Z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-03T00:00:00Z[UTC]");

        List<CIndex> cil = cIndexReaderRepository.listByPeriod(ffn, nfn);

        assertNotNull(cil);
        assertEquals(2, cil.size());
        assertEquals(zdt1, cil.get(0).getTimeTag());
        assertEquals(zdt2, cil.get(1).getTimeTag());
    }

    @InRequestScope
    @Test
    public void listByPeriod_calledWithDurationBiggerThan2Days_returnsList() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-02T00:00:00Z[UTC]");
        CIndexEntity cie1 = createCIndexEntity(zdt1);
        entityManager.persist(cie1);

        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-05T23:00:00Z[UTC]");
        CIndexEntity cie2 = createCIndexEntity(zdt2);
        entityManager.persist(cie2);

        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-06T00:00:00Z[UTC]");
        CIndexEntity cie3 = createCIndexEntity(zdt3);
        entityManager.persist(cie3);

        ZonedDateTime ffn = ZonedDateTime.parse("2017-01-02T00:00:00Z[UTC]");
        ZonedDateTime nfn = ZonedDateTime.parse("2017-01-06T00:00:00Z[UTC]");

        List<CIndex> cil = cIndexReaderRepository.listByPeriod(ffn, nfn);

        assertNotNull(cil);
        assertEquals(2, cil.size());
        assertEquals(zdt1, cil.get(0).getTimeTag());
        assertEquals(zdt2, cil.get(1).getTimeTag());
    }
    

    private CIndexEntity createCIndexEntity(ZonedDateTime zonedDateTime) {
        CIndexEntity cie = new CIndexEntity();
        cie.setId(UUID.randomUUID());
        cie.setCreationDate(zonedDateTime);
        cie.setModificationDate(zonedDateTime);
        cie.setTimeTag(zonedDateTime);
        cie.setPreValue(VALUE);
        cie.setPostValue(VALUE);
        return cie;
    }

}
