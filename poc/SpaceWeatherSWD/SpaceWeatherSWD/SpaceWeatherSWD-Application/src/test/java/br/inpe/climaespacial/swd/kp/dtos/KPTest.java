package br.inpe.climaespacial.swd.kp.dtos;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;
import br.inpe.climaespacial.swd.kp.dtos.KP;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;

import static org.junit.Assert.assertEquals;

@RunWith(CdiRunner.class)
@AdditionalClasses(KP.class)
public class KPTest {

    @Inject
    private KP kp;


    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        kp.setTimeTag(timeTag);

        assertEquals(timeTag, kp.getTimeTag());
    }
    
    public void kpValueListTest() {
        List<KPValue> kpvl = new ArrayList<>();
        
        kp.setKPValueList(kpvl);
        
        assertEquals(kpvl, kp.getKPValueList());
    }
    
    public void sumTest() {
        Long value = 30L;
        
        kp.setSum(value);
        
        assertEquals(value, kp.getSum());
    }
    
    public void sumFlagTest() {
        KPValueFlag flag = KPValueFlag.DOWN;
        
        kp.setSumFlag(flag);
        
        assertEquals(flag, kp.getSumFlag());
    }
    
    public void apTest() {
        Long value = 55L;
        
        kp.setAp(value);
        
        assertEquals(value, kp.getAp());
    }
    
    public void cpTest() {
        Double value = 5.5;
        
        kp.setCp(value);
        
        assertEquals(value, kp.getCp());
    }
    
    public void mostDisturbedAndQuietDaysTest() {
        String value = "Q0K";
        
        kp.setMostDisturbedAndQuietDays(value);
        
        assertEquals(value, kp.getMostDisturbedAndQuietDays());
    }    
    
}
