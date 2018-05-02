package br.inpe.climaespacial.swd.indexes.v.services;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import static br.inpe.climaespacial.swd.commons.EmbraceMockito.mockList;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import br.inpe.climaespacial.swd.indexes.v.repositories.VIndexHourlyAverageReaderRepository;
import br.inpe.climaespacial.swd.indexes.v.repositories.VIndexReaderRepository;
import br.inpe.climaespacial.swd.indexes.v.repositories.VIndexWriterRepository;
import java.time.ZonedDateTime;
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
@AdditionalClasses(DefaultVIndexService.class)
public class VIndexServiceTest {

    @Produces
    @Mock
    private VIndexHourlyAverageReaderRepository vIndexHourlyAverageReaderRepository;

    @Produces
    @Mock
    private VIndexReaderRepository vIndexReaderRepository;

    @Produces
    @Mock
    private VIndexCalculator vIndexCalculator;

    @Produces
    @Mock
    private VIndexWriterRepository vIndexWriterRepository;

    @Inject
    private VIndexService vIndexService;

    @Test
    public void calculate_called_skipsPersistance() {
        List<HourlyAverage> hal = mockList(HourlyAverage.class);
        when(hal.isEmpty()).thenReturn(true);
        when(vIndexHourlyAverageReaderRepository.getHourlyAverages()).thenReturn(hal);

        vIndexService.calculate();

        verify(vIndexHourlyAverageReaderRepository, times(1)).getHourlyAverages();
        verify(vIndexCalculator, never()).calculate(any(), any());
        verify(vIndexWriterRepository, never()).save(any());
    }

    @Test
    public void calculate_called_persistsVIndex() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T00:00:00Z[UTC]");

        HourlyAverage ha = mock(HourlyAverage.class);
        when(ha.getTimeTag()).thenReturn(zdt);

        int size = 10;

        List<HourlyAverage> hal1 = mockList(HourlyAverage.class);
        when(hal1.isEmpty()).thenReturn(false);
        when(hal1.size()).thenReturn(size);
        when(hal1.get(size - 1)).thenReturn(ha);

        List<HourlyAverage> hal2 = mockList(HourlyAverage.class);
        when(hal2.isEmpty()).thenReturn(true);

        when(vIndexHourlyAverageReaderRepository.getHourlyAverages())
                .thenReturn(hal1)
                .thenReturn(hal2);

        List<VIndex> vIndexList = mockList(VIndex.class);
        when(vIndexReaderRepository.getLastVIndexesFromDateTime(zdt)).thenReturn(vIndexList);

        VIndex vIndex = mock(VIndex.class);
        when(vIndexCalculator.calculate(hal1, vIndexList)).thenReturn(vIndex);

        vIndexService.calculate();

        verify(vIndexHourlyAverageReaderRepository, times(2)).getHourlyAverages();
        verify(vIndexCalculator, times(1)).calculate(hal1, vIndexList);
        verify(vIndexWriterRepository, times(1)).save(vIndex);
    }

    @Test(timeout = 2000L)
    public void calculate_called_persistsTwoVIndexes() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T00:00:00Z[UTC]");

        HourlyAverage ha = mock(HourlyAverage.class);
        when(ha.getTimeTag()).thenReturn(zdt);

        int size = 10;

        List<HourlyAverage> hal1 = mockList(HourlyAverage.class);
        when(hal1.isEmpty()).thenReturn(false);
        when(hal1.size()).thenReturn(size);
        when(hal1.get(size - 1)).thenReturn(ha);

        List<HourlyAverage> hal2 = mockList(HourlyAverage.class);
        when(hal2.isEmpty()).thenReturn(true);

        when(vIndexHourlyAverageReaderRepository.getHourlyAverages())
                .thenReturn(hal1)
                .thenReturn(hal1)
                .thenReturn(hal2);

        List<VIndex> vIndexList = mockList(VIndex.class);
        when(vIndexReaderRepository.getLastVIndexesFromDateTime(zdt))
                .thenReturn(vIndexList);

        VIndex vIndex = mock(VIndex.class);
        when(vIndexCalculator.calculate(hal1, vIndexList)).thenReturn(vIndex);

        vIndexService.calculate();

        verify(vIndexHourlyAverageReaderRepository, times(3)).getHourlyAverages();
        verify(vIndexCalculator, times(2)).calculate(hal1, vIndexList);
        verify(vIndexWriterRepository, times(2)).save(vIndex);
    }
}
