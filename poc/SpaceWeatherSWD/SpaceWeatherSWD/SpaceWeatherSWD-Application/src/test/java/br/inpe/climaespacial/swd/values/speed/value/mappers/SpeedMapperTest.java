package br.inpe.climaespacial.swd.values.speed.value.mappers;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.values.speed.value.dtos.Speed;
import br.inpe.climaespacial.swd.values.speed.value.entities.SpeedEntity;
import br.inpe.climaespacial.swd.values.speed.value.factories.SpeedFactory;
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
@AdditionalClasses({DefaultSpeedMapper.class,
    DefaultListFactory.class
})
public class SpeedMapperTest {

    @Produces
    @Mock
    private SpeedFactory speedFactory;

    @Inject
    private SpeedMapper speedMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            speedMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"speedEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;

        SpeedEntity speede = new SpeedEntity(zdt, value);
        Speed speed1 = new Speed();
        speed1.setTimeTag(zdt);
        speed1.setValue(value);
        List<SpeedEntity> speedel = Arrays.asList(speede);

        when(speedFactory.create(zdt, value)).thenReturn(speed1);

        List<Speed> speedl = speedMapper.map(speedel);

        verify(speedFactory).create(zdt, value);
        assertNotNull(speedl);
        assertThat(speedl, is(not(empty())));
        assertEquals(speedel.size(), speedl.size());
        Speed speed2 = speedl.get(0);
        assertEquals(zdt, speed2.getTimeTag());
        assertEquals(value, speed2.getValue());
    }
}
