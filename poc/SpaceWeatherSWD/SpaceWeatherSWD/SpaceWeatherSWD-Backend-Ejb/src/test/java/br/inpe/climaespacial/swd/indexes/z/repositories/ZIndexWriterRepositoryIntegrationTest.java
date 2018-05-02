package br.inpe.climaespacial.swd.indexes.z.repositories;

import br.inpe.climaespacial.swd.acquisition.providers.DefaultDateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.DefaultUUIDProvider;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import br.inpe.climaespacial.swd.indexes.z.entities.ZIndexEntity;
import br.inpe.climaespacial.swd.indexes.z.factories.DefaultZIndexEntityFactory;
import br.inpe.climaespacial.swd.indexes.z.mappers.DefaultZIndexEntityMapper;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;
import java.time.ZonedDateTime;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultZIndexWriterRepository.class,
    DefaultZIndexEntityMapper.class,
    DefaultZIndexEntityFactory.class,
    DefaultDateTimeProvider.class,
    DefaultUUIDProvider.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class
})
public class ZIndexWriterRepositoryIntegrationTest extends BaseIntegrationTest {

    @Inject
    private EntityManager entityManager;

    @Inject
    private ZIndexWriterRepository bIndexWriterRepository;

    @InRequestScope
    @Test
    public void save_called_saves() {

        ZIndex zi = createZIndex();

        bIndexWriterRepository.save(zi);

        TypedQuery<ZIndexEntity> q = entityManager.createQuery("SELECT zie FROM ZIndexEntity zie", ZIndexEntity.class);
        ZIndexEntity bie = q.getSingleResult();
        assertNotNull(bie);
    }

    private ZIndex createZIndex() {
        ZIndex zi = new ZIndex();
        zi.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        zi.setPreValue(5.0);
        zi.setPostValue(5.0);

        return zi;
    }

}
