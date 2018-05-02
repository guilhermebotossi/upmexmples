package br.inpe.climaespacial.swd.kp.mappers;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

import br.inpe.climaespacial.swd.commons.factories.ListFactory;
import br.inpe.climaespacial.swd.kp.dtos.KP;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.entities.KPEntity;
import br.inpe.climaespacial.swd.kp.acquisition.enums.KPValueFlag;
import br.inpe.climaespacial.swd.kp.factories.KPFactory;
import br.inpe.climaespacial.swd.kp.factories.KPValueFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPMapper.class)
public class KPMapperTest {

    @Mock
    @Produces
    private ListFactory<KP> kpListFactory;

    @Mock
    @Produces
    private ListFactory<KPValue> kpValueListFactory;

    
    @Mock
    @Produces
    private KPFactory kpFactory;
    
    @Mock
    @Produces
    private KPValueFactory kpValueFactory;

    @Inject
    private KPMapper kpMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;
        List<KPEntity> kpel = null;
        try {
            kpMapper.map(kpel);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"kpEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_calledWithEmptyList_returnsEmptyList() {
        List<KPEntity> kpel = mockList(KPEntity.class);
        List<KP> kpl1 = mockList(KP.class);
        when(kpListFactory.create()).thenReturn(kpl1);

        List<KP> kpl2 = kpMapper.map(kpel);

        assertNotNull(kpl2);
        assertThat(kpl2, is(not(empty())));
        verify(kpListFactory).create();
    }

    @Test
    public void map_called_returns() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T15:00:00z[UTC]");

        KPEntity kpe1 = new KPEntity();
        kpe1.setTimeTag(timeTag);

        List<KPEntity> kpel = Arrays.asList(kpe1);

        List<KP> kpl1 = new ArrayList<>();
        when(kpListFactory.create()).thenReturn(kpl1);

        KP kp1 = new KP();
        when(kpFactory.create()).thenReturn(kp1);

        List<KP> kpl2 = kpMapper.map(kpel);

        assertNotNull(kpl2);
        assertThat(kpl2, is(not(empty())));
        assertThat(kpl2, hasSize(1));
        KP kpf = kpl2.get(0);
        assertNotNull(kpf);
        assertEquals(timeTag, kpf.getTimeTag());
        verify(kpListFactory).create();
        verify(kpFactory).create();
    }
    
    @Test
    public void mapString_calledWithNull_throws() {
        RuntimeException re = null;
        String content = null;
        try {
            kpMapper.map(content);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"content\" cannot be null or empty.", re.getMessage());
    }
    
    @Test
    public void mapString_calledWithEmptyString_throws() {
        RuntimeException re = null;
        String content = "";
        try {
            kpMapper.map(content);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"content\" cannot be null or empty.", re.getMessage());
    }
    
    @Test
    public void mapString_calledInvalidContent_throws() {
        String content = "a1b204  3- 4- 5- 5-  3o 1+ 1- 3o   24- D4* 19 1.0";
        RuntimeException re = null;
        try {
            kpMapper.map(content);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"content\" invalid format.", re.getMessage()); 
        
    }
    
    @Test
    public void mapString_calledValue1Missing_succeeds() { 
        String content = "170404  ";
        when(kpListFactory.create()).thenReturn(new ArrayList<>());
        when(kpValueListFactory.create()).thenReturn(new ArrayList<>()); 
        when(kpFactory.create()).thenReturn(new KP());
          
        List<KP> kpl = kpMapper.map(content);      
        
        verify(kpListFactory).create();
        verify(kpFactory).create(); 
        verify(kpValueListFactory).create();
        verify(kpValueFactory, never()).create(any(ZonedDateTime.class), any(Long.class), any(KPValueFlag.class));
        assertNotNull(kpl);   
        assertThat(kpl, hasSize(1));   
         
        KP kp = kpl.get(0); 
        
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-04-04T00:00:00z[UTC]");
        assertEquals(timeTag, kp.getTimeTag());
        assertNotNull(kp.getKPValueList());
        assertThat(kp.getKPValueList(), is(empty()));
    }
    
    @Test
    public void mapString_calledValue2Missing_succeeds() {
        String content = "170404  3- ";
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-04-04T00:00:00z[UTC]");
        when(kpListFactory.create()).thenReturn(new ArrayList<>()); 
        when(kpFactory.create()).thenReturn(new KP());
        when(kpValueListFactory.create()).thenReturn(new ArrayList<>());
        when(kpValueFactory.create(timeTag, 3L, KPValueFlag.DOWN)).thenReturn(createKPValue(timeTag, 3L, KPValueFlag.DOWN));
          
        List<KP> kpl = kpMapper.map(content);   
        
        verify(kpListFactory).create();
        verify(kpFactory).create(); 
        verify(kpValueListFactory).create();
        verify(kpValueFactory).create(timeTag, 3L, KPValueFlag.DOWN);
        assertNotNull(kpl);   
        assertThat(kpl, hasSize(1));  
         
        KP kp = kpl.get(0); 
        
        assertEquals(timeTag, kp.getTimeTag());
        
        assertNotNull(kp.getKPValueList());
        assertThat(kp.getKPValueList(), hasSize(1));

        assertKPValue(timeTag, 3L, KPValueFlag.DOWN, kp.getKPValueList().get(0));
    }
    

    @Test
    public void mapString_calledValue3Missing_succeeds() {
        String content = "170404  3- 4+ ";
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-04-04T00:00:00z[UTC]");
        when(kpListFactory.create()).thenReturn(new ArrayList<>()); 
        when(kpFactory.create()).thenReturn(new KP());
        when(kpValueListFactory.create()).thenReturn(new ArrayList<>());
        when(kpValueFactory.create(timeTag, 3L, KPValueFlag.DOWN)).thenReturn(createKPValue(timeTag, 3L, KPValueFlag.DOWN));
        when(kpValueFactory.create(timeTag.plusHours(3), 4L, KPValueFlag.UP)).thenReturn(createKPValue(timeTag.plusHours(3), 4L, KPValueFlag.UP));
          
        List<KP> kpl = kpMapper.map(content);   
        
        verify(kpListFactory).create();
        verify(kpFactory).create(); 
        verify(kpValueListFactory).create();
        verify(kpValueFactory).create(timeTag, 3L, KPValueFlag.DOWN);
        verify(kpValueFactory).create(timeTag.plusHours(3), 4L, KPValueFlag.UP);
        assertNotNull(kpl);   
        assertThat(kpl, hasSize(1));  
         
        KP kp = kpl.get(0); 
        
        assertEquals(timeTag, kp.getTimeTag());
        
        assertNotNull(kp.getKPValueList());
        assertThat(kp.getKPValueList(), hasSize(2));
        
        assertKPValue(timeTag, 3L, KPValueFlag.DOWN, kp.getKPValueList().get(0));
        assertKPValue(timeTag.plusHours(3), 4L, KPValueFlag.UP, kp.getKPValueList().get(1));
    }
    
    @Test
    public void mapString_calledValue4Missing_succeeds() {
        String content = "170404  3- 4+ 5o ";
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-04-04T00:00:00z[UTC]");
        when(kpListFactory.create()).thenReturn(new ArrayList<>()); 
        when(kpFactory.create()).thenReturn(new KP());
        when(kpValueListFactory.create()).thenReturn(new ArrayList<>());
        when(kpValueFactory.create(timeTag, 3L, KPValueFlag.DOWN)).thenReturn(createKPValue(timeTag, 3L, KPValueFlag.DOWN));
        when(kpValueFactory.create(timeTag.plusHours(3), 4L, KPValueFlag.UP)).thenReturn(createKPValue(timeTag.plusHours(3), 4L, KPValueFlag.UP));
        when(kpValueFactory.create(timeTag.plusHours(6), 5L, KPValueFlag.ZERO)).thenReturn(createKPValue(timeTag.plusHours(6), 5L, KPValueFlag.ZERO));
          
        List<KP> kpl = kpMapper.map(content);   
        
        verify(kpListFactory).create();
        verify(kpFactory).create(); 
        verify(kpValueListFactory).create();
        verify(kpValueFactory).create(timeTag, 3L, KPValueFlag.DOWN);
        verify(kpValueFactory).create(timeTag.plusHours(3), 4L, KPValueFlag.UP);
        verify(kpValueFactory).create(timeTag.plusHours(6), 5L, KPValueFlag.ZERO);
        assertNotNull(kpl);   
        assertThat(kpl, hasSize(1));  
         
        KP kp = kpl.get(0); 
        
        assertEquals(timeTag, kp.getTimeTag());
        
        assertNotNull(kp.getKPValueList());
        assertThat(kp.getKPValueList(), hasSize(3));
        
        assertKPValue(timeTag, 3L, KPValueFlag.DOWN, kp.getKPValueList().get(0));
        assertKPValue(timeTag.plusHours(3), 4L, KPValueFlag.UP, kp.getKPValueList().get(1));
        assertKPValue(timeTag.plusHours(6), 5L, KPValueFlag.ZERO, kp.getKPValueList().get(2));
    }
    
    @Test
    public void mapString_calledValue5Missing_succeeds() {
        String content = "170404  3- 4+ 5o 6+ ";
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-04-04T00:00:00z[UTC]");
        when(kpListFactory.create()).thenReturn(new ArrayList<>()); 
        when(kpFactory.create()).thenReturn(new KP());
        when(kpValueListFactory.create()).thenReturn(new ArrayList<>());
        when(kpValueFactory.create(timeTag, 3L, KPValueFlag.DOWN)).thenReturn(createKPValue(timeTag, 3L, KPValueFlag.DOWN));
        when(kpValueFactory.create(timeTag.plusHours(3), 4L, KPValueFlag.UP)).thenReturn(createKPValue(timeTag.plusHours(3), 4L, KPValueFlag.UP));
        when(kpValueFactory.create(timeTag.plusHours(6), 5L, KPValueFlag.ZERO)).thenReturn(createKPValue(timeTag.plusHours(6), 5L, KPValueFlag.ZERO));
        when(kpValueFactory.create(timeTag.plusHours(9), 6L, KPValueFlag.UP)).thenReturn(createKPValue(timeTag.plusHours(9), 6L, KPValueFlag.UP));
          
        List<KP> kpl = kpMapper.map(content);   
        
        verify(kpListFactory).create();
        verify(kpFactory).create(); 
        verify(kpValueListFactory).create();
        verify(kpValueFactory).create(timeTag, 3L, KPValueFlag.DOWN);
        verify(kpValueFactory).create(timeTag.plusHours(3), 4L, KPValueFlag.UP);
        verify(kpValueFactory).create(timeTag.plusHours(6), 5L, KPValueFlag.ZERO);
        verify(kpValueFactory).create(timeTag.plusHours(9), 6L, KPValueFlag.UP);
        assertNotNull(kpl);   
        assertThat(kpl, hasSize(1));  
         
        KP kp = kpl.get(0); 
        
        assertEquals(timeTag, kp.getTimeTag());
        
        assertNotNull(kp.getKPValueList());
        assertThat(kp.getKPValueList(), hasSize(4));
        
        assertKPValue(timeTag, 3L, KPValueFlag.DOWN, kp.getKPValueList().get(0));
        assertKPValue(timeTag.plusHours(3), 4L, KPValueFlag.UP, kp.getKPValueList().get(1));
        assertKPValue(timeTag.plusHours(6), 5L, KPValueFlag.ZERO, kp.getKPValueList().get(2));
        assertKPValue(timeTag.plusHours(9), 6L, KPValueFlag.UP, kp.getKPValueList().get(3));
    }
    
    @Test
    public void mapString_calledValue6Missing_succeeds() {
        String content = "170404  3- 4+ 5o 6+  7- ";
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-04-04T00:00:00z[UTC]");
        when(kpListFactory.create()).thenReturn(new ArrayList<>()); 
        when(kpFactory.create()).thenReturn(new KP());
        when(kpValueListFactory.create()).thenReturn(new ArrayList<>());
        when(kpValueFactory.create(timeTag, 3L, KPValueFlag.DOWN)).thenReturn(createKPValue(timeTag, 3L, KPValueFlag.DOWN));
        when(kpValueFactory.create(timeTag.plusHours(3), 4L, KPValueFlag.UP)).thenReturn(createKPValue(timeTag.plusHours(3), 4L, KPValueFlag.UP));
        when(kpValueFactory.create(timeTag.plusHours(6), 5L, KPValueFlag.ZERO)).thenReturn(createKPValue(timeTag.plusHours(6), 5L, KPValueFlag.ZERO));
        when(kpValueFactory.create(timeTag.plusHours(9), 6L, KPValueFlag.UP)).thenReturn(createKPValue(timeTag.plusHours(9), 6L, KPValueFlag.UP));
        when(kpValueFactory.create(timeTag.plusHours(12), 7L, KPValueFlag.DOWN)).thenReturn(createKPValue(timeTag.plusHours(12), 7L, KPValueFlag.DOWN));
          
        List<KP> kpl = kpMapper.map(content);    
        
        verify(kpListFactory).create();
        verify(kpFactory).create(); 
        verify(kpValueListFactory).create();
        verify(kpValueFactory).create(timeTag, 3L, KPValueFlag.DOWN);
        verify(kpValueFactory).create(timeTag.plusHours(3), 4L, KPValueFlag.UP);
        verify(kpValueFactory).create(timeTag.plusHours(6), 5L, KPValueFlag.ZERO);
        verify(kpValueFactory).create(timeTag.plusHours(9), 6L, KPValueFlag.UP);
        verify(kpValueFactory).create(timeTag.plusHours(12), 7L, KPValueFlag.DOWN);
        assertNotNull(kpl);   
        assertThat(kpl, hasSize(1));  
         
        KP kp = kpl.get(0); 
        
        assertEquals(timeTag, kp.getTimeTag());
        
        assertNotNull(kp.getKPValueList());
        assertThat(kp.getKPValueList(), hasSize(5));
        
        assertKPValue(timeTag, 3L, KPValueFlag.DOWN, kp.getKPValueList().get(0));
        assertKPValue(timeTag.plusHours(3), 4L, KPValueFlag.UP, kp.getKPValueList().get(1));
        assertKPValue(timeTag.plusHours(6), 5L, KPValueFlag.ZERO, kp.getKPValueList().get(2));
        assertKPValue(timeTag.plusHours(9), 6L, KPValueFlag.UP, kp.getKPValueList().get(3));
        assertKPValue(timeTag.plusHours(12), 7L, KPValueFlag.DOWN, kp.getKPValueList().get(4));
    }
    
    @Test
    public void mapString_calledValue7Missing_succeeds() {
        String content = "170404  3- 4+ 5o 6+  7- 8o ";
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-04-04T00:00:00z[UTC]");
        when(kpListFactory.create()).thenReturn(new ArrayList<>()); 
        when(kpFactory.create()).thenReturn(new KP());
        when(kpValueListFactory.create()).thenReturn(new ArrayList<>());
        when(kpValueFactory.create(timeTag, 3L, KPValueFlag.DOWN)).thenReturn(createKPValue(timeTag, 3L, KPValueFlag.DOWN));
        when(kpValueFactory.create(timeTag.plusHours(3), 4L, KPValueFlag.UP)).thenReturn(createKPValue(timeTag.plusHours(3), 4L, KPValueFlag.UP));
        when(kpValueFactory.create(timeTag.plusHours(6), 5L, KPValueFlag.ZERO)).thenReturn(createKPValue(timeTag.plusHours(6), 5L, KPValueFlag.ZERO));
        when(kpValueFactory.create(timeTag.plusHours(9), 6L, KPValueFlag.UP)).thenReturn(createKPValue(timeTag.plusHours(9), 6L, KPValueFlag.UP));
        when(kpValueFactory.create(timeTag.plusHours(12), 7L, KPValueFlag.DOWN)).thenReturn(createKPValue(timeTag.plusHours(12), 7L, KPValueFlag.DOWN));
        when(kpValueFactory.create(timeTag.plusHours(15), 8L, KPValueFlag.ZERO)).thenReturn(createKPValue(timeTag.plusHours(15), 8L, KPValueFlag.ZERO));
          
        List<KP> kpl = kpMapper.map(content);   
        
        verify(kpListFactory).create();
        verify(kpFactory).create();
        verify(kpValueListFactory).create();
        verify(kpValueFactory).create(timeTag, 3L, KPValueFlag.DOWN);
        verify(kpValueFactory).create(timeTag.plusHours(3), 4L, KPValueFlag.UP);
        verify(kpValueFactory).create(timeTag.plusHours(6), 5L, KPValueFlag.ZERO);
        verify(kpValueFactory).create(timeTag.plusHours(9), 6L, KPValueFlag.UP);
        verify(kpValueFactory).create(timeTag.plusHours(12), 7L, KPValueFlag.DOWN);
        verify(kpValueFactory).create(timeTag.plusHours(15), 8L, KPValueFlag.ZERO);
        assertNotNull(kpl);   
        assertThat(kpl, hasSize(1));  
         
        KP kp = kpl.get(0); 
        
        assertEquals(timeTag, kp.getTimeTag());
        
        assertNotNull(kp.getKPValueList());
        assertThat(kp.getKPValueList(), hasSize(6));
        
        assertKPValue(timeTag, 3L, KPValueFlag.DOWN, kp.getKPValueList().get(0));
        assertKPValue(timeTag.plusHours(3), 4L, KPValueFlag.UP, kp.getKPValueList().get(1));
        assertKPValue(timeTag.plusHours(6), 5L, KPValueFlag.ZERO, kp.getKPValueList().get(2));
        assertKPValue(timeTag.plusHours(9), 6L, KPValueFlag.UP, kp.getKPValueList().get(3));
        assertKPValue(timeTag.plusHours(12), 7L, KPValueFlag.DOWN, kp.getKPValueList().get(4));
        assertKPValue(timeTag.plusHours(15), 8L, KPValueFlag.ZERO, kp.getKPValueList().get(5));
        
    }
      
    @Test
    public void mapString_calledValue8Missing_succeeds() {
        String content = "170404  3- 4+ 5o 6+  7- 8o  9-";
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-04-04T00:00:00z[UTC]");
        when(kpListFactory.create()).thenReturn(new ArrayList<>()); 
        when(kpFactory.create()).thenReturn(new KP());
        when(kpValueListFactory.create()).thenReturn(new ArrayList<>());
        when(kpValueFactory.create(timeTag, 3L, KPValueFlag.DOWN)).thenReturn(createKPValue(timeTag, 3L, KPValueFlag.DOWN));
        when(kpValueFactory.create(timeTag.plusHours(3), 4L, KPValueFlag.UP)).thenReturn(createKPValue(timeTag.plusHours(3), 4L, KPValueFlag.UP));
        when(kpValueFactory.create(timeTag.plusHours(6), 5L, KPValueFlag.ZERO)).thenReturn(createKPValue(timeTag.plusHours(6), 5L, KPValueFlag.ZERO));
        when(kpValueFactory.create(timeTag.plusHours(9), 6L, KPValueFlag.UP)).thenReturn(createKPValue(timeTag.plusHours(9), 6L, KPValueFlag.UP));
        when(kpValueFactory.create(timeTag.plusHours(12), 7L, KPValueFlag.DOWN)).thenReturn(createKPValue(timeTag.plusHours(12), 7L, KPValueFlag.DOWN));
        when(kpValueFactory.create(timeTag.plusHours(15), 8L, KPValueFlag.ZERO)).thenReturn(createKPValue(timeTag.plusHours(15), 8L, KPValueFlag.ZERO));
        when(kpValueFactory.create(timeTag.plusHours(18), 9L, KPValueFlag.DOWN)).thenReturn(createKPValue(timeTag.plusHours(18), 9L, KPValueFlag.DOWN));
          
        List<KP> kpl = kpMapper.map(content); 
        
        verify(kpListFactory).create();
        verify(kpFactory).create(); 
        verify(kpValueListFactory).create();
        verify(kpValueFactory).create(timeTag, 3L, KPValueFlag.DOWN);
        verify(kpValueFactory).create(timeTag.plusHours(3), 4L, KPValueFlag.UP);
        verify(kpValueFactory).create(timeTag.plusHours(6), 5L, KPValueFlag.ZERO);
        verify(kpValueFactory).create(timeTag.plusHours(9), 6L, KPValueFlag.UP);
        verify(kpValueFactory).create(timeTag.plusHours(12), 7L, KPValueFlag.DOWN);
        verify(kpValueFactory).create(timeTag.plusHours(15), 8L, KPValueFlag.ZERO);
        verify(kpValueFactory).create(timeTag.plusHours(18), 9L, KPValueFlag.DOWN);
        assertNotNull(kpl);   
        assertThat(kpl, hasSize(1));  
         
        KP kp = kpl.get(0); 
        
        assertEquals(timeTag, kp.getTimeTag());
        
        assertNotNull(kp.getKPValueList());
        assertThat(kp.getKPValueList(), hasSize(7));
        
        assertKPValue(timeTag, 3L, KPValueFlag.DOWN, kp.getKPValueList().get(0));
        assertKPValue(timeTag.plusHours(3), 4L, KPValueFlag.UP, kp.getKPValueList().get(1));
        assertKPValue(timeTag.plusHours(6), 5L, KPValueFlag.ZERO, kp.getKPValueList().get(2));
        assertKPValue(timeTag.plusHours(9), 6L, KPValueFlag.UP, kp.getKPValueList().get(3));
        assertKPValue(timeTag.plusHours(12), 7L, KPValueFlag.DOWN, kp.getKPValueList().get(4));
        assertKPValue(timeTag.plusHours(15), 8L, KPValueFlag.ZERO, kp.getKPValueList().get(5));
        assertKPValue(timeTag.plusHours(18), 9L, KPValueFlag.DOWN, kp.getKPValueList().get(6));
    }
        

    @Test 
    public void mapString_called_succeeds() { 
        String content = "170404  3- 4+ 5o 6+  7- 8o  9- 1o   24- D4* 19 1.0";
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-04-04T00:00:00z[UTC]");
        when(kpListFactory.create()).thenReturn(new ArrayList<>()); 
        when(kpFactory.create()).thenReturn(new KP());
        when(kpValueListFactory.create()).thenReturn(new ArrayList<>());
        when(kpValueFactory.create(timeTag, 3L, KPValueFlag.DOWN)).thenReturn(createKPValue(timeTag, 3L, KPValueFlag.DOWN));
        when(kpValueFactory.create(timeTag.plusHours(3), 4L, KPValueFlag.UP)).thenReturn(createKPValue(timeTag.plusHours(3), 4L, KPValueFlag.UP));
        when(kpValueFactory.create(timeTag.plusHours(6), 5L, KPValueFlag.ZERO)).thenReturn(createKPValue(timeTag.plusHours(6), 5L, KPValueFlag.ZERO));
        when(kpValueFactory.create(timeTag.plusHours(9), 6L, KPValueFlag.UP)).thenReturn(createKPValue(timeTag.plusHours(9), 6L, KPValueFlag.UP));
        when(kpValueFactory.create(timeTag.plusHours(12), 7L, KPValueFlag.DOWN)).thenReturn(createKPValue(timeTag.plusHours(12), 7L, KPValueFlag.DOWN));
        when(kpValueFactory.create(timeTag.plusHours(15), 8L, KPValueFlag.ZERO)).thenReturn(createKPValue(timeTag.plusHours(15), 8L, KPValueFlag.ZERO));
        when(kpValueFactory.create(timeTag.plusHours(18), 9L, KPValueFlag.DOWN)).thenReturn(createKPValue(timeTag.plusHours(18), 9L, KPValueFlag.DOWN));
        when(kpValueFactory.create(timeTag.plusHours(21), 1L, KPValueFlag.ZERO)).thenReturn(createKPValue(timeTag.plusHours(21), 1L, KPValueFlag.ZERO));
          
        List<KP> kpl = kpMapper.map(content);   
        
        verify(kpListFactory).create();
        verify(kpFactory).create(); 
        verify(kpValueListFactory).create();
        verify(kpValueFactory).create(timeTag, 3L, KPValueFlag.DOWN);
        verify(kpValueFactory).create(timeTag.plusHours(3), 4L, KPValueFlag.UP);
        verify(kpValueFactory).create(timeTag.plusHours(6), 5L, KPValueFlag.ZERO);
        verify(kpValueFactory).create(timeTag.plusHours(9), 6L, KPValueFlag.UP);
        verify(kpValueFactory).create(timeTag.plusHours(12), 7L, KPValueFlag.DOWN);
        verify(kpValueFactory).create(timeTag.plusHours(15), 8L, KPValueFlag.ZERO);
        verify(kpValueFactory).create(timeTag.plusHours(18), 9L, KPValueFlag.DOWN);
        verify(kpValueFactory).create(timeTag.plusHours(21), 1L, KPValueFlag.ZERO);
        assertNotNull(kpl);   
        assertThat(kpl, hasSize(1));  
          
        KP kp = kpl.get(0); 
        
        assertEquals(timeTag, kp.getTimeTag());
        
        assertNotNull(kp.getKPValueList());
        assertThat(kp.getKPValueList(), hasSize(8));
        
        assertKPValue(timeTag, 3L, KPValueFlag.DOWN, kp.getKPValueList().get(0));
        assertKPValue(timeTag.plusHours(3), 4L, KPValueFlag.UP, kp.getKPValueList().get(1));
        assertKPValue(timeTag.plusHours(6), 5L, KPValueFlag.ZERO, kp.getKPValueList().get(2));
        assertKPValue(timeTag.plusHours(9), 6L, KPValueFlag.UP, kp.getKPValueList().get(3));
        assertKPValue(timeTag.plusHours(12), 7L, KPValueFlag.DOWN, kp.getKPValueList().get(4));
        assertKPValue(timeTag.plusHours(15), 8L, KPValueFlag.ZERO, kp.getKPValueList().get(5));
        assertKPValue(timeTag.plusHours(18), 9L, KPValueFlag.DOWN, kp.getKPValueList().get(6));
        assertKPValue(timeTag.plusHours(21), 1L, KPValueFlag.ZERO, kp.getKPValueList().get(7));
        
        assertEquals(Long.valueOf(24), kp.getSum());
        assertEquals(KPValueFlag.DOWN, kp.getSumFlag());
        assertEquals(Long.valueOf(19), kp.getAp());
        assertEquals(1.0, kp.getCp(), 0.01);
        assertEquals("D4*", kp.getMostDisturbedAndQuietDays()); 
    }
    
    @Test
    public void mapString_calledMultiLines_succeeds() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append("170404  3- 4+ 5o 6+  7- 8o  9- 1o   24- D4* 19 1.0").append("\n");
        sb.append("1704                                 Mean 12 0.55").append("\n");
        sb.append("\n");
        sb.append("1704 Q 16  17   3  13  10  30  28  12K 29  18K").append("\n");
        sb.append("1704 D 22  23  20   4* 24*"); 
        String content = sb.toString(); 
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-04-04T00:00:00z[UTC]");
         
        when(kpListFactory.create()).thenReturn(new ArrayList<>());  
        when(kpFactory.create()).thenAnswer(kp -> new KP());
        when(kpValueListFactory.create()).thenReturn(new ArrayList<>());
        when(kpValueFactory.create(timeTag, 3L, KPValueFlag.DOWN)).thenReturn(createKPValue(timeTag, 3L, KPValueFlag.DOWN));
        when(kpValueFactory.create(timeTag.plusHours(3), 4L, KPValueFlag.UP)).thenReturn(createKPValue(timeTag.plusHours(3), 4L, KPValueFlag.UP));
        when(kpValueFactory.create(timeTag.plusHours(6), 5L, KPValueFlag.ZERO)).thenReturn(createKPValue(timeTag.plusHours(6), 5L, KPValueFlag.ZERO));
        when(kpValueFactory.create(timeTag.plusHours(9), 6L, KPValueFlag.UP)).thenReturn(createKPValue(timeTag.plusHours(9), 6L, KPValueFlag.UP));
        when(kpValueFactory.create(timeTag.plusHours(12), 7L, KPValueFlag.DOWN)).thenReturn(createKPValue(timeTag.plusHours(12), 7L, KPValueFlag.DOWN));
        when(kpValueFactory.create(timeTag.plusHours(15), 8L, KPValueFlag.ZERO)).thenReturn(createKPValue(timeTag.plusHours(15), 8L, KPValueFlag.ZERO));
        when(kpValueFactory.create(timeTag.plusHours(18), 9L, KPValueFlag.DOWN)).thenReturn(createKPValue(timeTag.plusHours(18), 9L, KPValueFlag.DOWN));
        when(kpValueFactory.create(timeTag.plusHours(21), 1L, KPValueFlag.ZERO)).thenReturn(createKPValue(timeTag.plusHours(21), 1L, KPValueFlag.ZERO));
          
        List<KP> kpl = kpMapper.map(content);  
        
        verify(kpListFactory).create();
        verify(kpFactory).create(); 
        verify(kpValueListFactory).create();
        verify(kpValueFactory).create(timeTag, 3L, KPValueFlag.DOWN);
        verify(kpValueFactory).create(timeTag.plusHours(3), 4L, KPValueFlag.UP);
        verify(kpValueFactory).create(timeTag.plusHours(6), 5L, KPValueFlag.ZERO);
        verify(kpValueFactory).create(timeTag.plusHours(9), 6L, KPValueFlag.UP);
        verify(kpValueFactory).create(timeTag.plusHours(12), 7L, KPValueFlag.DOWN);
        verify(kpValueFactory).create(timeTag.plusHours(15), 8L, KPValueFlag.ZERO);
        verify(kpValueFactory).create(timeTag.plusHours(18), 9L, KPValueFlag.DOWN);
        verify(kpValueFactory).create(timeTag.plusHours(21), 1L, KPValueFlag.ZERO);
        assertNotNull(kpl);   
        assertThat(kpl, hasSize(1));  
          
        KP kp = kpl.get(0); 
        
        assertEquals(timeTag, kp.getTimeTag());
        
        assertNotNull(kp.getKPValueList());
        assertThat(kp.getKPValueList(), hasSize(8));
        
        assertKPValue(timeTag, 3L, KPValueFlag.DOWN, kp.getKPValueList().get(0));
        assertKPValue(timeTag.plusHours(3), 4L, KPValueFlag.UP, kp.getKPValueList().get(1));
        assertKPValue(timeTag.plusHours(6), 5L, KPValueFlag.ZERO, kp.getKPValueList().get(2));
        assertKPValue(timeTag.plusHours(9), 6L, KPValueFlag.UP, kp.getKPValueList().get(3));
        assertKPValue(timeTag.plusHours(12), 7L, KPValueFlag.DOWN, kp.getKPValueList().get(4));
        assertKPValue(timeTag.plusHours(15), 8L, KPValueFlag.ZERO, kp.getKPValueList().get(5));
        assertKPValue(timeTag.plusHours(18), 9L, KPValueFlag.DOWN, kp.getKPValueList().get(6));
        assertKPValue(timeTag.plusHours(21), 1L, KPValueFlag.ZERO, kp.getKPValueList().get(7));
        
        assertEquals(Long.valueOf(24), kp.getSum());
        assertEquals(KPValueFlag.DOWN, kp.getSumFlag());
        assertEquals(Long.valueOf(19), kp.getAp());
        assertEquals(1.0, kp.getCp(), 0.01);
        assertEquals("D4*", kp.getMostDisturbedAndQuietDays()); 
    }
    
    private void assertKPValue(ZonedDateTime timeTag, Long value, KPValueFlag flag, KPValue kpValue) {
        assertEquals(timeTag, kpValue.getTimeTag());
        assertEquals(value, kpValue.getKPValue());
        assertEquals(flag, kpValue.getKPValueFlag());
    }
    
    private KPValue createKPValue(ZonedDateTime timeTag, Long value, KPValueFlag flag) {
        KPValue kpv = new KPValue();
        kpv.setTimeTag(timeTag);
        kpv.setKPValue(value);
        kpv.setKPValueFlag(flag);
        return kpv;
    }
}

