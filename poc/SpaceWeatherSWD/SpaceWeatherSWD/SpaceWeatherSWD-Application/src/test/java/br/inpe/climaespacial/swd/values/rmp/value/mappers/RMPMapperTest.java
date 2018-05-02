package br.inpe.climaespacial.swd.values.rmp.value.mappers;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.values.rmp.value.dtos.RMP;
import br.inpe.climaespacial.swd.values.rmp.value.entities.RMPEntity;
import br.inpe.climaespacial.swd.values.rmp.value.factories.RMPFactory;
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
@AdditionalClasses({DefaultRMPMapper.class,
    DefaultListFactory.class
})
public class RMPMapperTest {

    @Produces
    @Mock
    private RMPFactory rmpFactory;

    @Inject
    private RMPMapper rmpMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            rmpMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"rmpEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;

        RMPEntity rmpe = new RMPEntity(zdt, value);
        RMP rmp1 = new RMP();
        rmp1.setTimeTag(zdt);
        rmp1.setValue(value);
        List<RMPEntity> rmpel = Arrays.asList(rmpe);

        when(rmpFactory.create(zdt, value)).thenReturn(rmp1);

        List<RMP> rmpl = rmpMapper.map(rmpel);

        verify(rmpFactory).create(zdt, value);
        assertNotNull(rmpl);
        assertThat(rmpl, is(not(empty())));
        assertEquals(rmpel.size(), rmpl.size());
        RMP rmp2 = rmpl.get(0);
        assertEquals(zdt, rmp2.getTimeTag());
        assertEquals(value, rmp2.getValue());
    }
}
