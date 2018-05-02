package br.inpe.climaespacial.swd.values.by.value.mappers;

import br.inpe.climaespacial.swd.commons.factories.DefaultListFactory;
import br.inpe.climaespacial.swd.values.by.value.dtos.BY;
import br.inpe.climaespacial.swd.values.by.value.entities.BYEntity;
import br.inpe.climaespacial.swd.values.by.value.factories.BYFactory;
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
@AdditionalClasses({DefaultBYMapper.class,
    DefaultListFactory.class
})
public class BYMapperTest {

    @Produces
    @Mock
    private BYFactory byFactory;

    @Inject
    private BYMapper byMapper;

    @Test
    public void map_calledWithNull_throws() {
        RuntimeException re = null;

        try {
            byMapper.map(null);
        } catch (RuntimeException e) {
            re = e;
        }

        assertNotNull(re);
        assertEquals("Argument \"byEntityList\" cannot be null.", re.getMessage());
    }

    @Test
    public void map_called_succeeds() {
        ZonedDateTime zdt = ZonedDateTime.parse("2017-01-01T12:30:00z[UTC]");
        Double value = 2.0;

        BYEntity bye = new BYEntity(zdt, value);
        BY by1 = new BY();
        by1.setTimeTag(zdt);
        by1.setValue(value);
        List<BYEntity> byel = Arrays.asList(bye);

        when(byFactory.create(zdt, value)).thenReturn(by1);

        List<BY> byl = byMapper.map(byel);

        verify(byFactory).create(zdt, value);
        assertNotNull(byl);
        assertThat(byl, is(not(empty())));
        assertEquals(byel.size(), byl.size());
        BY by2 = byl.get(0);
        assertEquals(zdt, by2.getTimeTag());
        assertEquals(value, by2.getValue());
    }
}
