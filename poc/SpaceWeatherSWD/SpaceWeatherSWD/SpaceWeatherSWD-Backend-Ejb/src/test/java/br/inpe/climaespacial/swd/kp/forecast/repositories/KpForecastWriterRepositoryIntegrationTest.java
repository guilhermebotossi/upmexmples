package br.inpe.climaespacial.swd.kp.forecast.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

import br.inpe.climaespacial.swd.acquisition.providers.DefaultDateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.DefaultUUIDProvider;
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.forecast.entities.KPForecastEntity;
import br.inpe.climaespacial.swd.kp.forecast.factories.DefaultKPForecastEntityFactory;
import br.inpe.climaespacial.swd.kp.forecast.mappers.DefaultKPForecastEntityMapper;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class,
    HelperFactoryProducer.class,
    DefaultKpForecastWriterRepository.class,
    DefaultKPForecastEntityMapper.class,
    DefaultListFactory.class,
    DefaultKPForecastEntityFactory.class,
    DefaultDateTimeProvider.class,
    DefaultUUIDProvider.class
})
public class KpForecastWriterRepositoryIntegrationTest extends BaseIntegrationTest {
    
    @Inject
    private EntityManager entityManager;

    @Inject
    private KPForecastWriterRepository kpForecastWriterRepository;
    
    @Test
    @InRequestScope 
    public void save_called_succeeds() {
        KPForecast kpf = new KPForecast();
        kpf.setPredictedTimeTag(ZonedDateTime.parse("2017-01-01T15:00:00z[UTC]"));
        kpf.setIndexesTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        Double probability1 = 1.0;
        kpf.setProbability1(probability1);
        Double probability2 = 2.0;
        kpf.setProbability2(probability2);
        Double probability3 = 3.0;
        kpf.setProbability3(probability3);
        Double inferiorLimit1 = 4.0;
        kpf.setInferiorLimit1(inferiorLimit1);
        Double inferiorLimit2 = 5.0;
        kpf.setInferiorLimit2(inferiorLimit2);
        Double inferiorLimit3 = 6.0;
        kpf.setInferiorLimit3(inferiorLimit3);
        Double upperLimit1 = 1.0;
        kpf.setUpperLimit1(upperLimit1);
        Double upperLimit2 = 1.0;
        kpf.setUpperLimit2(upperLimit2);
        Double upperLimit3 = 1.0;
        kpf.setUpperLimit3(upperLimit3);
        
        List<KPForecast> kpfl = Arrays.asList(kpf);
        
        kpForecastWriterRepository.save(kpfl);
        
        KPForecastEntity kpfe = findKPForecateEntity();
        
        assertNotNull(kpfe);
        assertEquals(kpf.getPredictedTimeTag(), kpfe.getPredictedTimeTag());
        assertEquals(probability1 , kpfe.getProbability1());
        assertEquals(probability2 , kpfe.getProbability2());
        assertEquals(probability3 , kpfe.getProbability3());
        assertEquals(inferiorLimit1 , kpfe.getInferiorLimit1());
        assertEquals(inferiorLimit2 , kpfe.getInferiorLimit2());
        assertEquals(inferiorLimit3 , kpfe.getInferiorLimit3());
        assertEquals(upperLimit1 , kpfe.getUpperLimit1());
        assertEquals(upperLimit2 , kpfe.getUpperLimit2());
        assertEquals(upperLimit3 , kpfe.getUpperLimit3());
        assertEquals(kpf.getIndexesTimeTag(), kpfe.getIndexesTimeTag());
    }

    private KPForecastEntity findKPForecateEntity() {
        TypedQuery<KPForecastEntity> tq = entityManager.createQuery("SELECT kpf FROM KPForecastEntity kpf", KPForecastEntity.class);
        return tq.getSingleResult();
    }
}
