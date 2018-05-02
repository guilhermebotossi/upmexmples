package br.inpe.climaespacial.swd.indexes.c.services;

import br.inpe.climaespacial.swd.indexes.c.dtos.CIndex;
import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.indexes.c.repositories.CIndexHourlyAverageReaderRepository;
import br.inpe.climaespacial.swd.indexes.c.repositories.CIndexWriterRepository;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultCIndexService.class)
public class CIndexServiceTest {

    @Produces
    @Mock
    private CIndexHourlyAverageReaderRepository cIndexHourlyAverageReaderRepository;

    @Produces
    @Mock
    private CIndexCalculator cIndexCalculator;

    @Produces
    @Mock
    private CIndexWriterRepository cIndexWriterRepository;

    @Inject
    private CIndexService cIndexService;

    @Test
    public void calculate_called_skipsPersistance() {
        when(cIndexHourlyAverageReaderRepository.getHourlyAverage()).thenReturn(null);

        cIndexService.calculate();

        verify(cIndexHourlyAverageReaderRepository, times(1)).getHourlyAverage();
        verify(cIndexCalculator, never()).calculate(any());
        verify(cIndexWriterRepository, never()).save(any());
    }

    @Test
    public void calculate_called_persistsBIndex() {
        HourlyAverage ha = mock(HourlyAverage.class);
        when(cIndexHourlyAverageReaderRepository.getHourlyAverage())
                .thenReturn(ha)
                .thenReturn(null);
        CIndex cIndex = mock(CIndex.class);
        when(cIndexCalculator.calculate(ha)).thenReturn(cIndex);

        cIndexService.calculate();

        verify(cIndexHourlyAverageReaderRepository, times(2)).getHourlyAverage();
        verify(cIndexCalculator, times(1)).calculate(ha);
        verify(cIndexWriterRepository, times(1)).save(cIndex);
    }

    @Test
    public void calculate_called_persistsTwoBIndexes() {
        HourlyAverage ha1 = mock(HourlyAverage.class);
        HourlyAverage ha2 = mock(HourlyAverage.class);
        when(cIndexHourlyAverageReaderRepository.getHourlyAverage())
                .thenReturn(ha1)
                .thenReturn(ha2)
                .thenReturn(null);
        CIndex cIndex1 = mock(CIndex.class);
        CIndex cIndex2 = mock(CIndex.class);
        when(cIndexCalculator.calculate(ha1)).thenReturn(cIndex1);
        when(cIndexCalculator.calculate(ha2)).thenReturn(cIndex2);

        cIndexService.calculate();

        verify(cIndexHourlyAverageReaderRepository, times(3)).getHourlyAverage();
        verify(cIndexCalculator, times(1)).calculate(ha1);
        verify(cIndexWriterRepository, times(1)).save(cIndex1);
        verify(cIndexCalculator, times(1)).calculate(ha2);
        verify(cIndexWriterRepository, times(1)).save(cIndex2);
    }
}
