package br.inpe.climaespacial.swd.kp.forecast.dtos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.time.ZonedDateTime;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(KPForecast.class)
public class KPForecastTest {

    @Inject
    private KPForecast kpForecast;

    @Test
    public void predictedTimeTagTest() {
        ZonedDateTime predictedTimeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        kpForecast.setPredictedTimeTag(predictedTimeTag);

        assertEquals(predictedTimeTag, kpForecast.getPredictedTimeTag());
    }
    
    @Test
    public void indexesTimeTagTest() {
        ZonedDateTime indexesTimeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        kpForecast.setIndexesTimeTag(indexesTimeTag);

        assertEquals(indexesTimeTag, kpForecast.getIndexesTimeTag());
    }
    
    @Test
    public void probability1_called() {
        Double probability = 1.0;
        
        kpForecast.setProbability1(probability);
        
        assertSame(probability, kpForecast.getProbability1());
    }
    
    @Test
    public void probability2_called() {
        Double probability = 2.0;
        
        kpForecast.setProbability2(probability);
        
        assertSame(probability, kpForecast.getProbability2());
    }
    
    @Test
    public void probability3_called() {
        Double probability = 3.0;
        
        kpForecast.setProbability3(probability);
        
        assertSame(probability, kpForecast.getProbability3());
    }
    
    
    @Test
    public void upperLimit1_called() {
        Double upperLimit = 4.0;
        
        kpForecast.setUpperLimit1(upperLimit);
        
        assertSame(upperLimit, kpForecast.getUpperLimit1());
    }
    
    @Test
    public void upperLimit2_called() {
        Double upperLimit = 5.0;
        
        kpForecast.setUpperLimit2(upperLimit);
        
        assertSame(upperLimit, kpForecast.getUpperLimit2());
    }
    
    @Test
    public void upperLimit3_called() {
        Double upperLimit = 6.0;
        
        kpForecast.setUpperLimit3(upperLimit);
        
        assertSame(upperLimit, kpForecast.getUpperLimit3());
    }
    
    @Test
    public void inferiorLimit1_called() {
        Double inferiorLimit = 7.0;
        
        kpForecast.setInferiorLimit1(inferiorLimit);
        
        assertSame(inferiorLimit, kpForecast.getInferiorLimit1());
    }
    
    @Test
    public void inferiorLimit2_called() {
        Double inferiorLimit = 8.0;
        
        kpForecast.setInferiorLimit2(inferiorLimit);
        
        assertSame(inferiorLimit, kpForecast.getInferiorLimit2());
    }
    
    @Test
    public void inferiorLimit3_called() {
        Double inferiorLimit = 9.0;
        
        kpForecast.setInferiorLimit3(inferiorLimit);
        
        assertSame(inferiorLimit, kpForecast.getInferiorLimit3());
    }
}
