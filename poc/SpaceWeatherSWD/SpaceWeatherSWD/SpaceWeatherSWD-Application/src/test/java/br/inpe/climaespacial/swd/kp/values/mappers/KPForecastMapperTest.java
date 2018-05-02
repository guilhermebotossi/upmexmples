package br.inpe.climaespacial.swd.kp.values.mappers;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
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
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.forecast.entities.KPForecastEntity;
import br.inpe.climaespacial.swd.kp.forecast.factories.KPForecastFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPForecastMapper.class)
public class KPForecastMapperTest {
    
    @Mock
    @Produces
    private ListFactory<KPForecast> kpForecastList;
    
    @Mock
    @Produces
    private KPForecastFactory kpForecastFactory;
    
    @Inject
    private KPForecastMapper kpForecastMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;
        
        try {
            kpForecastMapper.map(null);
        } catch(RuntimeException e) {
            re = e;
        }
        
        assertNotNull(re); 
        assertEquals("Argument \"kpForecastEntityList\" cannot be null.", re.getMessage());
    }
    
    @Test
    public void map_calledWithEmptyList_returnsEmptyList() {
        List<KPForecastEntity> kpfel = mockList(KPForecastEntity.class);
        List<KPForecast> kpfl = mockList(KPForecast.class);
        when(kpForecastList.create()).thenReturn(kpfl);
        
        List<KPForecast> map = kpForecastMapper.map(kpfel);
        
       assertNotNull(map);
       assertThat(map, is(not(empty())));
       verify(kpForecastList).create();
    }
    
    @Test
    public void map_called_returns() {
        KPForecastEntity kpfe1 = new KPForecastEntity();
        ZonedDateTime predictedTimeTag = ZonedDateTime.parse("2017-01-01T15:00:00z[UTC]");
        ZonedDateTime indexesTimeTag = ZonedDateTime.parse("2017-01-01T15:00:00z[UTC]");
        kpfe1.setPredictedTimeTag(predictedTimeTag);
        kpfe1.setIndexesTimeTag(indexesTimeTag);
        
        Double probability1 = 1.0;
        kpfe1.setProbability1(probability1);
        Double probability2 = 2.0;
        kpfe1.setProbability2(probability2);
        Double probability3 = 3.0;
        kpfe1.setProbability3(probability3);
        Double inferiorLimit1 = 4.0;
        kpfe1.setInferiorLimit1(inferiorLimit1);
        Double inferiorLimit2 = 5.0;
        kpfe1.setInferiorLimit2(inferiorLimit2);
        Double inferiorLimit3 = 6.0;
        kpfe1.setInferiorLimit3(inferiorLimit3);
        Double upperLimit1 = 1.0;
        kpfe1.setUpperLimit1(upperLimit1);
        Double upperLimit2 = 1.0;
        kpfe1.setUpperLimit2(upperLimit2);
        Double upperLimit3 = 1.0;
        kpfe1.setUpperLimit3(upperLimit3);
        
        List<KPForecastEntity> kpfel = Arrays.asList(kpfe1);
        
        List<KPForecast> kpfl = new ArrayList<>();
        when(kpForecastList.create()).thenReturn(kpfl);
        
        KPForecast kpf1 = new KPForecast();
        when(kpForecastFactory.create()).thenReturn(kpf1);
        
        List<KPForecast> map = kpForecastMapper.map(kpfel);
        
       assertNotNull(map);
       assertThat(map, is(not(empty())));
       assertThat(map, hasSize(1));
       KPForecast kpf = map.get(0);
       assertNotNull(kpf);
       assertEquals(predictedTimeTag, kpf.getPredictedTimeTag());
       assertEquals(indexesTimeTag, kpf.getIndexesTimeTag());
       assertSame(probability1 , kpf.getProbability1());
       assertSame(probability2 , kpf.getProbability2());
       assertSame(probability3 , kpf.getProbability3());
       assertSame(inferiorLimit1 , kpf.getInferiorLimit1());
       assertSame(inferiorLimit2 , kpf.getInferiorLimit2());
       assertSame(inferiorLimit3 , kpf.getInferiorLimit3());
       assertSame(upperLimit1 , kpf.getUpperLimit1()); 
       assertSame(upperLimit2 , kpf.getUpperLimit2());
       assertSame(upperLimit3 , kpf.getUpperLimit3());
       verify(kpForecastList).create(); 
       verify(kpForecastFactory).create();
    }
}
