package br.inpe.climaespacial.swd.values.bt.value.repositories;

import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.home.validators.DefaultIntervalValidator;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;
import br.inpe.climaespacial.swd.values.bt.value.dtos.BT;
import br.inpe.climaespacial.swd.values.bt.value.factories.DefaultBTFactory;
import br.inpe.climaespacial.swd.values.bt.value.mappers.DefaultBTMapper;
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
    DefaultBTRepository.class,
    DefaultBTMapper.class,
    DefaultBTFactory.class
})
public class BTRepositoryIntegrationTest extends BaseIntegrationTest {

    private final ZonedDateTime initialDateTime = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
    private final ZonedDateTime finalDateTime = ZonedDateTime.parse("2017-01-02T00:00:00z[UTC]");

    @Inject
    private EntityManager entityManager;

    @Inject
    private BTRepository btRepository; 

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

        List<BT> bl = btRepository.list(initialDateTime, finalDateTime);

        assertNotNull(bl);
        assertThat(bl, is(not(empty())));
        assertThat(bl, hasSize(2));
        BT bt1 = bl.get(0);
        assertEquals(zdt1, bt1.getTimeTag());
        assertEquals(value1, bt1.getValue());
        BT bt2 = bl.get(1);
        assertEquals(zdt2, bt2.getTimeTag());
        assertEquals(value2, bt2.getValue());

    }
    
    public MagEntity create(Double value, ZonedDateTime timeTag) {
        MagEntity me = new MagEntity();

        me.setId(UUID.randomUUID());
        me.setCreationDate(ZonedDateTime.now());
        me.setModificationDate(ZonedDateTime.now());
        me.setTimeTag(timeTag);
        me.setBt(value);

        return me;
    }
}
