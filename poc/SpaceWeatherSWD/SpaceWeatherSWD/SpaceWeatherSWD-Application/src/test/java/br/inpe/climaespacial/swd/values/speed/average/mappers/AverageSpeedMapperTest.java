package br.inpe.climaespacial.swd.values.speed.average.mappers;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.values.speed.average.dtos.AverageSpeed;
import br.inpe.climaespacial.swd.values.speed.average.entities.AverageSpeedEntity;
import br.inpe.climaespacial.swd.values.speed.average.factories.AverageSpeedFactory;
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
@AdditionalClasses({DefaultAverageSpeedMapper.class,
    DefaultListFactory.class
})
public class AverageSpeedMapperTest {

    @Produces
    @Mock
    private AverageSpeedFactory AverageSpeedFactory;

    @Inject
    private AverageSpeedMapper AverageSpeedMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            AverageSpeedMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"averageSpeedEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;

        AverageSpeedEntity AverageSpeede = new AverageSpeedEntity(zdt, value);
        AverageSpeed AverageSpeed1 = new AverageSpeed();
        AverageSpeed1.setTimeTag(zdt);
        AverageSpeed1.setValue(value);
        List<AverageSpeedEntity> AverageSpeedel = Arrays.asList(AverageSpeede);

        when(AverageSpeedFactory.create(zdt, value)).thenReturn(AverageSpeed1);

        List<AverageSpeed> AverageSpeedl = AverageSpeedMapper.map(AverageSpeedel);

        verify(AverageSpeedFactory).create(zdt, value);
        assertNotNull(AverageSpeedl);
        assertThat(AverageSpeedl, is(not(empty())));
        assertEquals(AverageSpeedel.size(), AverageSpeedl.size());
        AverageSpeed AverageSpeed2 = AverageSpeedl.get(0);
        assertEquals(zdt, AverageSpeed2.getTimeTag());
        assertEquals(value, AverageSpeed2.getValue());
    }
}
