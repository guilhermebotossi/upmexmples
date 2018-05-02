package br.inpe.climaespacial.swd.kp.values.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.kp.values.dtos.KPForecastEntry;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, 
    DefaultKPForecastEntryFactory.class, 
    KPForecastEntry.class})
public class KPForecastEntryFactoryTest {

    @Inject
    private KPForecastEntryFactory kpForecastEntryFactory; 

    @Test
    public void create_called_returnsCalculatedValuesEntity() {
        KPForecastEntry kpfe = kpForecastEntryFactory.create();

        assertNotNull(kpfe);
        assertEquals(KPForecastEntry.class, kpfe.getClass());
    }
}
