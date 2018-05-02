package br.inpe.climaespacial.swd.kp.forecast.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.forecast.entities.KPForecastEntity;
import br.inpe.climaespacial.swd.kp.forecast.mappers.KPForecastEntityMapper;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKpForecastWriterRepository.class)
public class KpForecastWriterRepositoryTest {
    
    @Mock
    @Produces
    private EntityManager entityManager;
    
    @Mock
    @Produces
    private KPForecastEntityMapper kpForecastEntityMapper;
    
    @Inject
    private KPForecastWriterRepository kpForecastWriterRepository;
    
    @Test
    public void save_calledWithNull_throws() {
        RuntimeException re = null;

        try {        
            kpForecastWriterRepository.save(null);
        } catch(RuntimeException e) {
            re = e;
        }
        
        assertNotNull(re);
        assertEquals("Argument \"kpForecastList\" cannot be null.", re.getMessage()); 
    }
    
    @Test
    public void save_called_succeeds() {
        List<KPForecast> kpfl = mockList(KPForecast.class);
        KPForecastEntity kpfe1 = mock(KPForecastEntity.class);
        KPForecastEntity kpfe2 = mock(KPForecastEntity.class);
        List<KPForecastEntity> kpfel = Arrays.asList(kpfe1, kpfe2);
        when(kpForecastEntityMapper.map(kpfl)).thenReturn(kpfel); 
        
        kpForecastWriterRepository.save(kpfl);
        
        verify(kpForecastEntityMapper).map(kpfl);
        verify(entityManager).persist(kpfe1);
        verify(entityManager).persist(kpfe2);
        verify(entityManager).flush();
        verify(entityManager).clear(); 
    }


}
