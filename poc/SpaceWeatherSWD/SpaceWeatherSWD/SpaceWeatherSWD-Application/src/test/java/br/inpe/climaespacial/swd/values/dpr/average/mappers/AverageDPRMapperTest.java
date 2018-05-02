package br.inpe.climaespacial.swd.values.dpr.average.mappers;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.values.dpr.average.dtos.AverageDPR;
import br.inpe.climaespacial.swd.values.dpr.average.entities.AverageDPREntity;
import br.inpe.climaespacial.swd.values.dpr.average.factories.AverageDPRFactory;
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
@AdditionalClasses({DefaultAverageDPRMapper.class,
    DefaultListFactory.class
})
public class AverageDPRMapperTest {

    @Produces
    @Mock
    private AverageDPRFactory averageDPRFactory;

    @Inject
    private AverageDPRMapper averageDPRMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            averageDPRMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"averageDPREntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;

        AverageDPREntity averageDPRe = new AverageDPREntity(zdt, value);
        AverageDPR averageDPR1 = new AverageDPR();
        averageDPR1.setTimeTag(zdt);
        averageDPR1.setValue(value);
        List<AverageDPREntity> averageDPRel = Arrays.asList(averageDPRe);

        when(averageDPRFactory.create(zdt, value)).thenReturn(averageDPR1);

        List<AverageDPR> averageDPRl = averageDPRMapper.map(averageDPRel);

        verify(averageDPRFactory).create(zdt, value);
        assertNotNull(averageDPRl);
        assertThat(averageDPRl, is(not(empty())));
        assertEquals(averageDPRel.size(), averageDPRl.size());
        AverageDPR averageDPR2 = averageDPRl.get(0);
        assertEquals(zdt, averageDPR2.getTimeTag());
        assertEquals(value, averageDPR2.getValue());
    }
}
