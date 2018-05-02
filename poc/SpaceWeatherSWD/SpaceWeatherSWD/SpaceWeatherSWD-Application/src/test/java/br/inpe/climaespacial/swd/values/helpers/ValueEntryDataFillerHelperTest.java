package br.inpe.climaespacial.swd.values.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.values.dtos.ValueEntry;
import br.inpe.climaespacial.swd.values.factories.DefaultValueEntryFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses({TestableDefaultValueEntryDataFillerHelper.class,
                    DefaultValueEntryDataFillerHelper.class,
                    DefaultValueEntryFactory.class,
                    DefaultListFactory.class,
                    HelperFactoryProducer.class
})
public class ValueEntryDataFillerHelperTest {
    
    @Inject    
    private TestableDefaultValueEntryDataFillerHelper testableDefaultValueEntryDataFillerHelper;
    
    @Test
    public void create_called_suceedds() {
        ValueEntry valueEntry = testableDefaultValueEntryDataFillerHelper.create();
        
        assertNotNull(valueEntry);
        assertEquals(valueEntry.getClass(), ValueEntry.class); 
    }
}
