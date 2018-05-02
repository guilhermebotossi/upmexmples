package br.inpe.climaespacial.swd.kp.values.repositories;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockTypedQuery;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import br.inpe.climaespacial.swd.kp.values.mappers.KPForecastMapper;
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.forecast.entities.KPForecastEntity;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPForecastReaderRepository.class)
public class KPForecastReaderRepositoryTest {
    
    @Mock
    @Produces
    private EntityManager entityManager;
    
    @Mock
    @Produces
    private KPForecastMapper kpForecastMapper;
    
    @Inject
    private KPForecastReaderRepository kpForecastReaderRepository;
    
    @Test
    public void getNextForecasts_called_returns() {
        ZonedDateTime timeTag = ZonedDateTime.parse("2017-01-01T12:00:00z[UTC]");
        String sql = "SELECT kpfe FROM KPForecastEntity kpfe where kpfe.indexesTimeTag=:indexesTimeTag";
        
        TypedQuery<KPForecastEntity> tq = mockTypedQuery(KPForecastEntity.class);
        when(entityManager.createQuery(sql, KPForecastEntity.class)).thenReturn(tq);
        List<KPForecastEntity> kpfel = mockList(KPForecastEntity.class);
        when(tq.getResultList()).thenReturn(kpfel);
        List<KPForecast> kpfl1 = mockList(KPForecast.class);
        when(kpForecastMapper.map(kpfel)).thenReturn(kpfl1);
        when(kpfl1.size()).thenReturn(2);
        
        List<KPForecast> kpfl2 = kpForecastReaderRepository.getNextForecasts(timeTag); 
        
        assertNotNull(kpfl2);
        assertSame(kpfl1, kpfl2);
        assertThat(kpfl2, is(not(empty())));
        assertThat(kpfl2, hasSize(2));
        verify(entityManager).createQuery(sql, KPForecastEntity.class);
        verify(tq).setParameter("indexesTimeTag", timeTag);
        verify(tq).getResultList();
        verify(kpForecastMapper).map(kpfel);
    }
}
