package br.inpe.climaespacial.swd.kp.forecast.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.indexes.c.entities.CIndexEntity;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.entities.KPEntity;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;
import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;
import br.inpe.climaespacial.swd.kp.factories.DefaultKPValueFactory;
import br.inpe.climaespacial.swd.kp.forecast.entities.KPForecastEntity;
import br.inpe.climaespacial.swd.kp.forecast.mappers.DefaultKPValueMapper;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;

@RunWith(CdiRunner.class)
@AdditionalClasses( {EntityManagerFactoryProducer.class,
        EntityManagerProducer.class,
        HelperFactoryProducer.class,
        DefaultKPValueReaderRepository.class,
        DefaultKPValueMapper.class,
        DefaultKPValueFactory.class,
        DefaultListFactory.class
        })
public class KPValueReaderRepositoryIntegrationTest extends BaseIntegrationTest {

    @Inject
    private EntityManager entityManager;
    
    @Inject
    private KPValueReaderRepository kpValueReaderRepository;
    
    @Test 
    @InRequestScope
    public void getLastKPValue_called_getLast() {
        ZonedDateTime zdt1 = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]"); 
        KPEntity kpe1 = createKPEntity(zdt1);
        persist(kpe1);
        
        CIndexEntity cie1 = createCIndexEntity(zdt1);
        entityManager.persist(cie1);
        
        CIndexEntity cie2 = createCIndexEntity(zdt1.plusHours(3));
        entityManager.persist(cie2);
        
        CIndexEntity cie3 = createCIndexEntity(zdt1.plusHours(6));
        entityManager.persist(cie3);
        
        KPForecastEntity kpfe = createKPForecastEntity(zdt1);
        entityManager.persist(kpfe);
        
        ZonedDateTime zdt2 = ZonedDateTime.parse("2017-01-04T00:00:00z[UTC]"); 
        KPEntity kpe2 = createKPEntity(zdt2);
        persist(kpe2);
        
        CIndexEntity cie4 = createCIndexEntity(zdt2);
        entityManager.persist(cie4);
        
        CIndexEntity cie5 = createCIndexEntity(zdt2.plusHours(3));
        entityManager.persist(cie5);
        
        CIndexEntity cie6 = createCIndexEntity(zdt2.plusHours(6));
        entityManager.persist(cie6);
        
        KPForecastEntity kpfe2 = createKPForecastEntity(zdt2);
        entityManager.persist(kpfe2);
        
        KPValue lastKPValue = kpValueReaderRepository.getLastKPValue(); 
         
        assertNotNull(lastKPValue);
        assertEquals(zdt1.plusHours(3),  lastKPValue.getTimeTag());
    }
     
    private void persist(KPEntity kpe) {
        entityManager.persist(kpe);
        
        kpe.getKPValueList().forEach(kpv -> {entityManager.persist(kpv);});
    }

    @Test
    @InRequestScope
    public void getLastKPValue_called_returnsNull() { 
        KPValue lastKPValue = kpValueReaderRepository.getLastKPValue(); 
        
        assertNull(lastKPValue);
    }

    private KPEntity createKPEntity(ZonedDateTime zonedDateTime) {
        KPEntity kpe = new KPEntity();
        kpe.setId(UUID.randomUUID());
        kpe.setCreationDate(ZonedDateTime.now());
        kpe.setModificationDate(ZonedDateTime.now());
        kpe.setTimeTag(zonedDateTime);
        
        KPValueEntity kpve1 = createKPValueEntity(zonedDateTime, kpe);
        KPValueEntity kpve2 = createKPValueEntity(zonedDateTime.plusHours(3), kpe);
        KPValueEntity kpve3 = createKPValueEntity(zonedDateTime.plusHours(6), kpe);
        
        List<KPValueEntity> kpvel = Arrays.asList(kpve1, kpve2, kpve3);
        
        kpe.setKPValueList(kpvel);
        
        return kpe;
    }
    
    private CIndexEntity createCIndexEntity(ZonedDateTime zonedDateTime) {
        CIndexEntity cie = new CIndexEntity();
        cie.setId(UUID.randomUUID());
        cie.setCreationDate(ZonedDateTime.now());
        cie.setModificationDate(ZonedDateTime.now());
        cie.setTimeTag(zonedDateTime);
        return cie;
    }
    
    private KPForecastEntity createKPForecastEntity(ZonedDateTime zonedDateTime) {
        KPForecastEntity kpfe = new KPForecastEntity();
        kpfe.setId(UUID.randomUUID());
        kpfe.setCreationDate(ZonedDateTime.now());
        kpfe.setModificationDate(ZonedDateTime.now());
        kpfe.setPredictedTimeTag(zonedDateTime.plusHours(3));
        kpfe.setIndexesTimeTag(zonedDateTime);
        kpfe.setProbability1(1.0);
        kpfe.setProbability2(2.);
        kpfe.setProbability3(3.0);
        kpfe.setInferiorLimit1(4.0);
        kpfe.setInferiorLimit2(5.0);
        kpfe.setInferiorLimit3(6.0);
        kpfe.setUpperLimit1(7.0);
        kpfe.setUpperLimit2(8.0);
        kpfe.setUpperLimit3(9.0);
        
        return kpfe;
    }
    
    private KPValueEntity createKPValueEntity(ZonedDateTime zonedDateTime, KPEntity kpEntity) {
        KPValueEntity kpve = new KPValueEntity();
        kpve.setId(UUID.randomUUID());
        kpve.setCreationDate(ZonedDateTime.now());
        kpve.setModificationDate(ZonedDateTime.now());
        kpve.setTimeTag(zonedDateTime);
        kpve.setKPValue(3L);
        kpve.setKPValueFlag(KPValueFlag.ZERO);
        kpve.setKPEntity(kpEntity);
        
        return kpve;
    }
}
