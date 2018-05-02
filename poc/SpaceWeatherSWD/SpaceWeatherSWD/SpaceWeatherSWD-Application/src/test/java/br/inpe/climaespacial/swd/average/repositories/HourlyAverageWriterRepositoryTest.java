package br.inpe.climaespacial.swd.average.repositories;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.average.entities.HourlyAverageEntity;
import br.inpe.climaespacial.swd.average.mappers.HourlyAverageEntityMapper;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses(DefaultHourlyAverageWriterRepository.class)
public class HourlyAverageWriterRepositoryTest {

    @Produces
    @Mock
    private EntityManager entityManager;

    @Produces
    @Mock
    private HourlyAverageEntityMapper hourlyAverageEntityMapper;

    @Inject
    private HourlyAverageWriterRepository hourlyAverageWriterRepository;

    @Test
    public void save_calledWithNullArgument_throws() {
        RuntimeException re = null;

        try {
            hourlyAverageWriterRepository.save(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"hourlyAverage\" cannot be null.", re.getMessage());
    }

    @Test
    public void save_calledwithValidParameter_persists() {
        HourlyAverage ha = mock(HourlyAverage.class);
        HourlyAverageEntity hae = mock(HourlyAverageEntity.class);
        when(hourlyAverageEntityMapper.map(ha)).thenReturn(hae);

        hourlyAverageWriterRepository.save(ha);

        verify(hourlyAverageEntityMapper, times(1)).map(ha);
        verify(entityManager, times(1)).persist(hae);
        verify(entityManager, times(1)).flush();
    }

}
