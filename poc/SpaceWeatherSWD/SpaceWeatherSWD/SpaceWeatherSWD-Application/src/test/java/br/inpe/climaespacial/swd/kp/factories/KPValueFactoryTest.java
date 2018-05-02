package br.inpe.climaespacial.swd.kp.factories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.time.ZonedDateTime;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.commons.factories.HelperFactoryProducer;
import br.inpe.climaespacial.swd.kp.dtos.KP;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;

@RunWith(CdiRunner.class)
@AdditionalClasses({
    HelperFactoryProducer.class,
    DefaultKPValueFactory.class, 
    KP.class
})
public class KPValueFactoryTest {
    
    @Inject
    private KPValueFactory kpValueFactory;
    
    @Test
    public void create_calledWithNullDateTime_throws() { 
        RuntimeException re = null;
        ZonedDateTime timeTag = null;
        Long kpValue = null;
        KPValueFlag flag = null;
        
        try {
        kpValueFactory.create(timeTag, kpValue, flag);
        } catch(RuntimeException e) {
            re = e;
        }
        
        
        assertNotNull(re);  
        assertEquals("Argument \"timeTag\" cannot be null.", re.getMessage());
    }
    
    @Test
    public void create_calledWithNullValue_throws() { 
        RuntimeException re = null;
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T15:00:00z[UTC]");
        Long kpValue = null;
        KPValueFlag flag = null;
        
        try {
        kpValueFactory.create(timeTag, kpValue, flag);
        } catch(RuntimeException e) {
            re = e;
        }
        
        
        assertNotNull(re);  
        assertEquals("Argument \"kpValue\" cannot be null.", re.getMessage());
    }
    
    @Test
    public void create_calledWithNullFlag_throws() { 
        RuntimeException re = null;
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T15:00:00z[UTC]");
        Long kpValue = 1L;
        KPValueFlag flag = null;
        
        try {
        kpValueFactory.create(timeTag, kpValue, flag);
        } catch(RuntimeException e) {
            re = e;
        }
        
        
        assertNotNull(re);  
        assertEquals("Argument \"flag\" cannot be null.", re.getMessage());
    }
    
    @Test
    public void create_called_succeeds() { 
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T15:00:00z[UTC]");
        Long kpValue = 1L;
        KPValueFlag flag = KPValueFlag.UP;
        
        KPValue kpv = kpValueFactory.create(timeTag, kpValue, flag);
        
        assertNotNull(kpv);  
        assertSame(timeTag, kpv.getTimeTag());
        assertSame(kpValue, kpv.getKPValue());
        assertSame(flag, kpv.getKPValueFlag());
    }
}
