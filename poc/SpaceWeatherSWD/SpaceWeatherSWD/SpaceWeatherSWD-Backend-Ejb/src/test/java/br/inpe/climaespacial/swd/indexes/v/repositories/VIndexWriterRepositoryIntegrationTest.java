package br.inpe.climaespacial.swd.indexes.v.repositories;

import br.inpe.climaespacial.swd.acquisition.providers.DefaultDateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.DefaultUUIDProvider;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.entities.VIndexEntity;
import br.inpe.climaespacial.swd.indexes.v.factories.DefaultVIndexEntityFactory;
import br.inpe.climaespacial.swd.indexes.v.mappers.DefaultVIndexEntityMapper;
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
    DefaultVIndexWriterRepository.class,
    DefaultVIndexEntityMapper.class,
    DefaultVIndexEntityFactory.class,
    DefaultDateTimeProvider.class,
    DefaultUUIDProvider.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class
})
public class VIndexWriterRepositoryIntegrationTest extends BaseIntegrationTest {

    @Inject
    private EntityManager entityManager;

    @Inject
    private VIndexWriterRepository vIndexWriterRepository;

    @InRequestScope
    @Test
    public void save_called_saves() {

        VIndex bi = createVIndex();

        vIndexWriterRepository.save(bi);

        TypedQuery<VIndexEntity> q = entityManager.createQuery("SELECT vie FROM VIndexEntity vie", VIndexEntity.class);
        VIndexEntity cie = q.getSingleResult();
        assertNotNull(cie);
    }

    private VIndex createVIndex() {
        VIndex vi = new VIndex();
        vi.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        vi.setPreValue(1.0);
        vi.setPostValue(2.0);        

        return vi;
    }

}
