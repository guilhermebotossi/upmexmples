package br.inpe.climaespacial.swd.kp.forecast.factories;

import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultKPForecastFactory.class, 
    KPForecast.class
})
public class KPForecastFactoryTest {

    
    @Inject
    private KPForecastFactory kpForecastFactory;
    
    @Test
    public void create_called_returnsMag() {        
        KPForecast kpf = kpForecastFactory.create();
        
        assertNotNull(kpf); 
        assertEquals(KPForecast.class, kpf.getClass());
    }
}
