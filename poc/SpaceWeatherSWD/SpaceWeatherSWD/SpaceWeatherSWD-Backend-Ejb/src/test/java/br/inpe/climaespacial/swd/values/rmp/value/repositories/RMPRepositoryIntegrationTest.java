package br.inpe.climaespacial.swd.values.rmp.value.repositories;

import br.inpe.climaespacial.swd.home.validators.DefaultIntervalValidator;
import br.inpe.climaespacial.swd.calculation.entities.CalculatedValuesEntity;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;
import br.inpe.climaespacial.swd.values.rmp.value.dtos.RMP;
import br.inpe.climaespacial.swd.values.rmp.value.factories.DefaultRMPFactory;
import br.inpe.climaespacial.swd.values.rmp.value.mappers.DefaultRMPMapper;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class,
    HelperFactoryProducer.class,
    DefaultListFactory.class,
    DefaultIntervalValidator.class,
    DefaultRMPRepository.class,
    DefaultRMPMapper.class,
    DefaultRMPFactory.class
})
public class RMPRepositoryIntegrationTest extends BaseIntegrationTest {

    private final ZonedDateTime initialDateTime = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
    private final ZonedDateTime finalDateTime = ZonedDateTime.parse("2017-01-02T00:00:00z[UTC]");

    @Inject
    private EntityManager entityManager;

    @Inject
    private RMPRepository rmpRepository;
    
    @Test
    @InRequestScope
    public void list_called_succeeds() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        Double value1 = 1.0;
        entityManager.persist(create(value1, zdt1));

        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-01T23:59:00z[UTC]");
        Double value2 = 2.0;
        entityManager.persist(create(value2, zdt2));
        
        ZonedDateTime zdt3 = ZonedDateTime.parse("2017-01-02T00:00:00z[UTC]");
        Double value3 = 3.0;
        entityManager.persist(create(value3, zdt3));

        List<RMP> rmpl = rmpRepository.list(initialDateTime, finalDateTime);

        assertNotNull(rmpl);
        assertThat(rmpl, is(not(empty())));
        assertThat(rmpl, hasSize(2));
        RMP rmp1 = rmpl.get(0);
        assertEquals(zdt1, rmp1.getTimeTag());
        assertEquals(value1, rmp1.getValue());
        RMP rmp2 = rmpl.get(1);
        assertEquals(zdt2, rmp2.getTimeTag());
        assertEquals(value2, rmp2.getValue());

    }
    
    public CalculatedValuesEntity create(Double value, ZonedDateTime timeTag) {
        CalculatedValuesEntity cve = new CalculatedValuesEntity();

        cve.setId(UUID.randomUUID());
        cve.setCreationDate(ZonedDateTime.now());
        cve.setModificationDate(ZonedDateTime.now());
        cve.setTimeTag(timeTag);
        cve.setRmp(value);

        return cve;
    }
}
