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

import br.inpe.climaespacial.swd.acquisition.dtos.Plasma;
import br.inpe.climaespacial.swd.acquisition.entities.PlasmaEntity;
import br.inpe.climaespacial.swd.acquisition.factories.DefaultPlasmaEntityFactory;
import br.inpe.climaespacial.swd.acquisition.mappers.DefaultPlasmaEntityMapper;
import br.inpe.climaespacial.swd.acquisition.providers.DefaultDateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.DefaultUUIDProvider;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;

@RunWith(CdiRunner.class)
@AdditionalClasses({ HelperFactoryProducer.class, DefaultPlasmaWriterRepository.class, DefaultPlasmaEntityMapper.class,
        PlasmaEntity.class, DefaultListFactory.class, DefaultPlasmaEntityFactory.class, DefaultDateTimeProvider.class,
        DefaultUUIDProvider.class, EntityManagerFactoryProducer.class, EntityManagerProducer.class })
public class PlasmaWriterRepositoryIntegrationTest extends BaseIntegrationTest {

    @Inject
    private EntityManager entityManager;

    @Inject
    private PlasmaWriterRepository plasmaWriterRepository;

    @InRequestScope
    @Test
    public void save_called_savesPlasmaList() {

        Plasma plasma = createPlasma();
        List<Plasma> plasmaList = Arrays.asList(plasma);

        plasmaWriterRepository.save(plasmaList);

        TypedQuery<PlasmaEntity> q = entityManager.createQuery("SELECT pe FROM PlasmaEntity pe", PlasmaEntity.class);
        List<PlasmaEntity> plasmaEntityList = q.getResultList();
        assertEquals(1, plasmaEntityList.size());
    }

    @InRequestScope
    @Test
    public void save_called_savesPlasmaListIdempotently() {

        Plasma plasma = createPlasma();
        List<Plasma> plasmaList = Arrays.asList(plasma);

        plasmaWriterRepository.save(plasmaList);
        plasmaWriterRepository.save(plasmaList);

        TypedQuery<PlasmaEntity> q = entityManager.createQuery("SELECT pe FROM PlasmaEntity pe", PlasmaEntity.class);
        List<PlasmaEntity> plasmaEntityList = q.getResultList();
        assertEquals(1, plasmaEntityList.size());
    }

    private Plasma createPlasma() {
        Plasma plasma = new Plasma();
        plasma.setDensity(2.1);
        plasma.setSpeed(2.1);
        plasma.setTemperature(2.1);
        plasma.setTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        return plasma;
    }
    
}
