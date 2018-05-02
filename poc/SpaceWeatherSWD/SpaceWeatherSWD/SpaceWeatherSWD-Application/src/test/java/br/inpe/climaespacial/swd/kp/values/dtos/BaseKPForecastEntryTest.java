package br.inpe.climaespacial.swd.kp.values.dtos;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.time.ZonedDateTime;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultBaseKPForecastEntry.class)
public class BaseKPForecastEntryTest {
    
    
    @Inject 
    private DefaultBaseKPForecastEntry defaultBaseKPForecastEntry;
    
    @Test
    public void timeTag_called() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
       
        defaultBaseKPForecastEntry.setTimeTag(zdt);
        
        assertSame(zdt, defaultBaseKPForecastEntry.getTimeTag());
    }
    
    @Test
    public void presentationTimeTag_called() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T15:00:00z[UTC]");
        defaultBaseKPForecastEntry.setPresentationTimeTag(zdt);
        
        assertSame(zdt, defaultBaseKPForecastEntry.getPresentationTimeTag());
    }
    
    @Test
    public void forecast_called() {
        boolean forecast = true;
        
        defaultBaseKPForecastEntry.setForecast(forecast);
        
        assertTrue(defaultBaseKPForecastEntry.isForecast());
    }

}
