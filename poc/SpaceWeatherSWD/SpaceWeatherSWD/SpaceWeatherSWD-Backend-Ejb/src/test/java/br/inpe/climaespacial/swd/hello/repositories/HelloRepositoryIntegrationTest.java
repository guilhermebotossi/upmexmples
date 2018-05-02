package br.inpe.climaespacial.swd.hello.repositories;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.hello.dtos.Hello;
import br.inpe.climaespacial.swd.hello.entities.HelloEntity;
import br.inpe.climaespacial.swd.hello.factories.DefaultHelloFactory;
import br.inpe.climaespacial.swd.hello.factories.HelloEntityFactory;
import br.inpe.climaespacial.swd.hello.mappers.DefaultHelloMapper;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;
import java.time.ZonedDateTime;
import java.util.UUID;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultHelloRepository.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class,
    DefaultHelloMapper.class,
    DefaultHelloFactory.class
})
public class HelloRepositoryIntegrationTest extends BaseIntegrationTest {

    @Produces
    @Mock
    private DateTimeProvider dateTimeProvider;

    @Produces
    @Mock
    private HelloEntityFactory helloEntityFactory;

    @Inject
    private HelloRepository helloRepository;

    @InRequestScope
    @Test
    public void getLastUpdate_called_returnLastUpdate() {
        HelloEntity helloEntity = new HelloEntity();
        helloEntity.setId(UUID.randomUUID());

        ZonedDateTime now = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        helloEntity.setModificationDate(now);
        helloEntity.setCreationDate(now);
        when(helloEntityFactory.create()).thenReturn(helloEntity);
        when(dateTimeProvider.getNow()).thenReturn(now);

        helloRepository.save();
        Hello h = helloRepository.getHello();

        assertNotNull(h);
        assertEquals(now, h.getModificationDate());
    }

}
