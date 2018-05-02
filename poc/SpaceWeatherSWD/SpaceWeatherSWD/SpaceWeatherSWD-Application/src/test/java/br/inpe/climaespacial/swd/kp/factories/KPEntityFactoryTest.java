package br.inpe.climaespacial.swd.kp.factories;

import br.inpe.climaespacial.swd.acquisition.providers.DateTimeProvider;
import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.commons.providers.UUIDProvider;
import br.inpe.climaespacial.swd.kp.entities.KPEntity;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.mockito.Mock;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultKPEntityFactory.class, 
    KPEntity.class
})
public class KPEntityFactoryTest {
    
    @Produces
    @Mock
    private UUIDProvider uuidProvider;

    @Produces
    @Mock
    private DateTimeProvider dateTimeProvider;
    
    @Inject
    private KPEntityFactory kpEntityFactory;
    
    @Test
    public void create_called_returns() {        
        KPEntity kpe = kpEntityFactory.create();
        
        assertNotNull(kpe); 
        assertEquals(KPEntity.class, kpe.getClass());
    }
}
