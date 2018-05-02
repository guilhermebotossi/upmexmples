package br.inpe.climaespacial.swd.acquisition.repositories;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.acquisition.dtos.Mag;
import br.inpe.climaespacial.swd.acquisition.entities.MagEntity;
import br.inpe.climaespacial.swd.acquisition.factories.DefaultMagEntityFactory;
import br.inpe.climaespacial.swd.acquisition.mappers.DefaultMagEntityMapper;
import br.inpe.climaespacial.swd.acquisition.providers.DefaultDateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.DefaultUUIDProvider;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;

@RunWith(CdiRunner.class)
@AdditionalClasses({ HelperFactoryProducer.class, DefaultMagWriterRepository.class, DefaultMagEntityMapper.class,
        MagEntity.class, DefaultListFactory.class, DefaultMagEntityFactory.class, DefaultDateTimeProvider.class,
        DefaultUUIDProvider.class, EntityManagerFactoryProducer.class, EntityManagerProducer.class })
public class MagWriterRepositoryIntegrationTest extends BaseIntegrationTest {

    @Inject
    private EntityManager entityManager;

    @Inject
    private MagWriterRepository magWriterRepository;

    @InRequestScope
    @Test
    public void save_called_savesMagList() {

        Mag mag = createMag();
        List<Mag> magList = Arrays.asList(mag);

        magWriterRepository.save(magList);

        TypedQuery<MagEntity> q = entityManager.createQuery("SELECT me FROM MagEntity me", MagEntity.class);
        List<MagEntity> magEntityList = q.getResultList();
        assertEquals(1, magEntityList.size());
    }

    @InRequestScope
    @Test
    public void save_called_savesMagListIdempotently() {

        Mag mag = createMag();
        List<Mag> magList = Arrays.asList(mag);

        magWriterRepository.save(magList);
        magWriterRepository.save(magList);

        TypedQuery<MagEntity> q = entityManager.createQuery("SELECT me FROM MagEntity me", MagEntity.class);
        List<MagEntity> magEntityList = q.getResultList();
        assertEquals(1, magEntityList.size());
    }

    private Mag createMag() {
        Mag mag = new Mag();
        mag.setBt(2.1);
        mag.setBxGsm(2.2);
        mag.setByGsm(2.3);
        mag.setBzGsm(2.4);
        mag.setLatGsm(2.5);
        mag.setLonGsm(2.6);
        mag.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        return mag;
    }
}
