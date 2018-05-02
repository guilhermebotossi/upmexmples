package br.inpe.climaespacial.swd.values.rmp.average.mappers;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.values.rmp.average.dtos.AverageRMP;
import br.inpe.climaespacial.swd.values.rmp.average.entities.AverageRMPEntity;
import br.inpe.climaespacial.swd.values.rmp.average.factories.AverageRMPFactory;
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
@AdditionalClasses({DefaultAverageRMPMapper.class,
    DefaultListFactory.class
})
public class AverageRMPMapperTest {

    @Produces
    @Mock
    private AverageRMPFactory averageRMPFactory;

    @Inject
    private AverageRMPMapper averageRMPMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            averageRMPMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"averageRMPEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;

        AverageRMPEntity averageRMPe = new AverageRMPEntity(zdt, value);
        AverageRMP averageRMP1 = new AverageRMP();
        averageRMP1.setTimeTag(zdt);
        averageRMP1.setValue(value);
        List<AverageRMPEntity> averageRMPel = Arrays.asList(averageRMPe);

        when(averageRMPFactory.create(zdt, value)).thenReturn(averageRMP1);

        List<AverageRMP> averageRMPl = averageRMPMapper.map(averageRMPel);

        verify(averageRMPFactory).create(zdt, value);
        assertNotNull(averageRMPl);
        assertThat(averageRMPl, is(not(empty())));
        assertEquals(averageRMPel.size(), averageRMPl.size());
        AverageRMP averageRMP2 = averageRMPl.get(0);
        assertEquals(zdt, averageRMP2.getTimeTag());
        assertEquals(value, averageRMP2.getValue());
    }
}
