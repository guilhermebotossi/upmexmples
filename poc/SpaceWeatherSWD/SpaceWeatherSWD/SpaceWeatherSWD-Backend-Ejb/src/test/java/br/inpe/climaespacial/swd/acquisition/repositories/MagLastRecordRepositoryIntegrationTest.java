package br.inpe.climaespacial.swd.acquisition.repositories;

import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.qualifiers.MagLastRecordQualifier;
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
    DefaultMagLastRecordRepository.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class
})
public class MagLastRecordRepositoryIntegrationTest extends BaseIntegrationTest {

    @Inject
    private EntityManager entityManager;

    @Inject
    @MagLastRecordQualifier
    private LastRecordRepository lastRecordRepository;

    @InRequestScope
    @Test
    public void getLast_called_succeedsWithNullReturn() {
        ZonedDateTime last = lastRecordRepository.getLast();

        assertNull(last);
    }

    @InRequestScope
    @Test
    public void getLast_called_succeedsWithDateTimeResponse() {
        MagEntity me = createMagEntity();
        entityManager.persist(me);

        ZonedDateTime last = lastRecordRepository.getLast();

        assertNotNull(last);
        assertEquals(me.getTimeTag(), last);
    }

    private MagEntity createMagEntity() {
        MagEntity me = new MagEntity();
        me.setId(UUID.randomUUID());
        me.setCreationDate(ZonedDateTime.now());
        me.setModificationDate(ZonedDateTime.now());
        me.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        me.setBt(0D);
        me.setBxGsm(0D);
        me.setByGsm(0D);
        me.setBzGsm(0D);
        me.setLatGsm(0D);
        me.setLonGsm(0D);
        return me;
    }
}
