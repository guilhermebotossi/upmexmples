package br.inpe.climaespacial.swd.kp.values.repositories;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.time.ZonedDateTime;
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
import br.inpe.climaespacial.swd.kp.values.mappers.DefaultKPForecastMapper;
import br.inpe.climaespacial.swd.kp.values.repositories.DefaultKPForecastReaderRepository;
import br.inpe.climaespacial.swd.kp.values.repositories.KPForecastReaderRepository;
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.forecast.entities.KPForecastEntity;
import br.inpe.climaespacial.swd.kp.forecast.factories.DefaultKPForecastFactory;
import br.inpe.climaespacial.swd.test.BaseIntegrationTest;
import br.inpe.climaespacial.swd.test.EntityManagerFactoryProducer;
import br.inpe.climaespacial.swd.test.EntityManagerProducer;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    EntityManagerFactoryProducer.class,
    EntityManagerProducer.class,
    HelperFactoryProducer.class,
    DefaultKPForecastReaderRepository.class,
    DefaultKPForecastMapper.class,
    DefaultListFactory.class,
    DefaultKPForecastFactory.class
})
public class KPForecastReaderRepositoryIntegrationTest extends BaseIntegrationTest {
    
    private static final ZonedDateTime INDEXES_DATE_TIME =  ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
    
    @Inject
    private EntityManager entityManager;

    @Inject
    private KPForecastReaderRepository kpForecastReaderRepository;
    
    @Test
    @InRequestScope
    public void getNextForecasts_called_returnsEmptyList() {
        List<KPForecast> nextForecasts = kpForecastReaderRepository.getNextForecasts(INDEXES_DATE_TIME);
        
        assertNotNull(nextForecasts);
        assertThat(nextForecasts, is(empty()));
    }
    
    @Test
    @InRequestScope
    public void getNextForecasts_called_returnsList() {
        KPForecastEntity kpfe1 = createKPForecastentity(ZonedDateTime.parse("2017-01-01T15:00:00z[UTC]"));
        entityManager.persist(kpfe1);
        
        KPForecastEntity kpfe2 = createKPForecastentity(ZonedDateTime.parse("2017-01-01T18:00:00z[UTC]"));
        entityManager.persist(kpfe2);
        
        List<KPForecast> nextForecasts = kpForecastReaderRepository.getNextForecasts(INDEXES_DATE_TIME);
        
        assertNotNull(nextForecasts);
        assertThat(nextForecasts, is(not(empty())));
        assertThat(nextForecasts, hasSize(2));
        
        
        KPForecast kpf1 =  nextForecasts.get(0);
        assertNotNull(kpf1);
        assertEquals(kpfe1.getPredictedTimeTag(), kpf1.getPredictedTimeTag());
        assertEquals(INDEXES_DATE_TIME, kpf1.getIndexesTimeTag());
        
        KPForecast kpf2 =  nextForecasts.get(1);
        assertNotNull(kpf2);
        assertEquals(kpfe2.getPredictedTimeTag(), kpf2.getPredictedTimeTag());
        assertEquals(INDEXES_DATE_TIME, kpf2.getIndexesTimeTag());
    }


    private KPForecastEntity createKPForecastentity(ZonedDateTime predictedTimeTag) {
        KPForecastEntity kpfe = new KPForecastEntity();
        kpfe.setId(UUID.randomUUID());
        kpfe.setCreationDate(ZonedDateTime.now());
        kpfe.setModificationDate(ZonedDateTime.now());
        kpfe.setIndexesTimeTag(INDEXES_DATE_TIME);
        kpfe.setPredictedTimeTag(predictedTimeTag);
        Double probability1 = 1.0;
        kpfe.setProbability1(probability1);
        Double probability2 = 2.0;
        kpfe.setProbability2(probability2);
        Double probability3 = 3.0;
        kpfe.setProbability3(probability3);
        Double inferiorLimit1 = 4.0;
        kpfe.setInferiorLimit1(inferiorLimit1);
        Double inferiorLimit2 = 5.0;
        kpfe.setInferiorLimit2(inferiorLimit2);
        Double inferiorLimit3 = 6.0;
        kpfe.setInferiorLimit3(inferiorLimit3);
        Double upperLimit1 = 1.0;
        kpfe.setUpperLimit1(upperLimit1);
        Double upperLimit2 = 1.0;
        kpfe.setUpperLimit2(upperLimit2);
        Double upperLimit3 = 1.0;
        kpfe.setUpperLimit3(upperLimit3);
        return kpfe;
    }
}
