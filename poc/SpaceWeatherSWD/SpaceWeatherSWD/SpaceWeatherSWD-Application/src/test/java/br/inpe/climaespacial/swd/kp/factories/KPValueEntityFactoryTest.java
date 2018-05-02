package br.inpe.climaespacial.swd.kp.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.acquisition.providers.DefaultDateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.DefaultEntityFactory;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.DefaultUUIDProvider;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultKPValueEntityFactory.class, 
    DefaultEntityFactory.class,
    DefaultDateTimeProvider.class,
    DefaultUUIDProvider.class,
    KPValue.class
})
public class KPValueEntityFactoryTest {
    
    @Inject
    private KPValueEntityFactory kpValueEntityFactory;
    
    
    @Test
    public void create_called_succeeds() { 
        KPValueEntity kpve = kpValueEntityFactory.create();
        
        
        assertNotNull(kpve);  
        assertEquals(KPValueEntity.class, kpve.getClass());
    }
}
