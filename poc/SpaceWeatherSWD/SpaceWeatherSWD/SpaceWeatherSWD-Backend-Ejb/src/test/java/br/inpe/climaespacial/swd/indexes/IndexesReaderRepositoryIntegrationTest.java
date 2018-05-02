package br.inpe.climaespacial.swd.indexes;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.indexes.b.entities.BIndexEntity;
import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import br.inpe.climaespacial.swd.indexes.v.entities.VIndexEntity;
import br.inpe.climaespacial.swd.indexes.z.entities.ZIndexEntity;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.UUID;
import javax.enterprise.inject.Produces;
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
import org.mockito.Mock;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    DefaultIndexesReaderRepository.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class
})
public class IndexesReaderRepositoryIntegrationTest extends BaseIntegrationTest {

    @Produces
    @Mock
    private ListFactory<ZonedDateTime> zonedDateTimeListFactory;

    @Inject
    private EntityManager entityManager;

    @Inject
    private IndexesReaderRepository indexesReaderRepository;

    @Test
    @InRequestScope
    public void lastIndexesDate_called_returnsNull() {
        when(zonedDateTimeListFactory.create()).thenReturn(new ArrayList<>());

        ZonedDateTime la = indexesReaderRepository.lastIndexesDate();

        assertNull(la);
    }

    @Test
    @InRequestScope
    public void lastIndexesDate_calledWithEmptyDatabase_returnNullAcquisitionDate() {
        when(zonedDateTimeListFactory.create()).thenReturn(new ArrayList<>());

        ZonedDateTime la = indexesReaderRepository.lastIndexesDate();

        assertNull(la);
    }

    @Test
    @InRequestScope
    public void lastIndexesDate_calledWithDatabaseContainingSomeOfTheDates_returnBiggestAcquisitionDate() {
        when(zonedDateTimeListFactory.create()).thenReturn(new ArrayList<>());

        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-10T01:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T02:00:00z[UTC]");
        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]");
        ZonedDateTime zdt4 = ZonedDateTime.parse("2017-01-07T08:00:00z[UTC]");

        ZIndexEntity zi1 = new ZIndexEntity();
        zi1.setId(UUID.randomUUID());
        zi1.setCreationDate(zdt1);
        zi1.setModificationDate(zdt1);
        zi1.setTimeTag(zdt2);
        entityManager.persist(zi1);

        ZIndexEntity zi2 = new ZIndexEntity();
        zi2.setId(UUID.randomUUID());
        zi2.setCreationDate(zdt1);
        zi2.setModificationDate(zdt1);
        zi2.setTimeTag(zdt3);
        entityManager.persist(zi2);

        VIndexEntity vi1 = new VIndexEntity();
        vi1.setId(UUID.randomUUID());
        vi1.setCreationDate(zdt1);
        vi1.setModificationDate(zdt1);
        vi1.setTimeTag(zdt3);
        vi1.setIsCycleBegin(true);
        entityManager.persist(vi1);

        VIndexEntity vi2 = new VIndexEntity();
        vi2.setId(UUID.randomUUID());
        vi2.setCreationDate(zdt1);
        vi2.setModificationDate(zdt1);
        vi2.setTimeTag(zdt4);
        vi2.setIsCycleBegin(true);
        entityManager.persist(vi2);

        ZonedDateTime la = indexesReaderRepository.lastIndexesDate();

        assertNotNull(la);
        assertEquals(zdt4, la);
    }

    @Test
    @InRequestScope
    public void lastIndexesDate_calledWithDatabaseContainingAllDates_returnBiggestAcquisitionDate() {
        when(zonedDateTimeListFactory.create()).thenReturn(new ArrayList<>());

        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-10T01:00:00z[UTC]");
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T02:00:00z[UTC]");
        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-01T03:00:00z[UTC]");
        ZonedDateTime zdt4 = ZonedDateTime.parse("2017-01-07T08:00:00z[UTC]");

        CIndexEntity ci1 = new CIndexEntity();
        ci1.setId(UUID.randomUUID());
        ci1.setCreationDate(zdt1);
        ci1.setModificationDate(zdt1);
        ci1.setTimeTag(zdt2);
        entityManager.persist(ci1);

        CIndexEntity ci2 = new CIndexEntity();
        ci2.setId(UUID.randomUUID());
        ci2.setCreationDate(zdt1);
        ci2.setModificationDate(zdt1);
        ci2.setTimeTag(zdt3);
        entityManager.persist(ci2);

        BIndexEntity bi1 = new BIndexEntity();
        bi1.setId(UUID.randomUUID());
        bi1.setCreationDate(zdt1);
        bi1.setModificationDate(zdt1);
        bi1.setTimeTag(zdt2);
        entityManager.persist(bi1);

        BIndexEntity bi2 = new BIndexEntity();
        bi2.setId(UUID.randomUUID());
        bi2.setCreationDate(zdt1);
        bi2.setModificationDate(zdt1);
        bi2.setTimeTag(zdt3);
        entityManager.persist(bi2);

        ZIndexEntity zi1 = new ZIndexEntity();
        zi1.setId(UUID.randomUUID());
        zi1.setCreationDate(zdt1);
        zi1.setModificationDate(zdt1);
        zi1.setTimeTag(zdt2);
        entityManager.persist(zi1);

        ZIndexEntity zi2 = new ZIndexEntity();
        zi2.setId(UUID.randomUUID());
        zi2.setCreationDate(zdt1);
        zi2.setModificationDate(zdt1);
        zi2.setTimeTag(zdt3);
        entityManager.persist(zi2);

        VIndexEntity vi1 = new VIndexEntity();
        vi1.setId(UUID.randomUUID());
        vi1.setCreationDate(zdt1);
        vi1.setModificationDate(zdt1);
        vi1.setTimeTag(zdt3);
        vi1.setIsCycleBegin(true);
        entityManager.persist(vi1);

        VIndexEntity vi2 = new VIndexEntity();
        vi2.setId(UUID.randomUUID());
        vi2.setCreationDate(zdt1);
        vi2.setModificationDate(zdt1);
        vi2.setTimeTag(zdt4);
        vi2.setIsCycleBegin(true);
        entityManager.persist(vi2);

        ZonedDateTime la = indexesReaderRepository.lastIndexesDate();

        assertNotNull(la);
        assertEquals(zdt4, la);
    }

}
