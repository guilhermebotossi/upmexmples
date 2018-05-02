package br.inpe.climaespacial.swd.kp.acquisition.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.acquisition.providers.DefaultDateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.DefaultUUIDProvider;
import br.inpe.climaespacial.swd.kp.dtos.KP;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.entities.KPEntity;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;
import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;
import br.inpe.climaespacial.swd.kp.factories.DefaultKPEntityFactory;
import br.inpe.climaespacial.swd.kp.factories.DefaultKPValueEntityFactory;
import br.inpe.climaespacial.swd.kp.mappers.DefaultKPEntityMapper;
import br.inpe.climaespacial.swd.kp.mappers.DefaultKPValueEntityMapper;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;

@RunWith(CdiRunner.class)
@AdditionalClasses({ EntityManagerFactoryProducer.class, EntityManagerProducer.class, HelperFactoryProducer.class,
        DefaultKPEntityMapper.class, DefaultKPEntityFactory.class, DefaultListFactory.class,
        DefaultDateTimeProvider.class, DefaultUUIDProvider.class, DefaultKPWriterRepository.class,
        DefaultKPValueEntityMapper.class, DefaultKPValueEntityFactory.class

})
public class KPWriterRepositoryIntegrationTest extends BaseIntegrationTest {

    @Inject
    private EntityManager entityManager;

    @Inject
    private KPWriterRepository kpWriterRepository;

    @Test
    @InRequestScope
    public void save_calledWithKPNotFound_persists() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        KP kp = createKP(zdt);
        List<KP> kpl = Arrays.asList(kp);

        kpWriterRepository.save(kpl);

        TypedQuery<KPEntity> tqkpe = entityManager
                .createQuery("SELECT kpe FROM KPEntity kpe WHERE kpe.timeTag = :timeTag", KPEntity.class);
        tqkpe.setParameter("timeTag", zdt);
        KPEntity kpe = tqkpe.getSingleResult();
        assertNotNull(kpe);
    }

    @Test
    @InRequestScope
    public void save_calledWithKPUncompletedFounded_merges() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        KPEntity kp = createKPEntity(zdt);
        kp.getKPValueList().get(0).setKPValue(101L);
        kp.getKPValueList().get(1).setKPValue(102L);

        entityManager.persist(kp);
        entityManager.persist(kp.getKPValueList().get(0));
        entityManager.persist(kp.getKPValueList().get(1));
        
        entityManager.flush();

        KP kp1 = createKP(zdt);
        kp1.getKPValueList().get(0).setKPValue(201L);
        kp1.getKPValueList().get(1).setKPValue(202L);
        List<KP> kpl1 = Arrays.asList(kp1);

        kpWriterRepository.save(kpl1);

        TypedQuery<KPEntity> tqkpe = entityManager
                .createQuery("SELECT kpe FROM KPEntity kpe WHERE kpe.timeTag = :timeTag", KPEntity.class);
        tqkpe.setParameter("timeTag", zdt);
        KPEntity kpe = tqkpe.getSingleResult();
        assertNotNull(kpe);
        assertNotNull(kpe.getKPValueList().get(0).getKPValue());
        assertEquals(201L, kpe.getKPValueList().get(0).getKPValue(), 0.001);
        assertNotNull(kpe.getKPValueList().get(1).getKPValue());
        assertEquals(202L, kpe.getKPValueList().get(1).getKPValue(), 0.001); 
    }

    @Test
    @InRequestScope
    public void save_calledWithKPCompletedFounded_merge() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        KP kp = createKP(zdt); 
        kp.getKPValueList().get(0).setKPValue(101L);
        kp.getKPValueList().get(1).setKPValue(102L);
        List<KP> kpl = Arrays.asList(kp);

        kpWriterRepository.save(kpl);

        kp = createKP(zdt);
        kp.getKPValueList().get(0).setKPValue(201L);
        kp.getKPValueList().get(1).setKPValue(202L);
        kpl = Arrays.asList(kp);

        kpWriterRepository.save(kpl);

        TypedQuery<KPEntity> tqkpe = entityManager.createQuery(
                "SELECT kpe FROM KPEntity kpe JOIN FETCH kpe.kpValueList WHERE kpe.timeTag = :timeTag", KPEntity.class);
        tqkpe.setParameter("timeTag", zdt);
        KPEntity kpe = tqkpe.getSingleResult();
        assertNotNull(kpe);
        assertNotNull(kpe.getKPValueList().get(0).getKPValue());
        assertEquals(201L, kpe.getKPValueList().get(0).getKPValue(), 0.001);
        assertNotNull(kpe.getKPValueList().get(1).getKPValue());
        assertEquals(202L, kpe.getKPValueList().get(1).getKPValue(), 0.001);
    }

    private KP createKP(ZonedDateTime zdt) {
        KP kp = new KP();
        kp.setTimeTag(zdt);
        List<KPValue> kpvl = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            KPValue kpv = new KPValue();
            kpv.setTimeTag(zdt);
            kpv.setKPValue(i + 1L);
            kpv.setKPValueFlag(KPValueFlag.UP);
            kpvl.add(kpv);
            zdt = zdt.plusHours(3);
        }
        kp.setKPValueList(kpvl);
        kp.setSum(9L);
        kp.setSumFlag(KPValueFlag.UP);
        kp.setAp(10L);
        kp.setCp(11D);
        kp.setMostDisturbedAndQuietDays("Q8K");
        return kp;
    }
    
    private KPEntity createKPEntity(ZonedDateTime zdt) {
        KPEntity kp = new KPEntity();
        kp.setId(UUID.randomUUID());
        kp.setTimeTag(zdt);
        kp.setCreationDate(ZonedDateTime.now());
        kp.setModificationDate(ZonedDateTime.now());
        List<KPValueEntity> kpvl = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            KPValueEntity kpv = new KPValueEntity();
            kpv.setCreationDate(ZonedDateTime.now());
            kpv.setModificationDate(ZonedDateTime.now());
            kpv.setId(UUID.randomUUID());
            kpv.setTimeTag(zdt);
            kpv.setKPValue(i + 1L);
            kpv.setKPValueFlag(KPValueFlag.UP);
            kpv.setKPEntity(kp);
            kpvl.add(kpv);
            zdt = zdt.plusHours(3);
        }
        kp.setKPValueList(kpvl);
        kp.setSum(9L);
        kp.setSumFlag(KPValueFlag.UP);
        kp.setAp(10L);
        kp.setCp(11D);
        kp.setMostDisturbedAndQuietDays("Q8K");
        return kp;
    }

}
