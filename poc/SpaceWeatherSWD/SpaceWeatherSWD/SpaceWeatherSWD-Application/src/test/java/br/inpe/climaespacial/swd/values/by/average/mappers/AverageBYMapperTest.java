package br.inpe.climaespacial.swd.values.by.average.mappers;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.values.by.average.dtos.AverageBY;
import br.inpe.climaespacial.swd.values.by.average.entities.AverageBYEntity;
import br.inpe.climaespacial.swd.values.by.average.factories.AverageBYFactory;
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
@AdditionalClasses({DefaultAverageBYMapper.class,
    DefaultListFactory.class
})
public class AverageBYMapperTest {

    @Produces
    @Mock
    private AverageBYFactory averageBYFactory;

    @Inject
    private AverageBYMapper averageBYMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            averageBYMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"averageBYEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;

        AverageBYEntity averageBYe = new AverageBYEntity(zdt, value);
        AverageBY averageBY1 = new AverageBY();
        averageBY1.setTimeTag(zdt);
        averageBY1.setValue(value);
        List<AverageBYEntity> averageBYel = Arrays.asList(averageBYe);

        when(averageBYFactory.create(zdt, value)).thenReturn(averageBY1);

        List<AverageBY> averageBYl = averageBYMapper.map(averageBYel);

        verify(averageBYFactory).create(zdt, value);
        assertNotNull(averageBYl);
        assertThat(averageBYl, is(not(empty())));
        assertEquals(averageBYel.size(), averageBYl.size());
        AverageBY averageBY2 = averageBYl.get(0);
        assertEquals(zdt, averageBY2.getTimeTag());
        assertEquals(value, averageBY2.getValue());
    }
}
