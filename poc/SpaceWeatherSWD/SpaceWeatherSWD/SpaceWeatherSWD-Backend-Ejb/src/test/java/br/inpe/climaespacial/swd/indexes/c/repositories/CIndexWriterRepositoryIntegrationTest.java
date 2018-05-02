package br.inpe.climaespacial.swd.indexes.c.repositories;

import br.inpe.climaespacial.swd.acquisition.providers.DefaultDateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.DefaultUUIDProvider;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import br.inpe.climaespacial.swd.indexes.c.factories.DefaultCIndexEntityFactory;
import br.inpe.climaespacial.swd.indexes.c.mappers.DefaultCIndexEntityMapper;
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
    DefaultCIndexWriterRepository.class,
    DefaultCIndexEntityMapper.class,
    DefaultCIndexEntityFactory.class,
    DefaultDateTimeProvider.class,
    DefaultUUIDProvider.class,
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class
})
public class CIndexWriterRepositoryIntegrationTest extends BaseIntegrationTest {

    @Inject
    private EntityManager entityManager;

    @Inject
    private CIndexWriterRepository cIndexWriterRepository;

    @InRequestScope
    @Test
    public void save_called_saves() {

        CIndex ci = createCIndex();

        cIndexWriterRepository.save(ci);

        TypedQuery<CIndexEntity> q = entityManager.createQuery("SELECT cie FROM CIndexEntity cie", CIndexEntity.class);
        CIndexEntity cie = q.getSingleResult();
        assertNotNull(cie);
    }

    private CIndex createCIndex() {
        CIndex ci = new CIndex();
        ci.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        ci.setPreValue(5.0);
        ci.setPostValue(5.0);

        return ci;
    }

}
