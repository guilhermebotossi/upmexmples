package br.inpe.climaespacial.swd.kp.values.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.kp.values.dtos.KPValueEntry;

@RunWith(CdiRunner.class)
@AdditionalClasses({HelperFactoryProducer.class, 
    DefaultKPValueEntryFactory.class, 
    KPValueEntry.class})
public class KPValueEntryFactoryTest {

    @Inject
    private KPValueEntryFactory kpValueEntryFactory; 

    @Test
    public void create_called_returnsCalculatedValuesEntity() {
        KPValueEntry kpve = kpValueEntryFactory.create();

        assertNotNull(kpve);
        assertEquals(KPValueEntry.class, kpve.getClass());
    }
}
