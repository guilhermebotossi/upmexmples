package br.inpe.climaespacial.swd.kp.mappers;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.entities.KPEntity;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;
import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;
import br.inpe.climaespacial.swd.kp.factories.KPEntityFactory;
import br.inpe.climaespacial.swd.kp.factories.KPValueEntityFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPValueEntityMapper.class)
public class KPValueEntityMapperTest {
    
    private static final double DELTA = 0.001;

    @Mock
    @Produces
    private ListFactory<KPValueEntity> kpValueEntityListFactory;

    @Mock
    @Produces
    private KPValueEntityFactory kpValueEntityFactory;
    
    @Mock
    @Produces
    private KPEntityFactory kpEntityFactory;

    @Inject
    private KPValueEntityMapper kpValueEntityMapper;

    @Test
    public void map_calledWithNull_throwsRuntimeException() {
        RuntimeException re = null;

        try {
            kpValueEntityMapper.map(null, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"kpValueList\" cannot be null.", re.getMessage());
    }
    
    @Test
    public void map_calledWithTimetagNull_throwsRuntimeException() {
        RuntimeException re = null;
       
        KPValue kpv = new KPValue();
        kpv.setTimeTag(null);
        
        List<KPValue> kpvl = Arrays.asList(kpv);
        KPEntity kpe = mock(KPEntity.class);
        
        try {
            kpValueEntityMapper.map(kpvl, kpe);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Property \"KPValue.timeTag\" null.", re.getMessage());
    }

    @Test
    public void map_calledWithIDNull_throwsRuntimeException() {
        RuntimeException re = null;
       
        
        List<KPValue> kpvl = Arrays.asList(mock(KPValue.class));
        
        try {
            kpValueEntityMapper.map(kpvl, null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"kpEntity\" cannot be null.", re.getMessage()); 
    }
    @Test
    public void map_calledWithEmptyList_returnsEmptyList() {
        List<KPValue> kpvl = new ArrayList<>();
        List<KPValueEntity> kpvel = new ArrayList<>();
        when(kpValueEntityListFactory.create()).thenReturn(kpvel);
        KPEntity kpe = mock(KPEntity.class);        
        
        List<KPValueEntity> map = kpValueEntityMapper.map(kpvl, kpe);

        assertNotNull(map);
        assertThat(map, is(empty()));
        verify(kpValueEntityListFactory).create();
    }

    @Test
    public void map_called_returnsKPEntity() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T15:00:00z[UTC]");
        List<KPValue> kpvl = new ArrayList<>();
        kpvl.add(createKPValue(timeTag, 1L, KPValueFlag.UP));
        kpvl.add(createKPValue(timeTag.plusHours(3), 2L, KPValueFlag.DOWN));
        kpvl.add(createKPValue(timeTag.plusHours(6), 3L, KPValueFlag.ZERO));
        kpvl.add(createKPValue(timeTag.plusHours(9), 4L, KPValueFlag.DOWN));
        kpvl.add(createKPValue(timeTag.plusHours(12), 5L, KPValueFlag.UP));
        kpvl.add(createKPValue(timeTag.plusHours(15), 6L, KPValueFlag.ZERO));
        kpvl.add(createKPValue(timeTag.plusHours(18), 7L, KPValueFlag.DOWN));
        kpvl.add(createKPValue(timeTag.plusHours(21), 8L, KPValueFlag.UP));
        
        when(kpValueEntityListFactory.create()).thenReturn(new ArrayList<KPValueEntity>());
        when(kpValueEntityFactory.create()).thenAnswer(kpve -> new KPValueEntity());
        KPEntity kpe1 = new KPEntity();
        UUID kpId = UUID.fromString("672e0316-df1f-4589-a1fe-9b8abc1fc281");
        kpe1.setId(kpId);
        
        List<KPValueEntity> kpvel = kpValueEntityMapper.map(kpvl, kpe1);
        
        KPValueEntity kpe2 = kpvel.get(0);
        assertNotNull(kpe2);
        assertEquals(timeTag, kpe2.getTimeTag());
        assertKPValue(timeTag, 1L, KPValueFlag.UP, kpId, kpvel.get(0));
        assertKPValue(timeTag.plusHours(3), 2L, KPValueFlag.DOWN, kpId, kpvel.get(1));
        assertKPValue(timeTag.plusHours(6), 3L, KPValueFlag.ZERO, kpId, kpvel.get(2));
        assertKPValue(timeTag.plusHours(9), 4L, KPValueFlag.DOWN, kpId, kpvel.get(3));
        assertKPValue(timeTag.plusHours(12), 5L, KPValueFlag.UP, kpId ,kpvel.get(4));
        assertKPValue(timeTag.plusHours(15), 6L, KPValueFlag.ZERO, kpId, kpvel.get(5));
        assertKPValue(timeTag.plusHours(18), 7L, KPValueFlag.DOWN, kpId, kpvel.get(6));
        assertKPValue(timeTag.plusHours(21), 8L, KPValueFlag.UP, kpId, kpvel.get(7));
        verify(kpValueEntityListFactory).create();
        verify(kpValueEntityFactory, times(8)).create();
    }

    private void assertKPValue(ZonedDateTime timeTag, Long value, KPValueFlag flag, UUID kpId, KPValueEntity kpValueEntity) {
        assertEquals(timeTag, kpValueEntity.getTimeTag());
        assertEquals(value, kpValueEntity.getKPValue(), DELTA);
        assertEquals(flag, kpValueEntity.getKPValueFlag());
        assertEquals(kpId, kpValueEntity.getKPEntity().getId());
        
    }

    private KPValue createKPValue(ZonedDateTime timeTag, Long value, KPValueFlag flag) {
        KPValue kpv = new KPValue();
        kpv.setTimeTag(timeTag);
        kpv.setKPValue(value);
        kpv.setKPValueFlag(flag);
        return kpv;
    }

}
