package br.inpe.climaespacial.swd.values.ey.value.mappers;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.values.ey.value.dtos.EY;
import br.inpe.climaespacial.swd.values.ey.value.entities.EYEntity;
import br.inpe.climaespacial.swd.values.ey.value.factories.EYFactory;
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
@AdditionalClasses({DefaultEYMapper.class,
    DefaultListFactory.class
})
public class EYMapperTest {

    @Produces
    @Mock
    private EYFactory eyFactory;

    @Inject
    private EYMapper eyMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            eyMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"eyEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;

        EYEntity eye = new EYEntity(zdt, value);
        EY ey1 = new EY();
        ey1.setTimeTag(zdt);
        ey1.setValue(value);
        List<EYEntity> eyel = Arrays.asList(eye);

        when(eyFactory.create(zdt, value)).thenReturn(ey1);

        List<EY> eyl = eyMapper.map(eyel);

        verify(eyFactory).create(zdt, value);
        assertNotNull(eyl);
        assertThat(eyl, is(not(empty())));
        assertEquals(eyel.size(), eyl.size());
        EY ey2 = eyl.get(0);
        assertEquals(zdt, ey2.getTimeTag());
        assertEquals(value, ey2.getValue());
    }
}
