package br.inpe.climaespacial.swd.values.ey.average.mappers;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.values.ey.average.dtos.AverageEY;
import br.inpe.climaespacial.swd.values.ey.average.entities.AverageEYEntity;
import br.inpe.climaespacial.swd.values.ey.average.factories.AverageEYFactory;
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
@AdditionalClasses({DefaultAverageEYMapper.class,
    DefaultListFactory.class
})
public class AverageEYMapperTest {

    @Produces
    @Mock
    private AverageEYFactory averageEYFactory;

    @Inject
    private AverageEYMapper averageEYMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            averageEYMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"averageEYEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;

        AverageEYEntity averageEYe = new AverageEYEntity(zdt, value);
        AverageEY averageEY1 = new AverageEY();
        averageEY1.setTimeTag(zdt);
        averageEY1.setValue(value);
        List<AverageEYEntity> averageEYel = Arrays.asList(averageEYe);

        when(averageEYFactory.create(zdt, value)).thenReturn(averageEY1);

        List<AverageEY> averageEYl = averageEYMapper.map(averageEYel);

        verify(averageEYFactory).create(zdt, value);
        assertNotNull(averageEYl);
        assertThat(averageEYl, is(not(empty())));
        assertEquals(averageEYel.size(), averageEYl.size());
        AverageEY averageEY2 = averageEYl.get(0);
        assertEquals(zdt, averageEY2.getTimeTag());
        assertEquals(value, averageEY2.getValue());
    }

}
