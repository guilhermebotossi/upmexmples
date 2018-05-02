package br.inpe.climaespacial.swd.kp.entities;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;

@RunWith(CdiRunner.class)
@AdditionalClasses(KPValueEntity.class)
public class KPValueEntityTest {

    @Inject
    private KPValueEntity kpValueEntity;

    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        kpValueEntity.setTimeTag(timeTag);

        assertEquals(timeTag, kpValueEntity.getTimeTag());
    }
    
    public void kpValue1Test() {
        Long value = 1L;
        
        kpValueEntity.setKPValue(value);
        
        assertEquals(value, kpValueEntity.getKPValue());
    }
    
    public void kpValue1FlagTest() {
        KPValueFlag flag = KPValueFlag.UP;
        
        kpValueEntity.setKPValueFlag(flag);
        
        assertEquals(flag, kpValueEntity.getKPValueFlag());
    }
}

