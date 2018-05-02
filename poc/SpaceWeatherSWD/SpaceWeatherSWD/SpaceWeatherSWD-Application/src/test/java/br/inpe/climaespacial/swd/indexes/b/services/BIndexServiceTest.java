package br.inpe.climaespacial.swd.indexes.b.services;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.indexes.b.dtos.BIndex;
import br.inpe.climaespacial.swd.indexes.b.repositories.BIndexHourlyAverageReaderRepository;
import br.inpe.climaespacial.swd.indexes.b.repositories.BIndexWriterRepository;
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
@AdditionalClasses(DefaultBIndexService.class)
public class BIndexServiceTest {

    @Produces
    @Mock
    private BIndexHourlyAverageReaderRepository bIndexHourlyAverageReaderRepository;

    @Produces
    @Mock
    private BIndexCalculator bIndexCalculator;

    @Produces
    @Mock
    private BIndexWriterRepository bIndexWriterRepository;

    @Inject
    private BIndexService bIndexService;

    @Test
    public void calculate_called_skipsPersistance() {
        when(bIndexHourlyAverageReaderRepository.getHourlyAverage()).thenReturn(null);

        bIndexService.calculate();

        verify(bIndexHourlyAverageReaderRepository, times(1)).getHourlyAverage();
        verify(bIndexCalculator, never()).calculate(any());
        verify(bIndexWriterRepository, never()).save(any());
    }

    @Test
    public void calculate_called_persistsBIndex() {
        HourlyAverage ha = mock(HourlyAverage.class);
        when(bIndexHourlyAverageReaderRepository.getHourlyAverage())
                .thenReturn(ha)
                .thenReturn(null);
        BIndex bIndex = mock(BIndex.class);
        when(bIndexCalculator.calculate(ha)).thenReturn(bIndex);

        bIndexService.calculate();

        verify(bIndexHourlyAverageReaderRepository, times(2)).getHourlyAverage();
        verify(bIndexCalculator, times(1)).calculate(ha);
        verify(bIndexWriterRepository, times(1)).save(bIndex);
    }

    @Test
    public void calculate_called_persistsTwoBIndexes() {
        HourlyAverage ha1 = mock(HourlyAverage.class);
        HourlyAverage ha2 = mock(HourlyAverage.class);
        when(bIndexHourlyAverageReaderRepository.getHourlyAverage())
                .thenReturn(ha1)
                .thenReturn(ha2)
                .thenReturn(null);
        BIndex bIndex1 = mock(BIndex.class);
        BIndex bIndex2 = mock(BIndex.class);
        when(bIndexCalculator.calculate(ha1)).thenReturn(bIndex1);
        when(bIndexCalculator.calculate(ha2)).thenReturn(bIndex2);

        bIndexService.calculate();

        verify(bIndexHourlyAverageReaderRepository, times(3)).getHourlyAverage();
        verify(bIndexCalculator, times(1)).calculate(ha1);
        verify(bIndexWriterRepository, times(1)).save(bIndex1);
        verify(bIndexCalculator, times(1)).calculate(ha2);
        verify(bIndexWriterRepository, times(1)).save(bIndex2);
    }
}
