package br.inpe.climaespacial.swd.kp.entities;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;
import br.inpe.climaespacial.swd.kp.entities.KPEntity;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;

@RunWith(CdiRunner.class)
@AdditionalClasses(KPEntity.class)
public class KPEntityTest {

    @Inject
    private KPEntity kpEntity;

    @Test
    public void timeTagTest() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");

        kpEntity.setTimeTag(timeTag);

        assertEquals(timeTag, kpEntity.getTimeTag());
    }
    
    @Test
    public void kpValueListTest() {
        List<KPValueEntity> kpvel = new ArrayList<>();
        
        kpEntity.setKPValueList(kpvel);
        
        assertEquals(kpvel, kpEntity.getKPValueList());
    }
    
    @Test
    public void sumTest() {
        Long value = 30L;
        
        kpEntity.setSum(value);
        
        assertEquals(value, kpEntity.getSum());
    }
    
    @Test
    public void sumFlagTest() {
        KPValueFlag flag = KPValueFlag.DOWN;
        
        kpEntity.setSumFlag(flag);
        
        assertEquals(flag, kpEntity.getSumFlag());
    }
    
    @Test
    public void apTest() {
        Long value = 55L;
        
        kpEntity.setAp(value);
        
        assertEquals(value, kpEntity.getAp());
    }
    
    @Test
    public void cpTest() {
        Double value = 5.5;
        
        kpEntity.setCp(value);
        
        assertEquals(value, kpEntity.getCp());
    }
    
    @Test
    public void mostDisturbedAndQuietDaysTest() {
        String value = "Q0K";
        
        kpEntity.setMostDisturbedAndQuietDays(value);
        
        assertEquals(value, kpEntity.getMostDisturbedAndQuietDays());
    }    
    
    
}

