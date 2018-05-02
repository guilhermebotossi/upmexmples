package br.inpe.climaespacial.swd.kp.mappers;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
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

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.kp.dtos.KP;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.entities.KPEntity;
import br.inpe.climaespacial.swd.kp.entities.KPValueEntity;
import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;
import br.inpe.climaespacial.swd.kp.factories.KPEntityFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPEntityMapper.class)
public class KPEntityMapperTest {
    
    private static final double DELTA = 0.001;

    @Mock
    @Produces
    private ListFactory<KPEntity> kpEntityListFactory;
    
    @Mock
    @Produces
    private KPEntityFactory kpEntityFactory;
    
    @Mock
    @Produces 
    private KPValueEntityMapper kpValueEntityMapper;

    @Inject
    private KPEntityMapper kpEntityMapper;

    @Test
    public void map_calledWithNull_throwsRuntimeException() {
        RuntimeException re = null;

        try {
            kpEntityMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"kpList\" cannot be null.", re.getMessage());
    }
    
    
    @Test
    public void map_calledWithTimetagNull_throwsRuntimeException() {
        RuntimeException re = null;
       
        KP kp = new KP();
        kp.setTimeTag(null);
        
        List<KP> kpl = Arrays.asList(kp);
        
        try {
            kpEntityMapper.map(kpl);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Mapping KP to KPEntity: Timetag will a null content.", re.getMessage());
    }

    @Test
    public void map_calledWithEmptyList_returnsEmptyList() {
        List<KP> kpl = mockList(KP.class);
        List<KPEntity> kpel = mockList(KPEntity.class);
        when(kpEntityListFactory.create()).thenReturn(kpel);

        List<KPEntity> map = kpEntityMapper.map(kpl);

        assertNotNull(map);
        verify(kpEntityListFactory).create();
    }

    @Test
    public void map_called_returnsKPEntity() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T00:00:00z[UTC]");
        Long valor1 = 1L;
        Long valor2 = 2L;
        Double valor3 = 2.0;
        String mostDisturbedDay = "25";
       
        KP kp = new KP();
        kp.setTimeTag(timeTag);
        kp.setAp(valor1);
        kp.setCp(valor3);
        List<KPValue> kpvl = new ArrayList<>();
        kpvl.add(createKPValue(timeTag, 1L, KPValueFlag.UP));
        kpvl.add(createKPValue(timeTag.plusHours(3), 2L, KPValueFlag.DOWN));
        kpvl.add(createKPValue(timeTag.plusHours(6), 3L, KPValueFlag.ZERO));
        kpvl.add(createKPValue(timeTag.plusHours(9), 4L, KPValueFlag.DOWN));
        kpvl.add(createKPValue(timeTag.plusHours(12), 5L, KPValueFlag.UP));
        kpvl.add(createKPValue(timeTag.plusHours(15), 6L, KPValueFlag.ZERO));
        kpvl.add(createKPValue(timeTag.plusHours(18), 7L, KPValueFlag.DOWN));
        kpvl.add(createKPValue(timeTag.plusHours(21), 8L, KPValueFlag.UP));
        kp.setKPValueList(kpvl);
        kp.setSum(valor2);
        kp.setSumFlag(KPValueFlag.ZERO);
        kp.setMostDisturbedAndQuietDays(mostDisturbedDay);
        
        List<KP> kpl = Arrays.asList(kp);
        
        when(kpEntityListFactory.create()).thenReturn(new ArrayList<KPEntity>());
        KPEntity kpe1 = new KPEntity();
        when(kpEntityFactory.create()).thenReturn(kpe1);
        when(kpValueEntityMapper.map(kpvl, kpe1)).thenReturn(createKPValueEntityList(kpvl));
        
        List<KPEntity> kpel2 = kpEntityMapper.map(kpl);
        
        KPEntity kpe2 = kpel2.get(0);
        assertNotNull(kpe2);
        assertEquals(timeTag, kpe2.getTimeTag());
        assertNotNull(kpe2.getKPValueList());
        assertThat(kpe2.getKPValueList(), hasSize(8));
        assertKPValue(timeTag, 1L, KPValueFlag.UP, kpe2.getKPValueList().get(0)); 
        assertKPValue(timeTag.plusHours(3), 2L, KPValueFlag.DOWN, kpe2.getKPValueList().get(1));
        assertKPValue(timeTag.plusHours(6), 3L, KPValueFlag.ZERO, kpe2.getKPValueList().get(2));
        assertKPValue(timeTag.plusHours(9), 4L, KPValueFlag.DOWN, kpe2.getKPValueList().get(3));
        assertKPValue(timeTag.plusHours(12), 5L, KPValueFlag.UP, kpe2.getKPValueList().get(4));
        assertKPValue(timeTag.plusHours(15), 6L, KPValueFlag.ZERO, kpe2.getKPValueList().get(5));
        assertKPValue(timeTag.plusHours(18), 7L, KPValueFlag.DOWN, kpe2.getKPValueList().get(6));
        assertKPValue(timeTag.plusHours(21), 8L, KPValueFlag.UP, kpe2.getKPValueList().get(7));
        assertEquals(valor2, kpe2.getSum());
        assertEquals(KPValueFlag.ZERO, kpe2.getSumFlag());
        assertEquals(mostDisturbedDay, kpe2.getMostDisturbedAndQuietDays());
        verify(kpEntityListFactory).create();
        verify(kpEntityFactory).create();
        verify(kpValueEntityMapper).map(kpvl, kpe1);
    }

    private List<KPValueEntity> createKPValueEntityList(List<KPValue> kpValueList) {
        List<KPValueEntity> kpvel = new ArrayList<>();
        for(KPValue kpv : kpValueList) {
            kpvel.add(createKPValueEntity(kpv));
        }
        return kpvel;
    }

    private void assertKPValue(ZonedDateTime timeTag, Long value, KPValueFlag flag, KPValueEntity kpValueEntity) {
        assertEquals(timeTag, kpValueEntity.getTimeTag());
        assertEquals(value, kpValueEntity.getKPValue(), DELTA);
        assertEquals(flag, kpValueEntity.getKPValueFlag());
    }

    private KPValue createKPValue(ZonedDateTime timeTag, Long value, KPValueFlag flag) {
        KPValue kpv = new KPValue();
        kpv.setTimeTag(timeTag);
        kpv.setKPValue(value);
        kpv.setKPValueFlag(flag);
        return kpv;
    }

    
    private KPValueEntity createKPValueEntity(KPValue kpValue) {
        KPValueEntity kpve = new KPValueEntity();
        kpve.setTimeTag(kpValue.getTimeTag());
        kpve.setKPValue(kpValue.getKPValue());
        kpve.setKPValueFlag(kpValue.getKPValueFlag());
        
        return kpve;
    }
}
