package br.inpe.climaespacial.swd.values.temperature.value.mappers;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.values.temperature.value.dtos.Temperature;
import br.inpe.climaespacial.swd.values.temperature.value.entities.TemperatureEntity;
import br.inpe.climaespacial.swd.values.temperature.value.factories.TemperatureFactory;
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
@AdditionalClasses({DefaultTemperatureMapper.class,
    DefaultListFactory.class
})
public class TemperatureMapperTest {

    @Produces
    @Mock
    private TemperatureFactory temperatureFactory;

    @Inject
    private TemperatureMapper temperatureMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            temperatureMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"temperatureEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;

        TemperatureEntity temperaturee = new TemperatureEntity(zdt, value);
        Temperature temperature1 = new Temperature();
        temperature1.setTimeTag(zdt);
        temperature1.setValue(value);
        List<TemperatureEntity> temperatureel = Arrays.asList(temperaturee);

        when(temperatureFactory.create(zdt, value)).thenReturn(temperature1);

        List<Temperature> temperaturel = temperatureMapper.map(temperatureel);

        verify(temperatureFactory).create(zdt, value);
        assertNotNull(temperaturel);
        assertThat(temperaturel, is(not(empty())));
        assertEquals(temperatureel.size(), temperaturel.size());
        Temperature temperature2 = temperaturel.get(0);
        assertEquals(zdt, temperature2.getTimeTag());
        assertEquals(value, temperature2.getValue());
    }
}
