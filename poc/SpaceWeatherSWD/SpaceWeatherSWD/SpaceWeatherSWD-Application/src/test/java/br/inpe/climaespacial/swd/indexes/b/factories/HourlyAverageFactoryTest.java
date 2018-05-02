package br.inpe.climaespacial.swd.indexes.b.factories;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, DefaultHourlyAverageFactory.class})
public class HourlyAverageFactoryTest {

    @Inject
    private HourlyAverageFactory hourlyAverageFactory;

    @Test
    public void create_called_returnsInstance() {
        HourlyAverage ha = hourlyAverageFactory.create();

        assertNotNull(ha);
        assertEquals(HourlyAverage.class, ha.getClass());
    }
}
