package br.inpe.climaespacial.swd.indexes.b.repositories;

import br.inpe.climaespacial.swd.acquisition.providers.DefaultDateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.DefaultUUIDProvider;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.b.entities.BIndexEntity;
import br.inpe.climaespacial.swd.indexes.b.factories.DefaultBIndexEntityFactory;
import br.inpe.climaespacial.swd.indexes.b.mappers.DefaultBIndexEntityMapper;
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
    DefaultBIndexWriterRepository.class,
    DefaultBIndexEntityMapper.class,
    DefaultBIndexEntityFactory.class,
    DefaultDateTimeProvider.class,
    DefaultUUIDProvider.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class
})
public class BIndexWriterRepositoryIntegrationTest extends BaseIntegrationTest {

    @Inject
    private EntityManager entityManager;

    @Inject
    private BIndexWriterRepository bIndexWriterRepository;

    @InRequestScope
    @Test
    public void save_called_saves() {

        BIndex bi = createBIndex();

        bIndexWriterRepository.save(bi);

        TypedQuery<BIndexEntity> q = entityManager.createQuery("SELECT bie FROM BIndexEntity bie", BIndexEntity.class);
        BIndexEntity bie = q.getSingleResult();
        assertNotNull(bie);
    }

    private BIndex createBIndex() {
        BIndex bi = new BIndex();
        bi.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        bi.setPostValue(5.0);

        return bi;
    }

}
