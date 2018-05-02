package br.inpe.climaespacial.swd.values.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.values.dtos.ValueEntry;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultValueEntryFactory.class, 
                    HelperFactoryProducer.class})
public class ValueEntryFactoryTest {
    
    @Inject
    private ValueEntryFactory valueEntryFactory;
    
    @Test
    public void nothing() {
        
        ValueEntry ve = valueEntryFactory.create();
        
        assertNotNull(ve); 
        assertEquals(ValueEntry.class, ve.getClass());
    }

}
