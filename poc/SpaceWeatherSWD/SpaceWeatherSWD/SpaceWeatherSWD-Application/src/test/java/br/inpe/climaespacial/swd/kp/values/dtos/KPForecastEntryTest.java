package br.inpe.climaespacial.swd.kp.values.dtos;

import static org.junit.Assert.assertSame;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(KPForecastEntry.class)
public class KPForecastEntryTest {
    
    @Inject
    private KPForecastEntry kpValueEntry;

    @Test
    public void probability1_called() {
        Double probability = 1.0;
        
        kpValueEntry.setProbability1(probability);
        
        assertSame(probability, kpValueEntry.getProbability1());
    }
    
    @Test
    public void probability2_called() {
        Double probability = 2.0;
        
        kpValueEntry.setProbability2(probability);
        
        assertSame(probability, kpValueEntry.getProbability2());
    }
    
    @Test
    public void probability3_called() {
        Double probability = 3.0;
        
        kpValueEntry.setProbability3(probability);
        
        assertSame(probability, kpValueEntry.getProbability3());
    }
    
    
    @Test
    public void upperLimit1_called() {
        Double upperLimit = 4.0;
        
        kpValueEntry.setUpperLimit1(upperLimit);
        
        assertSame(upperLimit, kpValueEntry.getUpperLimit1());
    }
    
    @Test
    public void upperLimit2_called() {
        Double upperLimit = 5.0;
        
        kpValueEntry.setUpperLimit2(upperLimit);
        
        assertSame(upperLimit, kpValueEntry.getUpperLimit2());
    }
    
    @Test
    public void upperLimit3_called() {
        Double upperLimit = 6.0;
        
        kpValueEntry.setUpperLimit3(upperLimit);
        
        assertSame(upperLimit, kpValueEntry.getUpperLimit3());
    }
    
    @Test
    public void inferiorLimit1_called() {
        Double inferiorLimit = 7.0;
        
        kpValueEntry.setInferiorLimit1(inferiorLimit);
        
        assertSame(inferiorLimit, kpValueEntry.getInferiorLimit1());
    }
    
    @Test
    public void inferiorLimit2_called() {
        Double inferiorLimit = 8.0;
        
        kpValueEntry.setInferiorLimit2(inferiorLimit);
        
        assertSame(inferiorLimit, kpValueEntry.getInferiorLimit2());
    }
    
    @Test
    public void inferiorLimit3_called() {
        Double inferiorLimit = 9.0;
        
        kpValueEntry.setInferiorLimit3(inferiorLimit);
        
        assertSame(inferiorLimit, kpValueEntry.getInferiorLimit3());
    }
}
