package br.inpe.climaespacial.swd.values.bx.average.mappers;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.values.bx.average.dtos.AverageBX;
import br.inpe.climaespacial.swd.values.bx.average.entities.AverageBXEntity;
import br.inpe.climaespacial.swd.values.bx.average.factories.AverageBXFactory;
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
@AdditionalClasses({DefaultAverageBXMapper.class,
    DefaultListFactory.class
})
public class AverageBXMapperTest {

    @Produces
    @Mock
    private AverageBXFactory averageBXFactory;

    @Inject
    private AverageBXMapper averageBXMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            averageBXMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"averageBXEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;

        AverageBXEntity averageBXe = new AverageBXEntity(zdt, value);
        AverageBX averageBX1 = new AverageBX();
        averageBX1.setTimeTag(zdt);
        averageBX1.setValue(value);
        List<AverageBXEntity> averageBXel = Arrays.asList(averageBXe);

        when(averageBXFactory.create(zdt, value)).thenReturn(averageBX1);

        List<AverageBX> averageBXl = averageBXMapper.map(averageBXel);

        verify(averageBXFactory).create(zdt, value);
        assertNotNull(averageBXl);
        assertThat(averageBXl, is(not(empty())));
        assertEquals(averageBXel.size(), averageBXl.size());
        AverageBX averageBX2 = averageBXl.get(0);
        assertEquals(zdt, averageBX2.getTimeTag());
        assertEquals(value, averageBX2.getValue());
    }
}
