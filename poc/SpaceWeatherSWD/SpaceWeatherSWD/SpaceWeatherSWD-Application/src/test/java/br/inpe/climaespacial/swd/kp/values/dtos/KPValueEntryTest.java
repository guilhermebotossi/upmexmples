package br.inpe.climaespacial.swd.kp.values.dtos;

import static org.junit.Assert.assertSame;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses(KPValueEntry.class)
public class KPValueEntryTest {
    
    @Inject
    private KPValueEntry kpValueEntry;
    
    @Test
    public void value_called() {
        Double value = 2.0;
        
        kpValueEntry.setValue(value);
        
        assertSame(value, kpValueEntry.getValue());
    }

}
