package br.inpe.climaespacial.swd.values.density.value.repositories;

import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.home.validators.DefaultIntervalValidator;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;
import br.inpe.climaespacial.swd.values.density.value.dtos.Density;
import br.inpe.climaespacial.swd.values.density.value.factories.DefaultDensityFactory;
import br.inpe.climaespacial.swd.values.density.value.mappers.DefaultDensityMapper;
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
    DefaultDensityRepository.class,
    DefaultDensityMapper.class,
    DefaultDensityFactory.class
})
public class DensityRepositoryIntegrationTest extends BaseIntegrationTest {

    private final ZonedDateTime initialDateTime = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
    private final ZonedDateTime finalDateTime = ZonedDateTime.parse("2017-01-02T00:00:00z[UTC]");

    @Inject
    private EntityManager entityManager;

    @Inject
    private DensityRepository densityRepository;

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

        List<Density> dl = densityRepository.list(initialDateTime, finalDateTime);

        assertNotNull(dl);
        assertThat(dl, is(not(empty())));
        assertThat(dl, hasSize(2));
        Density d1 = dl.get(0);
        assertEquals(zdt1, d1.getTimeTag());
        assertEquals(value1, d1.getValue());
        Density d2 = dl.get(1);
        assertEquals(zdt2, d2.getTimeTag());
        assertEquals(value2, d2.getValue());

    }
    
    public PlasmaEntity create(Double value, ZonedDateTime timeTag) {
        PlasmaEntity pe = new PlasmaEntity();

        pe.setId(UUID.randomUUID());
        pe.setCreationDate(ZonedDateTime.now());
        pe.setModificationDate(ZonedDateTime.now());
        pe.setTimeTag(timeTag);
        pe.setDensity(value);

        return pe;
    }

}
