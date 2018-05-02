package br.inpe.climaespacial.swd.kp.forecast.mappers;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;
import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;
import br.inpe.climaespacial.swd.kp.factories.KPValueFactory;
import br.inpe.climaespacial.swd.kp.forecast.repositories.KPValueMapper;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPValueMapper.class)
public class KPValueMapperTest {
    
    @Mock
    @Produces
    private KPValueFactory kpValueFactory;
    
    @Mock
    @Produces
    private ListFactory<KPValue> kpValueListFactory;

    @Inject
    private KPValueMapper kpValueMapper;
    
    @Test
    public void map_called_returnNull() {
        KPValueEntity kpve1 = null;
        
        KPValue kpv = kpValueMapper.map(kpve1);
         
        assertNull(kpv);
        verify(kpValueFactory, never()).create(any(ZonedDateTime.class), any(Long.class), any(KPValueFlag.class));
    }
    
    @Test 
    public void map_called_returnKPValue() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T15:00:00z[UTC]"); 
        Long value = 1L;
        KPValueFlag flag = KPValueFlag.ZERO;
        KPValueEntity kpve = createKPValueEntity(timeTag, value, flag);
        KPValue kpv1 = createKPValue(timeTag, value, flag);
        
        when(kpValueFactory.create(timeTag, value, flag)).thenReturn(kpv1);
        
        KPValue kpv = kpValueMapper.map(kpve);
        
        assertNotNull(kpv);
        verify(kpValueFactory).create(timeTag, value, flag);
    } 
    
    
    @Test
    public void mapList_called_throws() {
        List<KPValueEntity> kpvel = null;
        RuntimeException re = null;
        
        try {
            kpValueMapper.map(kpvel);
        } catch(RuntimeException e) {
            re = e;
        }
         
        assertNotNull(re);
        assertEquals("Argument \"kpValueEntityList\" cannot be null.", re.getMessage()); 
        verify(kpValueFactory, never()).create(any(ZonedDateTime.class), any(Long.class), any(KPValueFlag.class));
    }
    
    @Test
    public void mapList_called_returnsList() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T15:00:00z[UTC]"); 
        Long value = 1L;
        KPValueFlag flag = KPValueFlag.ZERO;
        KPValueEntity kpve = createKPValueEntity(timeTag, value, flag);
        List<KPValueEntity> kpvel = Arrays.asList(kpve);
        KPValue kpv1 = createKPValue(timeTag, value, flag);
        
        when(kpValueListFactory.create()).thenReturn(new ArrayList<KPValue>());
        when(kpValueFactory.create(timeTag, value, flag)).thenReturn(kpv1);
        
        List<KPValue> kpvl = kpValueMapper.map(kpvel);
        
        assertNotNull(kpvl);
        assertThat(kpvl, hasSize(1));
        verify(kpValueListFactory).create();
        verify(kpValueFactory).create(timeTag, value, flag);
        
    }

    private KPValue createKPValue(ZonedDateTime timeTag, Long value, KPValueFlag flag) {
        KPValue kpv = new KPValue();
        kpv.setTimeTag(timeTag);
        kpv.setKPValue(value);
        kpv.setKPValueFlag(flag);
        return kpv;
    }

    private KPValueEntity createKPValueEntity(ZonedDateTime timeTag, Long value, KPValueFlag flag) {
        KPValueEntity kpve = new KPValueEntity();
        kpve.setTimeTag(timeTag);
        kpve.setKPValue(value);
        kpve.setKPValueFlag(flag);
        return kpve;
    }
}
