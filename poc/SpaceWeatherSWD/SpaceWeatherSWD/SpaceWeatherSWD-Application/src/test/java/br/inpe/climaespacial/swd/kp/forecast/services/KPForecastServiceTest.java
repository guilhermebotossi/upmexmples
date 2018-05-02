package br.inpe.climaespacial.swd.kp.forecast.services;

import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.kp.dtos.KPValue;
import br.inpe.climaespacial.swd.kp.forecast.dtos.KPForecast;
import br.inpe.climaespacial.swd.kp.forecast.repositories.CIndexReaderRepository;
import br.inpe.climaespacial.swd.kp.forecast.repositories.KPForecastWriterRepository;
import br.inpe.climaespacial.swd.kp.forecast.repositories.KPValueReaderRepository;

import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.any;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultKPForecastService.class)
public class KPForecastServiceTest {

    @Mock
    @Produces
    private CIndexReaderRepository cIndexReaderRepository;

    @Mock
    @Produces
    private KPValueReaderRepository kpValueReaderRepository;

    @Mock
    @Produces
    private KPForecastWriterRepository kpForecastWriterRepository;

    @Mock
    @Produces
    private KPForecastCalculator kpForecastCalculator;

    @Inject
    private KPForecastService kpForecastService;

    @Test(timeout = 5000)
    public void calculate_calledNullKpValue_doesNothing() {
        when(kpValueReaderRepository.getLastKPValue()).thenReturn(null);

        kpForecastService.calculate();

        verify(kpValueReaderRepository).getLastKPValue(); 
        verify(cIndexReaderRepository, never()).getLastCIndexes(any());
        verify(kpForecastCalculator, never()).calculate(any(), any());
        verify(kpForecastWriterRepository, never()).save(any());
    }
    
    @Test(timeout = 5000)
    public void calculate_called_succeeds() {
        List<CIndex> cil = mockList(CIndex.class);
        KPValue kpv = mock(KPValue.class);
        List<KPForecast> kpfl = mockList(KPForecast.class);

        when(kpValueReaderRepository.getLastKPValue()).thenReturn(kpv).thenReturn(null);
        when(cIndexReaderRepository.getLastCIndexes(kpv.getTimeTag())).thenReturn(cil);
        when(kpForecastCalculator.calculate(cil, kpv)).thenReturn(kpfl);

        kpForecastService.calculate();

        verify(kpValueReaderRepository, times(2)).getLastKPValue();
        verify(cIndexReaderRepository).getLastCIndexes(kpv.getTimeTag()); 
        verify(kpForecastCalculator).calculate(cil, kpv);
        verify(kpForecastWriterRepository).save(kpfl);
    }
}
