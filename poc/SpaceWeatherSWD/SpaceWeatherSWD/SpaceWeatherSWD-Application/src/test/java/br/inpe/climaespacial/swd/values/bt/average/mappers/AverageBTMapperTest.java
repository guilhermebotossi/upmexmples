package br.inpe.climaespacial.swd.values.bt.average.mappers;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.values.bt.average.dtos.AverageBT;
import br.inpe.climaespacial.swd.values.bt.average.entities.AverageBTEntity;
import br.inpe.climaespacial.swd.values.bt.average.factories.AverageBTFactory;
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
@AdditionalClasses({DefaultAverageBTMapper.class,
    DefaultListFactory.class
})
public class AverageBTMapperTest {

    @Produces
    @Mock
    private AverageBTFactory averageBTFactory;

    @Inject
    private AverageBTMapper averageBTMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            averageBTMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"averageBTEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;

        AverageBTEntity bte = new AverageBTEntity(zdt, value);
        AverageBT abt1 = new AverageBT();
        abt1.setTimeTag(zdt);
        abt1.setValue(value);
        List<AverageBTEntity> btel = Arrays.asList(bte);

        when(averageBTFactory.create(zdt, value)).thenReturn(abt1);

        List<AverageBT> abtl = averageBTMapper.map(btel);

        verify(averageBTFactory).create(zdt, value);
        assertNotNull(abtl);
        assertThat(abtl, is(not(empty())));
        assertEquals(btel.size(), abtl.size());
        AverageBT abt2 = abtl.get(0);
        assertEquals(zdt, abt2.getTimeTag());
        assertEquals(value, abt2.getValue());
    }
}
