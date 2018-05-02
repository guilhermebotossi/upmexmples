package br.inpe.climaespacial.swd.indexes.v.helpers;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.factories.DefaultVIndexFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses({TestableDefaultVIndexDataFillerHelper.class,
                    DefaultVIndexDataFillerHelper.class,
                    DefaultVIndexFactory.class,
                    DefaultListFactory.class,
                    HelperFactoryProducer.class
})
public class VindexDataFillerHelperTest {

    @Inject    
    private TestableDefaultVIndexDataFillerHelper testableDefaultVIndexDataFillerHelper;
    
    @Test
    public void create_called_suceedds() {
        VIndex vIndex = testableDefaultVIndexDataFillerHelper.create();
        
        assertNotNull(vIndex);
        assertEquals(vIndex.getClass(), VIndex.class);
    }
}
