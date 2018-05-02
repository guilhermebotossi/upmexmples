package br.inpe.climaespacial.swd.kp.forecast.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.time.ZonedDateTime;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(KPForecastEntity.class)
public class KPForecastEntityTest {

    @Inject
    private KPForecastEntity kpForecastEntity;

    @Test
    public void predictedTimeTagTest() {
        ZonedDateTime predictedTimeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        kpForecastEntity.setPredictedTimeTag(predictedTimeTag);

        assertEquals(predictedTimeTag, kpForecastEntity.getPredictedTimeTag());
    }
    
    @Test
    public void indexesTimeTagTest() {
        ZonedDateTime indexesTimeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        kpForecastEntity.setIndexesTimeTag(indexesTimeTag);

        assertEquals(indexesTimeTag, kpForecastEntity.getIndexesTimeTag());
    }
    
    @Test
    public void probability1_called() {
        Double probability = 1.0;
        
        kpForecastEntity.setProbability1(probability);
        
        assertSame(probability, kpForecastEntity.getProbability1());
    }
    
    @Test
    public void probability2_called() {
        Double probability = 2.0;
        
        kpForecastEntity.setProbability2(probability);
        
        assertSame(probability, kpForecastEntity.getProbability2());
    }
    
    @Test
    public void probability3_called() {
        Double probability = 3.0;
        
        kpForecastEntity.setProbability3(probability);
        
        assertSame(probability, kpForecastEntity.getProbability3());
    }
    
    
    @Test
    public void upperLimit1_called() {
        Double upperLimit = 4.0;
        
        kpForecastEntity.setUpperLimit1(upperLimit);
        
        assertSame(upperLimit, kpForecastEntity.getUpperLimit1());
    }
    
    @Test
    public void upperLimit2_called() {
        Double upperLimit = 5.0;
        
        kpForecastEntity.setUpperLimit2(upperLimit);
        
        assertSame(upperLimit, kpForecastEntity.getUpperLimit2());
    }
    
    @Test
    public void upperLimit3_called() {
        Double upperLimit = 6.0;
        
        kpForecastEntity.setUpperLimit3(upperLimit);
        
        assertSame(upperLimit, kpForecastEntity.getUpperLimit3());
    }
    
    @Test
    public void inferiorLimit1_called() {
        Double inferiorLimit = 7.0;
        
        kpForecastEntity.setInferiorLimit1(inferiorLimit);
        
        assertSame(inferiorLimit, kpForecastEntity.getInferiorLimit1());
    }
    
    @Test
    public void inferiorLimit2_called() {
        Double inferiorLimit = 8.0;
        
        kpForecastEntity.setInferiorLimit2(inferiorLimit);
        
        assertSame(inferiorLimit, kpForecastEntity.getInferiorLimit2());
    }
    
    @Test
    public void inferiorLimit3_called() {
        Double inferiorLimit = 9.0;
        
        kpForecastEntity.setInferiorLimit3(inferiorLimit);
        
        assertSame(inferiorLimit, kpForecastEntity.getInferiorLimit3());
    }
}
