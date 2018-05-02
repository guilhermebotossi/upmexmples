
package br.inpe.climaespacial.swd.acquisition.factories;

import br.inpe.climaespacial.swd.acquisition.dtos.Mag;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
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
    DefaultMagFactory.class, 
    Mag.class
})
public class MagFactoryTest {
    
    @Inject
    private MagFactory magFactory;
    
    @Test
    public void create_called_returnsMag() {        
        Mag m = magFactory.create();
        
        assertNotNull(m);
        assertEquals(Mag.class, m.getClass());
    }

}
