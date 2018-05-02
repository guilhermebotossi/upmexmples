package br.inpe.climaespacial.swd.values.temperature.average.mappers;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.values.temperature.average.dtos.AverageTemperature;
import br.inpe.climaespacial.swd.values.temperature.average.entities.AverageTemperatureEntity;
import br.inpe.climaespacial.swd.values.temperature.average.factories.AverageTemperatureFactory;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(CdiRunner.class)
@AdditionalClasses({DefaultAverageTemperatureMapper.class,
    DefaultListFactory.class
})
public class AverageTemperatureMapperTest {

    @Produces
    @Mock
    private AverageTemperatureFactory AverageTemperatureFactory;

    @Inject
    private AverageTemperatureMapper AverageTemperatureMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            AverageTemperatureMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"averageTemperatureEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;

        AverageTemperatureEntity averageTemperature = new AverageTemperatureEntity(zdt, value);
        AverageTemperature averageTemperature1 = new AverageTemperature();
        averageTemperature1.setTimeTag(zdt);
        averageTemperature1.setValue(value);
        List<AverageTemperatureEntity> atel = Arrays.asList(averageTemperature);

        when(AverageTemperatureFactory.create(zdt, value)).thenReturn(averageTemperature1);

        List<AverageTemperature> AverageTemperaturel = AverageTemperatureMapper.map(atel);

        verify(AverageTemperatureFactory).create(zdt, value);
        assertNotNull(AverageTemperaturel);
        assertThat(AverageTemperaturel, is(not(empty())));
        assertEquals(atel.size(), AverageTemperaturel.size());
        AverageTemperature AverageTemperature2 = AverageTemperaturel.get(0);
        assertEquals(zdt, AverageTemperature2.getTimeTag());
        assertEquals(value, AverageTemperature2.getValue());
    }
}
