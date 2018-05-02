package br.inpe.climaespacial.swd.kp.forecast.mappers;

import static org.hamcrest.Matchers.empty;
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
import br.inpe.climaespacial.swd.kp.forecast.factories.KPForecastEntityFactory;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultKPForecastEntityMapper.class})
public class KPForecastEntityMapperTest {

    @Produces
    @Mock
    private ListFactory<KPForecastEntity> kpForecastEntityListFactory;
    
    @Produces
    @Mock
    private KPForecastEntityFactory kpForecastEntityFactory;
    
    @Inject
    private KPForecastEntityMapper kpForecastEntityMapper;
    
    @Test
    public void map_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            kpForecastEntityMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }
 
        assertNotNull(re);
        assertEquals("Argument \"kpForecastList\" cannot be null.", re.getMessage());
    }
    
    @Test 
    public void map_map_returnsList() {
        KPForecast kpf = new KPForecast();
        kpf.setPredictedTimeTag(ZonedDateTime.parse("2017-01-01T15:00:00z[UTC]"));
        Double probability1 = 1.0;
        kpf.setProbability1(probability1);
        Double probability2 = 2.0;
        kpf.setProbability2(probability2);
        Double probability3 = 3.0;
        kpf.setProbability3(probability3);
        Double inferiorLimit1 = 4.0;
        kpf.setInferiorLimit1(inferiorLimit1);
        Double inferiorLimit2 = 5.0;
        kpf.setInferiorLimit2(inferiorLimit2);
        Double inferiorLimit3 = 6.0;
        kpf.setInferiorLimit3(inferiorLimit3);
        Double upperLimit1 = 1.0;
        kpf.setUpperLimit1(upperLimit1);
        Double upperLimit2 = 1.0;
        kpf.setUpperLimit2(upperLimit2);
        Double upperLimit3 = 1.0;
        kpf.setUpperLimit3(upperLimit3);
        
        kpf.setIndexesTimeTag(ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]"));
        
        List<KPForecast> kpfl = Arrays.asList(kpf);
        when(kpForecastEntityListFactory.create()).thenReturn(new ArrayList<KPForecastEntity>());
        when(kpForecastEntityFactory.create()).thenReturn(new KPForecastEntity());
        
        List<KPForecastEntity> kpfel = kpForecastEntityMapper.map(kpfl);
        
        assertNotNull(kpfel);
        assertThat(kpfel, is(not(empty())));
        KPForecastEntity kpfe = kpfel.get(0);
        assertSame(kpf.getPredictedTimeTag(), kpfe.getPredictedTimeTag());
        assertSame(kpf.getIndexesTimeTag(), kpfe.getIndexesTimeTag());
        assertSame(probability1 , kpfe.getProbability1());
        assertSame(probability2 , kpfe.getProbability2());
        assertSame(probability3 , kpfe.getProbability3());
        assertSame(inferiorLimit1 , kpfe.getInferiorLimit1());
        assertSame(inferiorLimit2 , kpfe.getInferiorLimit2());
        assertSame(inferiorLimit3 , kpfe.getInferiorLimit3());
        assertSame(upperLimit1 , kpfe.getUpperLimit1());
        assertSame(upperLimit2 , kpfe.getUpperLimit2());
        assertSame(upperLimit3 , kpfe.getUpperLimit3());
        
        
        verify(kpForecastEntityListFactory).create();
        verify(kpForecastEntityFactory).create();
    }
}
