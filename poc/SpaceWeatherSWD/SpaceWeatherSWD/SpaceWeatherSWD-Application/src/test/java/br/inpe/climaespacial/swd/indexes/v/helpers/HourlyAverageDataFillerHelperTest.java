package br.inpe.climaespacial.swd.indexes.v.helpers;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.indexes.b.factories.DefaultHourlyAverageFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses({TestableDefaultHourlyAverageDataFillerHelper.class,
                    DefaultHourlyAverageDataFillerHelper.class,
                    DefaultHourlyAverageFactory.class,
                    DefaultListFactory.class,
                    HelperFactoryProducer.class
})
public class HourlyAverageDataFillerHelperTest {

    
    @Inject    
    private TestableDefaultHourlyAverageDataFillerHelper testableDefaultHourlyAverageDataFillerHelper;
    
    @Test
    public void create_called_suceedds() {
        HourlyAverage hourlyAverage = testableDefaultHourlyAverageDataFillerHelper.create();
        
        assertNotNull(hourlyAverage);
        assertEquals(hourlyAverage.getClass(), HourlyAverage.class);
    }
}
