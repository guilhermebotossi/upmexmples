package br.inpe.climaespacial.swd.kp.acquisition.enums;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class KPValueFlagTest {
    
    @Test
    public void getEnumByValue_calledWithNonExistingValue() {
        KPValueFlag flag = KPValueFlag.getEnumByValue("#");
        
        assertNull(flag);
    }
    
    @Test
    public void getEnumByValue_calledWithPlusSign_returnUP() {
        KPValueFlag flag = KPValueFlag.getEnumByValue("+");
        
        assertNotNull(flag);
        assertSame(KPValueFlag.UP, flag);
    }

    
    @Test
    public void getEnumByValue_calledWithMinusSign_returnDown() {
        KPValueFlag flag = KPValueFlag.getEnumByValue("-");
        
        assertNotNull(flag);
        assertSame(KPValueFlag.DOWN, flag);
    }
    
    @Test
    public void getEnumByValue_calledWithLetterO_returnZero() {
        KPValueFlag flag = KPValueFlag.getEnumByValue("o");
        
        assertNotNull(flag);
        assertSame(KPValueFlag.ZERO, flag);
    }
}
