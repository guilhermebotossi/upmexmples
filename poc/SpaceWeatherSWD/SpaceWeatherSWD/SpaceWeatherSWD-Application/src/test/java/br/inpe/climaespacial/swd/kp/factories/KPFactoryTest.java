package br.inpe.climaespacial.swd.kp.factories;

import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.kp.dtos.KP;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultKPFactory.class, 
    KP.class
})
public class KPFactoryTest {
    
    @Inject
    private KPFactory kpFactory;
    
    @Test
    public void create_called_returnsMag() {        
        KP kp = kpFactory.create();
        
        assertNotNull(kp); 
        assertEquals(KP.class, kp.getClass());
    }
}
