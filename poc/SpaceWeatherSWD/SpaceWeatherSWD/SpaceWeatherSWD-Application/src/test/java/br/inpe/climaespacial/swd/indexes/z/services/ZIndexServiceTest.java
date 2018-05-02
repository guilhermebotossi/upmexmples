package br.inpe.climaespacial.swd.indexes.z.services;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import br.inpe.climaespacial.swd.indexes.z.dtos.ZIndex;
import br.inpe.climaespacial.swd.indexes.z.repositories.ZIndexHourlyAverageReaderRepository;
import br.inpe.climaespacial.swd.indexes.z.repositories.ZIndexWriterRepository;
import java.util.List;
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
@AdditionalClasses(DefaultZIndexService.class)
public class ZIndexServiceTest {

    @Produces
    @Mock
    private ZIndexHourlyAverageReaderRepository zIndexHourlyAverageReaderRepository;

    @Produces
    @Mock
    private ZIndexCalculator zIndexCalculator;

    @Produces
    @Mock
    private ZIndexWriterRepository zIndexWriterRepository;

    @Inject
    private ZIndexService zIndexService;

    @Test
    public void calculate_called_skipsPersistance() {
        List<HourlyAverage> hal = mockList(HourlyAverage.class);
        when(hal.isEmpty()).thenReturn(true);
        when(zIndexHourlyAverageReaderRepository.getHourlyAverages()).thenReturn(hal);

        zIndexService.calculate();

        verify(zIndexHourlyAverageReaderRepository, times(1)).getHourlyAverages();
        verify(zIndexCalculator, never()).calculate(any());
        verify(zIndexWriterRepository, never()).save(any());
    }

    @Test
    public void calculate_called_persistsBIndex() {
        List<HourlyAverage> hal1 = mockList(HourlyAverage.class);
        when(hal1.isEmpty()).thenReturn(false);

        List<HourlyAverage> hal2 = mockList(HourlyAverage.class);
        when(hal2.isEmpty()).thenReturn(true);

        when(zIndexHourlyAverageReaderRepository.getHourlyAverages())
                .thenReturn(hal1)
                .thenReturn(hal2);
        ZIndex zIndex = mock(ZIndex.class);
        when(zIndexCalculator.calculate(hal1)).thenReturn(zIndex);

        zIndexService.calculate();

        verify(zIndexHourlyAverageReaderRepository, times(2)).getHourlyAverages();
        verify(zIndexCalculator, times(1)).calculate(hal1);
        verify(zIndexWriterRepository, times(1)).save(zIndex);
    }

    @Test
    public void calculate_called_persistsTwoBIndexes() {
        List<HourlyAverage> hal1 = mockList(HourlyAverage.class);
        when(hal1.isEmpty()).thenReturn(false);

        List<HourlyAverage> hal2 = mockList(HourlyAverage.class);
        when(hal2.isEmpty()).thenReturn(false);

        List<HourlyAverage> hal3 = mockList(HourlyAverage.class);
        when(hal3.isEmpty()).thenReturn(true);

        when(zIndexHourlyAverageReaderRepository.getHourlyAverages())
                .thenReturn(hal1)
                .thenReturn(hal2)
                .thenReturn(hal3);
        ZIndex zIndex1 = mock(ZIndex.class);
        ZIndex zIndex2 = mock(ZIndex.class);
        when(zIndexCalculator.calculate(hal1)).thenReturn(zIndex1);
        when(zIndexCalculator.calculate(hal2)).thenReturn(zIndex2);

        zIndexService.calculate();

        verify(zIndexHourlyAverageReaderRepository, times(3)).getHourlyAverages();
        verify(zIndexCalculator, times(1)).calculate(hal1);
        verify(zIndexWriterRepository, times(1)).save(zIndex1);
        verify(zIndexCalculator, times(1)).calculate(hal2);
        verify(zIndexWriterRepository, times(1)).save(zIndex2);
    }
}
