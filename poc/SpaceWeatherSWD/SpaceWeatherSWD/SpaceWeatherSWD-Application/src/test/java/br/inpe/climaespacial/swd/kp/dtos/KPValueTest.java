package br.inpe.climaespacial.swd.kp.dtos;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;

@RunWith(CdiRunner.class)
@AdditionalClasses(KPValue.class)
public class KPValueTest {
    
    @Inject
    private KPValue kpValue;
    
    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        kpValue.setTimeTag(timeTag);

        assertEquals(timeTag, kpValue.getTimeTag());
    }
    
    public void kpValue1Test() {
        Long value = 1L;
        
        kpValue.setKPValue(value);
        
        assertEquals(value, kpValue.getKPValue());
    }
    
    public void kpValue1FlagTest() {
        KPValueFlag flag = KPValueFlag.UP;
        
        kpValue.setKPValueFlag(flag);
        
        assertEquals(flag, kpValue.getKPValueFlag());
    }
    
}
